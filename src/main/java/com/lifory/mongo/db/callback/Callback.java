package com.lifory.mongo.db.callback;

public interface Callback<T> {
	/**
	 * 成功回调
	 */
	public void onResponse(Object response);
	/**
	 * 失败回调
	 */
	public void onFailure(Throwable e);
	/**
	 * 回调是否完成
	 * @return
	 */
	public boolean isComplate();
	/**
	 * 是否执行成功
	 * @return
	 */
	public boolean isSuccessful();
	/**
	 * 同步等待
	 */
	public void sync();
}
