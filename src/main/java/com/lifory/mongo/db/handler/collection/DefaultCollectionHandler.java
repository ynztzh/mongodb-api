package com.lifory.mongo.db.handler.collection;

import org.bson.conversions.Bson;

import com.lifory.mongo.db.callback.Callback;
import com.lifory.mongo.db.common.DBCP;
import com.lifory.mongo.db.handler.CollectionHandler;
import com.mongodb.async.SingleResultCallback;
import com.mongodb.async.client.MongoDatabase;

public class DefaultCollectionHandler extends CollectionHandler {

	@Override
	public void create(Callback<Void> callback, DBCP dbcp) {
		MongoDatabase database = getDatabase(dbcp);
		database.createCollection(dbcp.getCollection(), new SingleResultCallback<Void>() {

			@Override
			public void onResult(Void result, Throwable t) {
				callback(callback, result, t);
			}
			
		});
	}

	@Override
	public void delete(Callback<Void> callback, DBCP dbcp) {
		getCollection(dbcp).drop(new SingleResultCallback<Void>() {

			@Override
			public void onResult(Void result, Throwable t) {
				callback(callback, result, t);
			}
		});
	}

	@Override
	public void createIndex(Callback<String> callback, DBCP dbcp,Bson index) {
		getCollection(dbcp).createIndex(index,new SingleResultCallback<String>() {

			@Override
			public void onResult(String result, Throwable t) {
				callback(callback, result, t);
			}
			
		});
		
	}
	
	@Override
	public void deleteIndex(Callback<Void> callback, DBCP dbcp,Bson index) {
		getCollection(dbcp).dropIndex(index,new SingleResultCallback<Void>() {

			@Override
			public void onResult(Void result, Throwable t) {
				callback(callback, result, t);
			}

		});
	}
}
