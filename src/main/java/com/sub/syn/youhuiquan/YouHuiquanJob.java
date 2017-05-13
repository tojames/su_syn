package com.sub.syn.youhuiquan;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sub.syn.common.DateUtils;

/**
 * 大淘客商品同步
 * @author wuyb
 *
 */
public class YouHuiquanJob extends ParentYhyJob{

	private static Log log=LogFactory.getLog(YouHuiquanJob.class);
	private String url = "http://api.dataoke.com/index.php?r=Port/index&type=total&appkey=8438shn9i6&v=utf-8&page=[page]";
	private int page = 1;
	
	private PYouHuiQuanService service=new PYouHuiQuanService();

	private List<YouHuiQuan> jsonToBean(String json) {
		List<YouHuiQuan> list = new ArrayList<YouHuiQuan>();
		com.alibaba.fastjson.JSONObject object = JSON.parseObject(json);
		JSONArray items = object.getJSONArray("result");
		String s = "";
		YouHuiQuan bean = new YouHuiQuan();
		for (int i = 0; i < items.size(); i++) {
			bean = new YouHuiQuan();
			s = items.getString(i);
			JSONObject obj = JSONObject.parseObject(s);
			bean.setId(Integer.parseInt(obj.getString("ID")));
			bean.setAliClick(obj.getString("ali_click"));
			bean.setCid(Integer.parseInt(obj.getString("Cid")));
			bean.setD_title(obj.getString("D_title"));
			bean.setDsr(obj.getString("Dsr"));
			bean.setGoodsId(obj.getLongValue("GoodsID"));
			bean.setIntroduce(obj.getString("Introduce"));
			bean.setIsTmall(obj.getIntValue("IsTmall"));
			bean.setOrgPrice(obj.getDoubleValue("Org_Price"));
			bean.setPic(obj.getString("Pic"));
			bean.setPrice(obj.getDoubleValue("Price"));
			bean.setqQuanMLink(obj.getString("Quan_m_link"));
			bean.setQuanCondition(obj.getString("Quan_condition"));
			bean.setQuanLink(obj.getString("Quan_link"));
			bean.setQuanPrice(obj.getDoubleValue("Quan_price"));
			bean.setSalesNum(obj.getIntValue("Sales_num"));
			bean.setSellerID(obj.getString("SellerID"));
			bean.setTitle(obj.getString("Title"));
			bean.setCreateTime(new Date());
			try {
				bean.setQuanTime(DateUtils.parseDate(obj.getString("Quan_time"), "yyyy-mm-dd HH:MM:SS"));
			} catch (ParseException e) {
				e.printStackTrace();
			}//领券结束时间
			bean.setType(1);
			bean.setQuanSurplus(obj.getIntValue("Quan_surplus"));
			list.add(bean);
		}
		return list;
	}

	public void synJob() {
		String json = httpGetRequest(url.replace("[page]", page + ""));
		List<YouHuiQuan> list = jsonToBean(json);
		try {
			while (list.size()>0) {
				service.deleteByIds(list);
				service.saveBath(list);
				++page;
				System.out.println(list.size() + " 页数：" + page);
				log.info("大淘客优惠券 记录条数："+list.size() + " 页数：" + page);
				json = httpGetRequest(url.replace("[page]", page + ""));
				try{
					list = jsonToBean(json);
				}catch(com.alibaba.fastjson.JSONException e){
					break;
				}
			}
		} catch (Exception e) {
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
			synJob();
		}
	}
	
	/**
	 * 删除无用的优惠券信息
	 */
	public void deleteYouHuiQuan(){
		service.delete4EndTime();
	}

	public static void main(String[] args) {
		long start=System.currentTimeMillis();
		YouHuiquanJob b = new YouHuiquanJob();
		b.synJob();
		long end=System.currentTimeMillis();
		System.out.println("耗时："+(end-start)/1000+"秒");
	}
}
