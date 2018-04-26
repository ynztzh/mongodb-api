package com.lifory.mongo.db.common;

import java.util.ArrayList;
import java.util.List;

public class Pageable {

	/**
	 * 查询起始
	 */
	private int skip;

	/**
	 * 查询条数
	 */
	private int limit;

	/**
	 * 排序字段
	 */
	private List<Sort> sort = new ArrayList<>();

	public int getSkip() {
		return skip;
	}

	public void setSkip(int skip) {
		this.skip = skip;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public List<Sort> getSort() {
		return sort;
	}

	public void addSort(Sort sort) {
		this.sort.add(sort);
	}

	public class Sort {

		private String[] field;
		private SortType type;

		public String[] getField() {
			return field;
		}

		public void setField(String[] field) {
			this.field = field;
		}

		public SortType getType() {
			return type;
		}

		public void setType(SortType type) {
			this.type = type;
		}

	}
}
