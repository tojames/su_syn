package com.sub.syn.jzdy;

import java.util.List;

import com.sub.syn.youhuiquan.YouHuiQuan;

public interface PJzdyDao {

	/***获取订阅信息**/
	public List<PJzdy> selectTs();
	
	/**
	 * 按关键词查询优惠券信息
	 * @param bean
	 * @return
	 */
	public YouHuiQuan selectByContent(PJzdy bean);
	
	/**
	 * 修改下发提醒时间
	 * @param bean
	 */
	public void updateTxTime(PJzdy bean);
}
