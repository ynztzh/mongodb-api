package com.lifory.mongo.db.handler;

import org.bson.conversions.Bson;

import com.lifory.mongo.db.callback.Callback;
import com.lifory.mongo.db.common.DBCP;
import com.lifory.mongo.db.common.Pageable;

/**
 * 文档查询接口
 */
public abstract class FindderHandler<T> extends AbstractHandler<T> {

	/**
	 * 统计文档数
	 * 
	 * @param callback
	 * @param dbcp
	 * @param filter  (Filters)http://www.runoob.com/mongodb/mongodb-operators.html
	 */
	public abstract void count(Callback callback, DBCP dbcp, Bson filter);

	/**
	 * 检索文档
	 * 
	 * @param callback
	 * @param dbcp
	 * @param filter  (Filters)http://www.runoob.com/mongodb/mongodb-operators.html
	 */
	public abstract void find(Callback callback, DBCP dbcp, Bson filter, Pageable pageable);

	/**
	 * 获取单个文档
	 * 
	 * @param callback
	 * @param dbcp
	 * @param filter  (Filters)http://www.runoob.com/mongodb/mongodb-operators.html
	 */
	public abstract void get(Callback callback, DBCP dbcp, Bson filter);

	/**
	 * 聚合查询
	 * @param callback
	 * @param dbcp
	 * @param pipeline(Aggregates+Filters) http://www.runoob.com/mongodb/mongodb-aggregate.html
	 */
	public abstract void aggregration(Callback callback, DBCP dbcp, Bson ...pipeline);
	
	/**
	 * 聚合查询
	 * @param callback
	 * @param dbcp
	 * @param pipeline(Aggregates+Filters) http://www.runoob.com/mongodb/mongodb-aggregate.html
	 */
	public abstract <R>void aggregration(Callback callback, DBCP dbcp, Class<R> clz, Bson ...pipeline);

	/**
	 * 获取某字段不重复值
	 * 
	 * @param callback
	 * @param dbcp
	 * @param filter  (Filters)http://www.runoob.com/mongodb/mongodb-operators.html
	 * @apram clz     R Type
	 */
	public abstract <R> void distinct(Callback callback, DBCP dbcp, Bson filter, String field, Class<R> clz);
	
	/**
	 * 监控Collection变化
	 * @param callback
	 * @param dbcp
	 */
	public abstract void watch(Callback callback, DBCP dbcp);
}
