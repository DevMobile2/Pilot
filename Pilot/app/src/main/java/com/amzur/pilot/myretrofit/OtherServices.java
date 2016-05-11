package com.amzur.pilot.myretrofit;

import com.squareup.okhttp.ResponseBody;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.POST;
import retrofit.http.Url;

/**
 * Created by RameshK on 26-02-2016.
 *
 */
public interface OtherServices {

    @POST
    Call<ResponseBody> getDetailsAfterTask(@Url String url);

    @GET
    Call<ResponseBody> getProfileDetails(@Url String url, @Header("x-li-format") String json);


}
