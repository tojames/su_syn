package com.sub.syn.category.service;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.sub.syn.category.dao.PCategoryDao;
import com.sub.syn.category.entity.PCategory;
import com.sub.syn.common.BaseDao;

/**
 * 保存分类
 * @author wuyb
 *
 */
public class SaveCategory {
	
	private static SqlSessionFactory sqlSessionFactory=BaseDao.getSession();

	public static void saveBath(List<PCategory> list){
		SqlSession session = sqlSessionFactory.openSession();
        try {
        	PCategoryDao dao=session.getMapper(PCategoryDao.class);
        	dao.addPCategoryBatch(list);
        	session.commit();
        } finally {
            session.close();
        }
	}
	
	public static void save(PCategory bean){
		SqlSession session = sqlSessionFactory.openSession();
        try {
        	PCategoryDao dao=session.getMapper(PCategoryDao.class);
        	dao.insertPCategory(bean);
        	session.commit();
        } finally {
            session.close();
        }
	}
	
	public static void main(String[] args){
		PCategory bean=new PCategory();
		bean.setIcon("11111");
		bean.setName("测试");
		bean.setId("324234");
		save(bean);
	}
}
