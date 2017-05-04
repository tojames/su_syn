package com.sub.syn.timer;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.sub.syn.common.Config;
import com.sub.syn.youhuiquan.YouHuiquanJob;

public class Task {
	
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
		Runnable runnable = new Runnable() {
			public void run() {
				// task to run goes here
				System.out.println("Hello !!"+new Date());
				YouHuiquanJob b = new YouHuiquanJob();
				b.synJob();
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
		
		
	}
}
