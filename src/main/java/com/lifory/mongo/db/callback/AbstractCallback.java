package com.lifory.mongo.db.callback;

import com.lifory.mongo.db.common.Flag;

@SuppressWarnings("unchecked")
public abstract class AbstractCallback<T> implements Callback<T> {
	
	private Flag compateFlag = new Flag();
	private Flag successFlag = new Flag();
	
	@Override
	public boolean isComplate() {
		return compateFlag.getStat();
	}
	
	@Override
	public boolean isSuccessful() {
		return successFlag.getStat();
	}
	
	/**
	 * 同步等待完成
	 */
	@Override
	public synchronized void sync() {
		try {
			wait();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public abstract void successful(T response);
	
	public abstract void failure(Throwable e);

	@Override
	public synchronized void onResponse(Object response) {
		try {
			successful((T)response);
			compateFlag.setStat(true);
			successFlag.setStat(true);
		} catch (Exception k) {
			k.printStackTrace();
		} 
		notify();
	}

	@Override
	public synchronized void onFailure(Throwable e) {
		try {
			failure(e);
			compateFlag.setStat(true);
			successFlag.setStat(false);
		} catch (Exception k) {
			k.printStackTrace();
		}
		notify();
	}
}
