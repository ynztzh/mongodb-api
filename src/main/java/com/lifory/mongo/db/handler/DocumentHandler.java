package com.lifory.mongo.db.handler;

import java.util.Map;

import org.bson.conversions.Bson;

import com.lifory.mongo.db.callback.Callback;
import com.lifory.mongo.db.common.DBCP;

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
	public abstract void add(Callback callback,DBCP dbcp,T ...ts);
	
	/**
	 * 删除文档
	 * @param callback
	 * @param dbcp
	 * @param filter
	 */
	public abstract void delete(Callback callback,DBCP dbcp,Bson filter);
	
	/**
	 * 删除文档
	 * @param callback
	 * @param dbcp
	 * @param filter
	 * @param t
	 */
	public abstract void deleteAndGet(Callback callback,DBCP dbcp,Bson filter);
	
	/**
	 * 替换文档
	 * @param callback
	 * @param dbcp
	 * @param filter
	 * @param t
	 */
	public abstract void replace(Callback callback,DBCP dbcp,Bson filter,T t);
	
	/**
	 * 更新文档
	 * @param callback
	 * @param dbcp
	 * @param filter
	 * @param update
	 */
	public abstract void update(Callback callback,DBCP dbcp,Bson filter, Map<String,?> update);
}
