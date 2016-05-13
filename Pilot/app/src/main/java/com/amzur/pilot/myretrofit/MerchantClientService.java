package com.amzur.pilot.myretrofit;


import com.squareup.okhttp.ResponseBody;

import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.POST;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by RameshK on 24-11-2015.
 *
 */
public interface MerchantClientService {

    @POST("merchant-rest-service/auth/login")
    Call<ResponseBody> authLogin(@Body String body, @Header("Content-type")String content);

    @POST("merchant-rest-service/auth/logout")
    Call<ResponseBody> authLogout();

    @GET("merchant-rest-service/categories")
    Call<ResponseBody> getCategories();

    @GET("merchant-rest-service/categories/{id}")
    Call<ResponseBody> getItems(@Path("id") int id);

    @GET("merchant-rest-service/items/{id}")
    Call<ResponseBody> getItemDetails(@Path("id") int id);

    @POST("merchant-rest-service/buy")
    Call<ResponseBody> buyItem(@Body String body, @Header("Content-type")String content);


}
