package com.amzur.pilot.myretrofit;


import com.squareup.okhttp.ResponseBody;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;

/**
 * Created by RameshK on 24-11-2015.
 *
 */
public interface MerchantClientService {

    @GET("merchant-rest-service/categories/{id}")
    Call<ResponseBody> getItems(@Path("id") int id);

    @GET("merchant-rest-service/items/{id}")
    Call<ResponseBody> getItemDetails(@Path("id") int id);

    @POST("merchant-rest-service/buy/{id}")
    Call<ResponseBody> buyItem(@Path("id") int id);


}
