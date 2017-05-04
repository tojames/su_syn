package com.sub.syn.qianggou.entity;

import java.util.LinkedList;
import java.util.Queue;

public class TimeQueue {
	
	public static Queue<String> queue = new LinkedList<String>();
	
	/**
	 * 操作标志 0新增  大于0修改
	 */
	public static int type=0;

}
