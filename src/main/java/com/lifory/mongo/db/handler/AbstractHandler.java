package com.lifory.mongo.db.handler;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

import java.lang.reflect.ParameterizedType;
import java.util.Map;
import java.util.Objects;

import org.bson.Document;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.conversions.Bson;

import com.lifory.mongo.db.callback.Callback;
import com.lifory.mongo.db.client.Client;
import com.lifory.mongo.db.common.Closable;
import com.lifory.mongo.db.common.DBCP;
import com.mongodb.async.client.MongoClient;
import com.mongodb.async.client.MongoClients;
import com.mongodb.async.client.MongoCollection;
import com.mongodb.async.client.MongoDatabase;

/**
 * 共用操作接口
 */
public abstract class AbstractHandler<T> implements Closable {
	
	/**
	 * 获取泛型类
	 */
	@SuppressWarnings("unchecked")
	protected Class<T> getT() {
		return (Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}
	
	/**
	 * 获取文档操作API
	 */
	private MongoClient getClient() {
		return Client.get();
	}
	
	/**
	 * 构建Bson
	 * @return
	 */
	protected Bson getBson(Map<String,?> values) {
		Document document = new Document();
		document.putAll(values);
		return document;
	}

	/**
	 * 获取操作数据库
	 * 
	 * @param name
	 * @return
	 */
	protected MongoDatabase getDatabase(DBCP dbcp) {
		
		if(Objects.isNull(dbcp.getDatabase())) {
			throw new IllegalArgumentException("缺失参数：database");
		}
		
		return getClient().getDatabase(dbcp.getDatabase())
				.withCodecRegistry(fromRegistries(MongoClients.getDefaultCodecRegistry(),
						fromProviders(PojoCodecProvider.builder().automatic(true).build())));
	}

	/**
	 * 获取集合
	 * 
	 * @param dbcp
	 * @return
	 */
	protected MongoCollection<?> getCollection(DBCP dbcp) {
		
		if(Objects.isNull(dbcp.getCollection())) {
			throw new IllegalArgumentException("缺失参数：collection");
		}
		
		return (MongoCollection<?>) getDatabase(dbcp).getCollection(dbcp.getCollection());
	}

	/**
	 * 获取集合
	 * @param dbcp
	 * @return
	 */
	protected MongoCollection<T> getTCollection(DBCP dbcp) {
		if(Objects.isNull(dbcp.getCollection())) {
			throw new IllegalArgumentException("缺失参数：collection");
		}
		return (MongoCollection<T>) getDatabase(dbcp).getCollection(dbcp.getCollection(), getT());
	}

	/**
	 * 执行回调
	 * 
	 * @param callback
	 * @param result
	 * @param t
	 */
	protected void callback(Callback callback, Object result, Throwable t) {
		if (Objects.isNull(t)) {
			callback.onResponse(result);
		} else {
			callback.onFailure(t);
		}
	}

	/**
	 * 关闭客户端
	 */
	public void close() {
		try {
			Client.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
