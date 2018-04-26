package com.lifory.mongo.db.handler;

import org.bson.conversions.Bson;

import com.lifory.mongo.db.callback.Callback;
import com.lifory.mongo.db.common.DBCP;

public abstract class CollectionHandler extends AbstractHandler<Object> {

	/**
	 * 创建集合
	 * @param callback
	 * @param dbcp
	 */
	public abstract void create(Callback callback,DBCP dbcp);
	
	/**
	 * 删除集合集合
	 * @param callback
	 * @param name
	 */
	public abstract void delete(Callback callback,DBCP dbcp);
	
	/**
	 * 创建集合索引
	 * @param callback
	 * @param dbcp
	 * @param index (Indexes) http://www.runoob.com/mongodb/mongodb-indexing.html
	 */
	public abstract void createIndex(Callback callback,DBCP dbcp,Bson index);
	
	/**
	 * 删除集合索引
	 * @param callback
	 * @param dbcp
	 * @param index (Indexes) http://www.runoob.com/mongodb/mongodb-indexing.html
	 */
	public abstract void deleteIndex(Callback callback,DBCP dbcp,Bson index);
}
