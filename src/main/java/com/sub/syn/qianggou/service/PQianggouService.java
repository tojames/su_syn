package com.sub.syn.qianggou.service;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.sub.syn.common.BaseDao;
import com.sub.syn.qianggou.dao.PQianggouDao;
import com.sub.syn.qianggou.entity.PQianggou;

/**
 * 抢购service
 * @author wuyb
 *
 */
public class PQianggouService {
	
	private static SqlSessionFactory sqlSessionFactory=BaseDao.getSession();

	/**
	 * 批量保存抢购商品信息
	 * @param list
	 */
	public void saveBath(List<PQianggou> list){
		SqlSession session = sqlSessionFactory.openSession();
        try {
        	PQianggouDao dao=session.getMapper(PQianggouDao.class);
        	dao.addBatch(list);
        	session.commit();
        } finally {
            session.close();
        }
	}
	
	/**
	 * 批量保存抢购商品信息
	 * @param list
	 */
	public void updateBath(List<PQianggou> list){
		SqlSession session = sqlSessionFactory.openSession();
        try {
        	PQianggouDao dao=session.getMapper(PQianggouDao.class);
        	dao.updateBatch(list);
        	session.commit();
        } finally {
            session.close();
        }
	}
	
	public int getCount(PQianggou bean){
		SqlSession session = sqlSessionFactory.openSession();
        try {
        	PQianggouDao dao=session.getMapper(PQianggouDao.class);
        	return dao.getCount(bean);
        } finally {
            session.close();
        }
	}
	
	/**
	 * 保存抢购信息
	 * @param bean
	 */
	public void save(PQianggou bean){
		SqlSession session=sqlSessionFactory.openSession();
		try{
			PQianggouDao dao=session.getMapper(PQianggouDao.class);
			dao.insert(bean);
			session.commit();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			session.close();
		}
	}
	
	public void deleteByTime(PQianggou bean){
		SqlSession session=sqlSessionFactory.openSession();
		try{
			PQianggouDao dao=session.getMapper(PQianggouDao.class);
			dao.deleteByTime(bean);
			session.commit();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			session.close();
		}
	}
	
	public static void main(String[] args){
		PQianggou bean=new PQianggou();
		bean.setStartTime("2017-05-12 20:00:00");
		bean.setEndTime("2017-05-12 21:00:00");
		new PQianggouService().deleteByTime(bean);
	}
}
