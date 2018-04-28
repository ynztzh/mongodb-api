package com.lifory.mongo.db.handler;

import java.util.Map;

import org.bson.conversions.Bson;

import com.lifory.mongo.db.callback.Callback;
import com.lifory.mongo.db.common.DBCP;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;

/**
 * 文档操作接口
 */
@SuppressWarnings("unchecked")
public abstract class DocumentHandler<T> extends AbstractHandler<T> {

	/**
	 * 批量保存数据
	 * @param callback
	 * @param dbcp
	 * @param docs
	 */
	public abstract void add(Callback<Void> callback,DBCP dbcp,T ...ts);
	
	/**
	 * 删除文档
	 * @param callback
	 * @param dbcp
	 * @param filter
	 */
	public abstract void delete(Callback<DeleteResult> callback,DBCP dbcp,Bson filter);
	
	/**
	 * 删除文档
	 * @param callback
	 * @param dbcp
	 * @param filter
	 * @param t
	 */
	public abstract void deleteAndGet(Callback<T> callback,DBCP dbcp,Bson filter);
	
	/**
	 * 替换文档
	 * @param callback
	 * @param dbcp
	 * @param filter
	 * @param t
	 */
	public abstract void replace(Callback<UpdateResult> callback,DBCP dbcp,Bson filter,T t);
	
	/**
	 * 更新文档
	 * @param callback
	 * @param dbcp
	 * @param filter
	 * @param update
	 */
	public abstract void update(Callback<UpdateResult> callback,DBCP dbcp,Bson filter, Map<String,?> update);
}
