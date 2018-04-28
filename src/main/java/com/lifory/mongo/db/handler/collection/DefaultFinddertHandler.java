package com.lifory.mongo.db.handler.collection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import org.bson.conversions.Bson;

import com.lifory.mongo.db.callback.Callback;
import com.lifory.mongo.db.common.DBCP;
import com.lifory.mongo.db.common.Pageable;
import com.lifory.mongo.db.common.Pageable.Sort;
import com.lifory.mongo.db.common.SortType;
import com.lifory.mongo.db.handler.FindderHandler;
import com.mongodb.Block;
import com.mongodb.async.SingleResultCallback;
import com.mongodb.async.client.AggregateIterable;
import com.mongodb.async.client.ChangeStreamIterable;
import com.mongodb.async.client.DistinctIterable;
import com.mongodb.async.client.FindIterable;
import com.mongodb.async.client.MongoCollection;
import com.mongodb.client.model.Sorts;
import com.mongodb.client.model.changestream.ChangeStreamDocument;

public abstract class DefaultFinddertHandler<T> extends FindderHandler<T> {
	
	@Override
	public void find(Callback<List<T>> callback, DBCP dbcp, Bson filter, Pageable pageable) {
		
		if(Objects.isNull(pageable)) {
			throw new IllegalArgumentException("非法参数，请传入分页对象");
		}
		
		MongoCollection<T> collection = getTCollection(dbcp);
		FindIterable<T> iterable;
		
		//Filter
		if(Objects.isNull(filter)) {
			iterable = collection.find();
		} else {
			iterable = collection.find(filter);
		}
		
		//配置分页对象
		setPageable(pageable, iterable);
		
		//打包数据
		packages(callback, iterable);
	}
	
	//打包数据
	private void packages(Callback<List<T>> callback, FindIterable<T> iterable) {
		List<T> datas = new ArrayList<>();
		iterable.forEach(new Block<T>() {

			@Override
			public void apply(T t) {
				datas.add(t);
			}
			
		}, new SingleResultCallback<Void>() {

			@Override
			public void onResult(Void result, Throwable t) {
				callback(callback, datas, t);
			}
			
		});
	}

	@Override
	public void get(Callback<T> callback, DBCP dbcp, Bson filter) {
		MongoCollection<T> collection = getTCollection(dbcp);
		FindIterable<T> find = collection.find(filter);
		
		find.first(new SingleResultCallback<T>() {

			@Override
			public void onResult(T result, Throwable t) {
				callback(callback, result, t);
			}
			
		});
	}
	
	//配置分页对象
	private void setPageable(Pageable pageable, FindIterable<T> iterable) {
		//Limit
		iterable.limit(pageable.getLimit() > 0?pageable.getLimit():20);
		
		//Sort
		Bson[] endings = new Bson[pageable.getSort().size()];
		for (int i=0;i<endings.length;i++) {
			
			Sort sort = pageable.getSort().get(i);
			
			Bson ending = null;
			if(SortType.ASC.equals(sort.getType())) {
				ending = Sorts.ascending(sort.getField());
			} else if(SortType.DESC.equals(sort.getType())) {
				ending = Sorts.descending(sort.getField());
			}
			
			endings[i] = ending;
		}
		
		//Sort
		if(endings.length > 0) {
			Bson sortBson = Sorts.orderBy(endings);
			iterable.sort(Sorts.orderBy(sortBson));
		}
		
		//Skip
		if(pageable.getSkip() > 0) {
			iterable.skip(pageable.getSkip());
		}
	}
	
	@Override
	public void count(Callback<Long> callback, DBCP dbcp,Bson filter) {
		MongoCollection<T> collection = getTCollection(dbcp);
		
		if(Objects.isNull(filter)) {
			collection.count(new SingleResultCallback<Long>() {

				@Override
				public void onResult(Long result, Throwable t) {
					callback(callback, result, t);
				}
				
			});
		} else {
			collection.count(filter, new SingleResultCallback<Long>() {

				@Override
				public void onResult(Long result, Throwable t) {
					callback(callback, result, t);
				}
				
			});
		}
		
	}

	@Override
	public void aggregration(Callback<List<T>> callback, DBCP dbcp, Bson ...pipeline) {
		
		MongoCollection<T> collection = getTCollection(dbcp);
		AggregateIterable<T> aggregate = collection.aggregate(Arrays.asList(pipeline));
		aggregate.allowDiskUse(true);
		
		List<T> datas = new ArrayList<>();
		aggregate.forEach(new Block<T>() {

			@Override
			public void apply(T t) {
				datas.add(t);
			}
			
		}, new SingleResultCallback<Void>() {

			@Override
			public void onResult(Void result, Throwable t) {
				callback(callback, datas, t);
			}
			
		});
	}
	
	@Override
	public <R> void aggregration(Callback<List<R>> callback, DBCP dbcp, Class<R> clz, Bson... pipeline) {
		MongoCollection<T> collection = getTCollection(dbcp);
		AggregateIterable<R> aggregate = collection.aggregate(Arrays.asList(pipeline),clz);
		aggregate.allowDiskUse(true);
		
		List<R> datas = new ArrayList<>();
		aggregate.forEach(new Block<R>() {

			@Override
			public void apply(R t) {
				datas.add(t);
			}
			
		}, new SingleResultCallback<Void>() {

			@Override
			public void onResult(Void result, Throwable t) {
				callback(callback, datas, t);
			}
			
		});
	}

	@Override
	public <R> void distinct(Callback<List<R>> callback, DBCP dbcp, Bson filter, String field, Class<R> clz) {
		MongoCollection<T> collection = getTCollection(dbcp);
		DistinctIterable<R> distinct = collection.distinct(field, filter, clz);
		
		List<R> datas = new ArrayList<>();
		distinct.forEach(new Block<R>() {
			@Override
			public void apply(R t) {
				datas.add(t);
			}
		}, new SingleResultCallback<Void>() {

			@Override
			public void onResult(Void result, Throwable t) {
				callback(callback, datas, t);
			}
		});
	}
	
	@Override
	public void watch(Callback<ChangeStreamDocument<T>> callback, DBCP dbcp) {
		MongoCollection<T> collection = getTCollection(dbcp);
		ChangeStreamIterable<T> watch = collection.watch();
		
		List<ChangeStreamDocument<T>> datas = new ArrayList<>();
		watch.forEach(new Block<ChangeStreamDocument<T>>() {

			@Override
			public void apply(ChangeStreamDocument<T> t) {
				datas.add(t);
			}
			
		}, new SingleResultCallback<Void>() {
			
			@Override
			public void onResult(Void result, Throwable t) {
				callback(callback, datas, t);
			}
			
		});
	}
}
