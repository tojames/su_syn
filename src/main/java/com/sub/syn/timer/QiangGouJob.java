package com.sub.syn.timer;

import java.util.Date;
import java.util.LinkedList;

import com.sub.syn.common.DateUtils;
import com.sub.syn.qianggou.entity.PQianggou;
import com.sub.syn.qianggou.entity.TimeQueue;
import com.sub.syn.qianggou.service.PQianggouService;

/**
 * 抢购商品同步
 * @author wuyb
 *
 */
public class QiangGouJob  {
	
	private PQianggouService qservice=new PQianggouService();

	public void syn() {
		
		LinkedList<String> list = DateUtils.getHours(new Date());
		String time=list.get(0);
		String endTime=list.get(list.size()-1);
		PQianggou bean=new PQianggou();
		bean.setStartTime(time.split("=")[0]);
		bean.setEndTime(endTime.split("=")[1]);
		int count=new PQianggouService().getCount(bean);
		TimeQueue.type=count;
		System.out.println("数量："+count);
		TimeQueue.queue = list;
		for (int i = 0; i <=10; i++) {
			new SynQiangGouThread().start();
		}
	}
	
	/**
	 * 删除无用的抢购商品
	 */
	public void deleteQiangGou(){
		PQianggou bean=new PQianggou();
		String endTime=DateUtils.getDate("yyyy-MM-dd HH:mm:ss");
		bean.setEndTime(endTime);
		qservice.deleteByTime(bean);
	} 
	
	public static void main(String[] args){
		QiangGouJob dd=new QiangGouJob();
		//dd.deleteQiangGou();
		
		dd.syn();
	}
}
