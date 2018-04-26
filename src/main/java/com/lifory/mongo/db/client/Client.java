package com.lifory.mongo.db.client;

import com.mongodb.ConnectionString;
import com.mongodb.async.client.MongoClient;
import com.mongodb.async.client.MongoClients;

public final class Client {

	private static MongoClient client;

	/**
	 * 获取客户端
	 */
	public static MongoClient get() {

		if (client == null) {
			client = MongoClients.create(new ConnectionString("mongodb://localhost:27017"));
		}

		return client;
	}

	/**
	 * 关闭客户端
	 * 
	 * @throws Exception
	 */
	public static void close() throws Exception {
		if (client != null) {
			client.close();
			client = null;
		}
	}
}
