package com.amzur.pilot.mockserver;

import com.amzur.pilot.myretrofit.MerchantClientService;
import com.squareup.okhttp.ResponseBody;

import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.Header;
import retrofit.http.Path;

/**
 * Created by MRamesh on 17-05-2016.
 */
public class MockMerchantClientService implements MerchantClientService {
    @Override
    public Call<ResponseBody> authLogin(@Body String body, @Header("Content-type") String content) {
        return null;
    }

    @Override
    public Call<ResponseBody> authLogout(@Header("api_key") String contentRange, @Body String body, @Header("Content-type") String content) {
        return null;
    }

    @Override
    public Call<ResponseBody> getCategories(@Header("api_key") String contentRange) {
        return null;
    }

    @Override
    public Call<ResponseBody> getItems(@Header("api_key") String contentRange, @Path("id") int id) {
        return null;
    }

    @Override
    public Call<ResponseBody> getItemDetails(@Header("api_key") String contentRange, @Path("id") int id) {
        return null;
    }

    @Override
    public Call<ResponseBody> buyItem(@Header("api_key") String contentRange, @Body String body, @Header("Content-type") String content) {
        return null;
    }
}
