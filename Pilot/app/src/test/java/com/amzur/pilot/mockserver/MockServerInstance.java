package com.amzur.pilot.mockserver;

import com.amzur.eteki.mock.BehaviorDelegate;
import com.amzur.eteki.mock.MockRetrofit;
import com.amzur.eteki.mock.NetworkBehavior;
import com.amzur.eteki.myretrofit.eTekiService;
import com.amzur.pilot.myretrofit.MerchantClientService;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.mockwebserver.MockWebServer;
import retrofit2.Retrofit;

/**
 * Created by MRamesh on 12-04-2016.
 *
 */
public class MockServerInstance {
  public static MerchantClientService getMockServiceObject() throws IOException {
      MockWebServer mockWebServer;
      MockRetrofit mockRetrofit;
      mockWebServer=new MockWebServer();
      mockWebServer.start();
      Retrofit retrofit=new Retrofit.Builder().baseUrl("http://test.com").client(new OkHttpClient()).build();
      NetworkBehavior networkBehavior=NetworkBehavior.create();
      mockRetrofit=new MockRetrofit.Builder(retrofit).networkBehavior(networkBehavior).build();
      BehaviorDelegate<MerchantClientService> delegate = mockRetrofit.create(MerchantClientService.class);
      return new MockeTekiService(delegate);
  }

}
