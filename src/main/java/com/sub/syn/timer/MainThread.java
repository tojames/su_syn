package com.sub.syn.timer;

import java.util.Date;
import java.util.LinkedList;

import com.sub.syn.common.DateUtils;
import com.sub.syn.qianggou.entity.PQianggou;
import com.sub.syn.qianggou.entity.TimeQueue;
import com.sub.syn.qianggou.service.PQianggouService;

public class MainThread extends Thread {

	public static void main(String[] args) {
		
		LinkedList<String> list = DateUtils.getHours(new Date());
		String time=list.get(0);
		PQianggou bean=new PQianggou();
		bean.setStartTime(time.split("=")[0]);
		bean.setEndTime(time.split("=")[1]);
		int count=new PQianggouService().getCount(bean);
		TimeQueue.type=count;
		System.out.println("数量："+count);
		TimeQueue.queue = list;
		for (int i = 0; i <=10; i++) {
			new SynQiangGouThread().start();
		}
	}
}
