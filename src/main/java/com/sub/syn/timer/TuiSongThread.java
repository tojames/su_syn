package com.sub.syn.timer;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sub.syn.common.MessageTools;
import com.sub.syn.jzdy.PJzdy;
import com.sub.syn.jzdy.PJzdyService;
import com.sub.syn.youhuiquan.YouHuiQuan;
import com.taobao.api.ApiException;

/**
 * 推送订阅用户的优惠券信息
 * @author wuyb
 *
 */
public class TuiSongThread extends Thread{
	
	private Log log=LogFactory.getLog(TuiSongThread.class);
	
	private PJzdyService service=new PJzdyService();
	
	private YouHuiQuan yhq=new YouHuiQuan();

	public void run(){
		while(true){
			log.info("定时下发提醒短信");
			List<PJzdy> list=service.selectTs();
			for(PJzdy bean:list){
				yhq=service.selectByGoodsId(bean);
				//下发短信给用户
				if(null!=yhq){
					MessageTools.sendShortMsg(bean.getMobile(), bean.getContent(), yhq.getGoodsId()+"");
					//修改下发提醒时间
					bean.setTxTime(new Date());
					service.updateTxTime(bean);
				}
			}
			try {
				TimeUnit.SECONDS.sleep(120);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) throws ApiException {
		TuiSongThread tt=new TuiSongThread();
		tt.start();
	}
}
