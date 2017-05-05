package com.sub.syn.jzdy;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.sub.syn.common.BaseDao;
import com.sub.syn.youhuiquan.YouHuiQuan;

public class PJzdyService {
	
	private static SqlSessionFactory sqlSessionFactory=BaseDao.getSession();

	public List<PJzdy> selectTs(){
		List<PJzdy> list=null;
    	SqlSession session=sqlSessionFactory.openSession();
		try{
			PJzdyDao dao=session.getMapper(PJzdyDao.class);
			list=dao.selectTs();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			session.close();
		}
		return list;
    }
	
	/**
	 * 按关键词查询优惠券信息
	 * @param bean
	 * @return
	 */
	public YouHuiQuan selectByContent(PJzdy bean){
		YouHuiQuan yhq=new YouHuiQuan();
		SqlSession session=sqlSessionFactory.openSession();
		try{
			PJzdyDao dao=session.getMapper(PJzdyDao.class);
			yhq=dao.selectByContent(bean);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			session.close();
		}
		return yhq;
	}
	
	/**
	 * 修改下发提醒时间
	 * @param bean
	 */
	public void updateTxTime(PJzdy bean){
		SqlSession session=sqlSessionFactory.openSession();
		try{
			PJzdyDao dao=session.getMapper(PJzdyDao.class);
			dao.updateTxTime(bean);
			session.commit();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			session.close();
		}
	}
	
}
