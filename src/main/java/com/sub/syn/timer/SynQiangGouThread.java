package com.sub.syn.timer;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sub.syn.common.Common;
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

	private PQianggouService service = new PQianggouService();

	public void run() {
		String time = TimeQueue.queue.poll();
		System.out.println(this.getName() + "||" + time);
		while (null != time) {
			String start = time.split("=")[0];
			String end = time.split("=")[1];
			TaobaoClient client = new DefaultTaobaoClient(Common.url, Common.appkey, Common.secret);
			TbkJuTqgGetRequest req = new TbkJuTqgGetRequest();
			long pageNo = 1L;
			String jsonstr = getQg(client, req, pageNo, start, end);
			String total_results = jsonstr.substring(jsonstr.indexOf("total_results") + 15,
					jsonstr.indexOf("request_id") - 2);
			// System.out.println("total_results : "+total_results);
			while (total_results.equals("40")&&Integer.parseInt(total_results) > 0) {
				List<PQianggou> list = jsonToBean(jsonstr);
				if(TimeQueue.type==0){
					service.saveBath(list);
				}else{
					service.updateBath(list);
				}
				jsonstr = getQg(client, req, ++pageNo, start, end);
				total_results = jsonstr.substring(jsonstr.indexOf("total_results") + 15,
						jsonstr.indexOf("request_id") - 2);
				System.out.println(this.getName() + "   total_results : " + total_results);
				if (total_results.length() > 2) {
					break;
				}
			}
			System.out.println(this.getName() + "   抽取完成");
			time = TimeQueue.queue.poll();
		}
	}

	public String getQg(TaobaoClient client, TbkJuTqgGetRequest req, long pageNo, String start, String end) {
		req.setAdzoneId(Common.adzoneId);
		req.setFields(
				"click_url,pic_url,reserve_price,zk_final_price,total_amount,sold_num,title,category_name,start_time,end_time");
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
			bean.setTotalAmount(obj.getString("total_amount"));
			bean.setZkFinalPrice(obj.getString("zk_final_price"));
			bean.setType(0);
			// service.save(bean);
			list.add(bean);
		}
		return list;
	}
}