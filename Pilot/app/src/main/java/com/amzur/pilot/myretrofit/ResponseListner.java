package com.amzur.pilot.myretrofit;

public interface ResponseListner {

	void onSuccess(String result, int pos);
	void onError(String error, int pos);
}
