package com.sub.syn.youhuiquan;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.sub.syn.common.BaseDao;

/**
 * 抢购service
 * @author wuyb
 *
 */
public class PYouHuiQuanService {
	
	private static SqlSessionFactory sqlSessionFactory=BaseDao.getSession();
	
	public static SqlSession getSession(){
		return  sqlSessionFactory.openSession();
	}

	/**
	 * 批量保存抢购商品信息
	 * @param list
	 */
	public void saveBath(List<YouHuiQuan> list){
		SqlSession session = sqlSessionFactory.openSession();
        try {
        	PYouHuiQuanDao dao=session.getMapper(PYouHuiQuanDao.class);
        	dao.addBatch(list);
        	session.commit();
        } finally {
            session.close();
        }
	}
	
	public void insert(YouHuiQuan bean){
		SqlSession session = sqlSessionFactory.openSession();
        try {
        	PYouHuiQuanDao dao=session.getMapper(PYouHuiQuanDao.class);
        	dao.insert(bean);
        	session.commit();
        } finally {
            session.close();
        }
	}
	
	/**
	 * 批量保存抢购商品信息
	 * @param list
	 */
	public void updateBath(List<YouHuiQuan> list){
		SqlSession session = sqlSessionFactory.openSession();
        try {
        	PYouHuiQuanDao dao=session.getMapper(PYouHuiQuanDao.class);
        	dao.updateBatch(list);
        	session.commit();
        } finally {
            session.close();
        }
	}
	
	public int getCount(YouHuiQuan bean){
		SqlSession session = sqlSessionFactory.openSession();
        try {
        	PYouHuiQuanDao dao=session.getMapper(PYouHuiQuanDao.class);
        	return dao.getCount(bean);
        } finally {
            session.close();
        }
	}
	
	/**
	 * 保存抢购信息
	 * @param bean
	 */
	public void save(YouHuiQuan bean){
		SqlSession session=sqlSessionFactory.openSession();
		try{
			PYouHuiQuanDao dao=session.getMapper(PYouHuiQuanDao.class);
			dao.insert(bean);
			session.commit();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			session.close();
		}
	}
	
	public void save(YouHuiQuan bean,SqlSession session){
		try{
			PYouHuiQuanDao dao=session.getMapper(PYouHuiQuanDao.class);
			dao.insert(bean);
			session.commit();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/***saveOrUpdate**/
    public void saveOrUpdate(YouHuiQuan bean){
    	SqlSession session=sqlSessionFactory.openSession();
		try{
			PYouHuiQuanDao dao=session.getMapper(PYouHuiQuanDao.class);
			dao.saveOrUpdate(bean);
			session.commit();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			session.close();
		}
    }
    
    /**
     * 查询
     * @param bean
     * @return
     */
    public YouHuiQuan select (YouHuiQuan bean){
    	YouHuiQuan bean1=new YouHuiQuan();
    	SqlSession session=sqlSessionFactory.openSession();
		try{
			PYouHuiQuanDao dao=session.getMapper(PYouHuiQuanDao.class);
			bean1=dao.select(bean);
			session.commit();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			session.close();
		}
		return bean1;
    }
    
    /**
     * 查询
     * @param bean
     * @return
     */
    public YouHuiQuan select (YouHuiQuan bean,SqlSession session){
    	YouHuiQuan bean1=new YouHuiQuan();
		try{
			PYouHuiQuanDao dao=session.getMapper(PYouHuiQuanDao.class);
			bean1=dao.select(bean);
			session.commit();
		}catch(Exception e){
			e.printStackTrace();
		}
		return bean1;
    }
	
    public void update(YouHuiQuan bean){
    	SqlSession session=sqlSessionFactory.openSession();
		try{
			PYouHuiQuanDao dao=session.getMapper(PYouHuiQuanDao.class);
			dao.update(bean);
			session.commit();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			session.close();
		}
    }
    
    public void update(YouHuiQuan bean,SqlSession session){
		try{
			PYouHuiQuanDao dao=session.getMapper(PYouHuiQuanDao.class);
			dao.update(bean);
			session.commit();
		}catch(Exception e){
			e.printStackTrace();
		}
    }
    
    public void deleteByIds(List<YouHuiQuan> id){
    	SqlSession session=sqlSessionFactory.openSession();
		try{
			PYouHuiQuanDao dao=session.getMapper(PYouHuiQuanDao.class);
			dao.deleteByIds(id);
			session.commit();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			session.close();
		}
    }
    
    public void deleteByIdsTbjd(List<YouHuiQuan> id){
    	SqlSession session=sqlSessionFactory.openSession();
		try{
			PYouHuiQuanDao dao=session.getMapper(PYouHuiQuanDao.class);
			dao.deleteByIdsTbjd(id);
			session.commit();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			session.close();
		}
    }
    
    /***清除无用的优惠券信息**/
    public void delete4EndTime(){
    	SqlSession session=sqlSessionFactory.openSession();
		try{
			PYouHuiQuanDao dao=session.getMapper(PYouHuiQuanDao.class);
			dao.delete4EndTime();
			session.commit();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			session.close();
		}
    }
	
	public static void main(String[] args){
//		List<YouHuiQuan> list=new ArrayList<YouHuiQuan>();
//		YouHuiQuan bean=new YouHuiQuan();
//		bean.setId(9);
//		bean.setTitle("2222");
//		list.add(bean);
//		//new PYouHuiQuanService().insert(bean);
//		new PYouHuiQuanService().saveBath(list);
		
		new PYouHuiQuanService().delete4EndTime();
	}
}
