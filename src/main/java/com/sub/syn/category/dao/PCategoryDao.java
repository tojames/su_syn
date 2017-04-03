package com.sub.syn.category.dao;

import java.util.List;

import com.sub.syn.category.entity.PCategory;

/**
 * 分类
 * @author wuyb
 *
 */
public interface PCategoryDao {

	 public PCategory selectUserByID(String id);
	 
	 /**
	  * 新增一条分类记录
	  * @param pCategory
	  */
	 public void insertPCategory(PCategory pCategory);
	 
	 public void addPCategoryBatch(List<PCategory> pCategorys);
}
