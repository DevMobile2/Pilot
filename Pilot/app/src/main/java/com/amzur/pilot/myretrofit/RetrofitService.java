package com.amzur.pilot.myretrofit;

import retrofit.Response;

/**
 * Created by RameshK on 25-11-2015.
 *
 */
public interface RetrofitService {
    void onSuccess(String result, int pos, Throwable t);

}
