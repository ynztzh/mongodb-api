package com.lifory.mongo.db.handler.collection;

import java.util.Arrays;
import java.util.Map;

import org.bson.conversions.Bson;

import com.lifory.mongo.db.callback.Callback;
import com.lifory.mongo.db.common.DBCP;
import com.lifory.mongo.db.handler.DocumentHandler;
import com.mongodb.async.SingleResultCallback;
import com.mongodb.async.client.MongoCollection;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;

@SuppressWarnings("unchecked")
public abstract class DefaultDocumentHandler<T> extends DocumentHandler<T> {
	
	@Override
	public void add(Callback<Void> callback, DBCP dbcp, T ...ts) {
		
		if(ts.length <= 0) {
			throw new IllegalArgumentException("缺失参数，请传入需要保存的文档");
		}
		
		MongoCollection<T> collection = getTCollection(dbcp);
		collection.insertMany(Arrays.asList(ts), new SingleResultCallback<Void>() {

			@Override
			public void onResult(Void result, Throwable t) {
				callback(callback, result, t);
			}
			
		});
		
	}

	@Override
	public void delete(Callback<DeleteResult> callback, DBCP dbcp, Bson filter) {
		MongoCollection<T> collection = getTCollection(dbcp);
		collection.deleteMany(filter, new SingleResultCallback<DeleteResult>() {

			@Override
			public void onResult(DeleteResult result, Throwable t) {
				callback(callback, result, t);
			}
			
		});
		
	}
	
	@Override
	public void deleteAndGet(Callback<T> callback, DBCP dbcp, Bson filter) {
		MongoCollection<T> collection = getTCollection(dbcp);
		collection.findOneAndDelete(filter,new SingleResultCallback<T>() {

			@Override
			public void onResult(T result, Throwable t) {
				callback(callback, result, t);
			}
			
		});
	}

	@Override
	public void replace(Callback<UpdateResult> callback, DBCP dbcp, Bson filter, T t) {
		MongoCollection<T> collection = getTCollection(dbcp);
		collection.replaceOne(filter, t, new SingleResultCallback<UpdateResult>() {

			@Override
			public void onResult(UpdateResult result, Throwable t) {
				callback(callback, result, t);
			}
			
		});
		
	}

	@Override
	public void update(Callback<UpdateResult> callback, DBCP dbcp, Bson filter, Map<String,?> update) {
		MongoCollection<T> collection = getTCollection(dbcp);
		
		collection.updateMany(filter, getBson(update), new SingleResultCallback<UpdateResult>() {
			
			@Override
			public void onResult(UpdateResult result, Throwable t) {
				callback(callback, result, t);
			}
			
		});
		
	}
}
