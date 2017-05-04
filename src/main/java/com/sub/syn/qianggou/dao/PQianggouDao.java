package com.sub.syn.qianggou.dao;

import java.util.List;

import com.sub.syn.qianggou.entity.PQianggou;

public interface PQianggouDao {

    
    /**
     * 批量增加抢购商品
     * @param pCategorys
     */
    public void addBatch(List<PQianggou> pCategorys);
    
    public void updateBatch(List<PQianggou> pCategorys);
    
    public void insert(PQianggou pQianggou);
    
    /**
     * 获取某个时间段内是否有同步过
     * @param pQianggou
     * @return
     */
    public int getCount(PQianggou pQianggou);
    
    public void deleteByTime(PQianggou pQianggou);

}