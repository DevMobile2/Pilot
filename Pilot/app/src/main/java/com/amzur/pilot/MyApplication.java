package com.amzur.pilot;

import android.app.Activity;
import android.app.Application;
import android.provider.Settings;
import android.util.Log;

import com.amzur.pilot.myretrofit.GsonStringConverterFactory;
import com.amzur.pilot.myretrofit.MerchantClientService;
import com.amzur.pilot.utilities.PreferenceData;
import com.amzur.pilot.utilities.UtilsServer;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.logging.HttpLoggingInterceptor;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;


/**
 * Created by MRamesh on 11-05-2016.
 *
 */
public class MyApplication extends Application {
    public static Retrofit mRetrofit;
    public static MerchantClientService service;
    private static MyApplication instance;
    private static OkHttpClient httpClient = new OkHttpClient();
    private Activity mCurrentActivity = null;

    public static synchronized MerchantClientService getSerivce() {
        if (service == null) {
            service = getRetrofit().create(MerchantClientService.class);
        }
        return service;
    }

   /* public static synchronized MerchantClientService getSerivceWithApiKey() {
        if (service == null) {
            service = getRetrofitWithApikey().create(MerchantClientService.class);
        }
        return service;
    }*/

    public static synchronized Retrofit getRetrofit() {
        if (mRetrofit == null) {
            httpClient.interceptors().add(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request original = chain.request();

                    Request request = original.newBuilder()
                            //  .header("device-type", "ANDROID")
                            // .header("device-id", Settings.Secure.getString(MyApplication.getInstance().getContentResolver(), Settings.Secure.ANDROID_ID))
                            //    .header("api_key", PreferenceData.getString(PreferenceData.PREF_API_KEY))
                            .method(original.method(), original.body())

                            .build();

                    return chain.proceed(request);
                }
            });
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            httpClient.interceptors().add(interceptor);
            httpClient.setReadTimeout(1, TimeUnit.MINUTES);
            httpClient.setWriteTimeout(1, TimeUnit.MINUTES);
            mRetrofit = new Retrofit.Builder()
                    .baseUrl(UtilsServer.URL_API)
                    .addConverterFactory(new GsonStringConverterFactory())
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient)
                    .build();


        }
        return mRetrofit;
    }

/*    public static synchronized Retrofit getRetrofitWithApikey() {
        if (mRetrofit == null) {
            httpClient.interceptors().add(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request original = chain.request();

                    Request request = original.newBuilder()

                                .header("api_key", PreferenceData.getString(PreferenceData.PREF_API_KEY))
                            .method(original.method(), original.body())

                            .build();

                    return chain.proceed(request);
                }
            });
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            httpClient.interceptors().add(interceptor);
            httpClient.setReadTimeout(1, TimeUnit.MINUTES);
            httpClient.setWriteTimeout(1, TimeUnit.MINUTES);
            mRetrofit = new Retrofit.Builder()
                    .baseUrl(UtilsServer.URL_API)
                    .addConverterFactory(new GsonStringConverterFactory())
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient)
                    .build();


        }
        return mRetrofit;
    }*/

    public static synchronized MyApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public Activity getCurrentActivity() {
        return mCurrentActivity;
    }

    public void setCurrentActivity(Activity mCurrentActivity) {
        this.mCurrentActivity = mCurrentActivity;
    }
}
