package com.amzur.pilot.myretrofit;


import com.squareup.okhttp.ResponseBody;

import retrofit.Call;
import retrofit.http.GET;

/**
 * Created by RameshK on 24-11-2015.
 *
 */
public interface MerchantClientService {
    @GET("/categories/1")
    Call<ResponseBody> getItems();

}
