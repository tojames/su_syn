package com.sub.syn.timer;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sub.syn.common.Config;
import com.sub.syn.common.DateUtils;
import com.sub.syn.youhuiquan.YouHuiquanJob;
import com.sub.syn.youhuiquan.YouHuiquanTKJDJob;

public class Task {
	
	private static Log log=LogFactory.getLog(Task.class);
	
	/** 
	 * 获取指定时间对应的毫秒数 
	 * @param time "HH:mm:ss" 
	 * @return 
	 */  
	private static long getTimeMillis(String time) {  
	    try {  
	        DateFormat dateFormat = new SimpleDateFormat("yy-MM-dd HH:mm:ss");  
	        DateFormat dayFormat = new SimpleDateFormat("yy-MM-dd");  
	        Date curDate = dateFormat.parse(dayFormat.format(new Date()) + " " + time);  
	        return curDate.getTime();  
	    } catch (ParseException e) {  
	        e.printStackTrace();
	    }  
	    return 0;  
	} 

	public static void main(String[] args) {
		String hours=DateUtils.getDate("yyyyMMdd HH").split(" ")[1];
		//同步优惠商品
		Runnable runnable = new Runnable() {
			public void run() {
				//2点到6点不同步
				if(Integer.parseInt(hours)<=2||Integer.parseInt(hours)>=6){
					// 优惠券信息同步
					log.info("开始同步优惠券信息");
					YouHuiquanJob b = new YouHuiquanJob();
					b.synJob();
					
					YouHuiquanTKJDJob yt=new YouHuiquanTKJDJob();
					yt.synJob();
					
					//同步抢购商品 淘宝
					QiangGouJob q=new QiangGouJob();
					q.syn();
				}
			}
		};
		
		/***删除无用的优惠信息***/
		Runnable runnable1 = new Runnable() {
			public void run() {
				// 优惠券信息同步
				log.info("开始删除无用的优惠券信息");
				YouHuiquanJob b = new YouHuiquanJob();
				b.deleteYouHuiQuan();
				
				//删除无用的抢购商品
				log.info("开始删除无用的抢购信息");
				QiangGouJob qg=new QiangGouJob();
				qg.deleteQiangGou();
			}
		};
		
		ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
		//晚上八点执行
	    long initDelay  = getTimeMillis(Config.getProperty("start_time")) - System.currentTimeMillis();
	    initDelay=initDelay/1000/60;
	    //System.out.println("initDelay:"+initDelay);
	    long oneDay = Long.parseLong(Config.getProperty("exec_rate")) ;//间隔时间 单位：分钟
	    initDelay = initDelay > 0 ? initDelay : oneDay + initDelay;  
		// 第二个参数为首次执行的延时时间，第三个参数为定时执行的间隔时间
		service.scheduleAtFixedRate(runnable, initDelay, oneDay, TimeUnit.MINUTES);
		
		//删除无用数据
		long initDelay1  = getTimeMillis(Config.getProperty("delete_start_time")) - System.currentTimeMillis();
		initDelay1=initDelay1/1000/60;
	    //System.out.println("initDelay:"+initDelay);
		long oneDay1 = Long.parseLong(Config.getProperty("delete_exec_rate")) ;//间隔时间 单位：天
		initDelay1 = initDelay1 > 0 ? initDelay1 : oneDay1 + initDelay1;  
		// 第二个参数为首次执行的延时时间，第三个参数为定时执行的间隔时间
		service.scheduleAtFixedRate(runnable1, initDelay1, oneDay1, TimeUnit.MINUTES);
		
		//启动定时下发短信提醒的功能
		log.info("启动下发短信功能");
		System.out.println("启动下发短信功能");
		TuiSongThread tt=new TuiSongThread();
		tt.start();
	}
}
