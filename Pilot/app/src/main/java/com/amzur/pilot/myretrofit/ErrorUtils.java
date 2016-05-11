package com.amzur.pilot.myretrofit;





import com.amzur.pilot.MyApplication;
import com.squareup.okhttp.ResponseBody;

import java.io.IOException;
import java.lang.annotation.Annotation;

import retrofit.Converter;
import retrofit.Response;

public class ErrorUtils {

    public static APIError parseError(Response<ResponseBody> response) {
        Converter<ResponseBody, APIError> converter =
                MyApplication.getRetrofit()
                        .responseConverter(APIError.class, new Annotation[0]);

        APIError error;

        try {
            error = converter.convert(response.errorBody());
        } catch (IOException e) {
            return new APIError();
        }

        return error;
    }
}