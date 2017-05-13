package com.sub.syn.timer;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sub.syn.common.Common;
import com.sub.syn.common.Config;
import com.sub.syn.qianggou.entity.PQianggou;
import com.sub.syn.qianggou.entity.TimeQueue;
import com.sub.syn.qianggou.service.PQianggouService;
import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.internal.util.StringUtils;
import com.taobao.api.request.TbkJuTqgGetRequest;
import com.taobao.api.response.TbkJuTqgGetResponse;

/**
 * 同步抢购商品信息 淘宝
 * 
 * @author wuyb
 *
 */
public class SynQiangGouThread extends Thread {
	
	private Log log=LogFactory.getLog(SynQiangGouThread.class);

	private PQianggouService service = new PQianggouService();
	
	private int supus=Integer.parseInt(Config.getProperty("supus"));

	public void run() {
		long a=System.currentTimeMillis();
		String time = TimeQueue.queue.poll();
		log.info(this.getName() + "||" + time);
		while (null != time) {
			String start = time.split("=")[0];
			String end = time.split("=")[1];
			TaobaoClient client = new DefaultTaobaoClient(Common.url, Common.appkey, Common.secret);
			TbkJuTqgGetRequest req = new TbkJuTqgGetRequest();
			long pageNo = 1L;
			String jsonstr = getQg(client, req, pageNo, start, end);
			String total_results = jsonstr.substring(jsonstr.indexOf("total_results") + 15,jsonstr.indexOf("request_id") - 2);
			// System.out.println("total_results : "+total_results);
			while (total_results.equals("40")&&Integer.parseInt(total_results) > 0) {
				List<PQianggou> list = jsonToBean(jsonstr);
				if(TimeQueue.type==0){
					service.saveBath(list);
				}else{
					service.updateBath(list);
				}
				jsonstr = getQg(client, req, ++pageNo, start, end);
				System.out.println("响应："+jsonstr);
				total_results = jsonstr.substring(jsonstr.indexOf("total_results") + 15,jsonstr.indexOf("request_id") - 2);
				log.info(this.getName() + "   total_results : " + total_results);
				if (total_results.length() > 2) {
					break;
				}
			}
			try {
				TimeUnit.MILLISECONDS.sleep(200);//100毫秒
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			//System.out.println(this.getName() + "   抽取完成");
			time = TimeQueue.queue.poll();
		}
		long b=System.currentTimeMillis();
		log.info("花费时间："+(b-a)/1000+"秒");
	}

	public String getQg(TaobaoClient client, TbkJuTqgGetRequest req, long pageNo, String start, String end) {
		req.setAdzoneId(Common.adzoneId);
		req.setFields("click_url,pic_url,reserve_price,zk_final_price,total_amount,sold_num,title,category_name,start_time,end_time");
		req.setStartTime(StringUtils.parseDateTime(start));
		req.setEndTime(StringUtils.parseDateTime(end));
		req.setPageNo(pageNo);
		req.setPageSize(40L);
		TbkJuTqgGetResponse rsp;
		try {
			rsp = client.execute(req);
			return rsp.getBody();
		} catch (ApiException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void main(String[] args) throws ApiException {
		SynQiangGouThread t = new SynQiangGouThread();
		t.start();

	}

	/**
	 * 抢购商品bean 转换
	 * 
	 * @param json
	 * @return
	 */
	private List<PQianggou> jsonToBean(String json) {
		DecimalFormat df = new DecimalFormat("######0.00");
		List<PQianggou> list = new ArrayList<PQianggou>();
		com.alibaba.fastjson.JSONObject object = JSON.parseObject(json);
		JSONObject data = object.getJSONObject("tbk_ju_tqg_get_response").getJSONObject("results");
		JSONArray items = data.getJSONArray("results");
		String s = "";
		PQianggou bean = new PQianggou();
		for (int i = 0; i < items.size(); i++) {
			bean = new PQianggou();
			s = items.getString(i);
			JSONObject obj = JSONObject.parseObject(s);
			bean.setCategoryName(obj.getString("category_name"));
			bean.setClickUrl(obj.getString("click_url"));
			bean.setEndTime(obj.getString("end_time"));
			bean.setNumIid(obj.getLong("num_iid"));
			bean.setPicUrl(obj.getString("pic_url"));
			bean.setReservePrice(obj.getString("reserve_price"));
			bean.setSoldNum(obj.getInteger("sold_num"));
			bean.setStartTime(obj.getString("start_time"));
			bean.setTitle(obj.getString("title"));
			bean.setTotalAmount(obj.getInteger("total_amount"));
			bean.setZkFinalPrice(Double.parseDouble(df.format(obj.getDoubleValue("zk_final_price"))));
			//商品价格小于1000，销售总数量小于50时，不抽取数据
//			if(bean.getTotalAmount()<=50&&bean.getZkFinalPrice()<=50){
//				continue;
//			}
			bean.setType(0);
			// service.save(bean);
			if(bean.getTotalAmount()<=bean.getSoldNum()+supus){
				bean.setStatus(1);
			}
			list.add(bean);
		}
		return list;
	}
}
