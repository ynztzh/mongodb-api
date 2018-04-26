package com.lifory.mongo.db;

import com.lifory.mongo.db.callback.AbstractCallback;
import com.lifory.mongo.db.common.DBCP;
import com.lifory.mongo.db.handler.CollectionHandler;
import com.lifory.mongo.db.handler.DocumentHandler;
import com.lifory.mongo.db.handler.collection.DefaultCollectionHandler;
import com.lifory.mongo.db.handler.collection.DefaultDocumentHandler;
import com.lifory.mongo.db.param.Person;

/**
 * Hello world!
 *
 */
public class App {
	
	
	/**
	 * 
	 * index        db
	 * collection   table
	 * document     row
	 * ros          field
	 * @throws Exception 
	 * 
	 */
	public static void main(String[] args) throws Exception {
		DocumentHandler<Person> handler = new DefaultDocumentHandler<Person>() {};
		AbstractCallback<Void> callback = new AbstractCallback<Void>() {

			@Override
			public void successful(Void response) {
				
			}

			@Override
			public void failure(Throwable t) {
				t.printStackTrace();
			}
			
		};
		
		handler.add(callback, new DBCP().setDatabase("xtsj").setCollection("user"), new Person().setId(200).setName("张三").setPassword("123456"));
		
		callback.sync();
	}
	
	public void testCollection() {
		CollectionHandler handler = new DefaultCollectionHandler();
		
		AbstractCallback<Void> callback = new AbstractCallback<Void>() {

			@Override
			public void successful(Void response) {
				
			}

			@Override
			public void failure(Throwable t) {
				t.printStackTrace();
			}
			
		};
		
		handler.create(callback, new DBCP().setDatabase("xtsj").setCollection("User"));
		callback.sync();
	}
	
}
