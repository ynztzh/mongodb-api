package com.lifory.mongo.db.common;

/**
 * 数据访问公共参数
 */
public class DBCP {

	private String database;
	private String collection;

	public String getDatabase() {
		return database;
	}

	public DBCP setDatabase(String database) {
		this.database = database;
		return this;
	}

	public String getCollection() {
		return collection;
	}

	public DBCP setCollection(String collection) {
		this.collection = collection;
		return this;
	}

}
