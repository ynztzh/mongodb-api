# mongodb-api

#### 项目介绍
mongodb-api 3.6.3 异步客户端接口实现，maven依赖包如下：
```
<dependency>
    <groupId>org.mongodb</groupId>
    <artifactId>mongodb-driver-async</artifactId>
    <version>3.6.3</version>
</dependency>

```

#### 连接mongodb代码
http://mongodb.github.io/mongo-java-driver/3.6/driver-async/tutorials/connect-to-mongodb/

```

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


```

#### 代码测试

callback 可以为业务单独定制，handler支持泛型，调用sync则同步调用
```
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
		
handler.add(callback, 
     new DBCP().setDatabase("xtsj").setCollection("user"),
     new Person().setId(200).setName("张三").setPassword("123456")
);
		
callback.sync();
```


#### 调用说明

过滤器请关注Filters，聚合查询请关注Aggregates，索引创建请关注Indexes，这三个类提供了一系列好用的静态方法，方便大家构造查询参数，这些构造类由Mongodb客户端提供，具体使用请参照对应的文档：

http://mongodb.github.io/mongo-java-driver/3.6/driver-async/

