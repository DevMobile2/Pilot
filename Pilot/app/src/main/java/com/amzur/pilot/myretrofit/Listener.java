package com.amzur.pilot.myretrofit;

import android.app.Activity;


import com.amzur.pilot.dialog.CustomDialog;
import com.amzur.pilot.utilities.Utils;
import com.squareup.okhttp.ResponseBody;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;

import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by RameshK on 25-11-2015.
 */
public class Listener implements Callback<ResponseBody> {

    Type tt;
    RetrofitService listner;
    CustomDialog dialog;
    Activity activity;

    public Listener(RetrofitService listner, String title, boolean showProgress, Activity activity) {
        //tt=t;
        this.listner = listner;
        dialog = new CustomDialog(activity);
        dialog.setCancelable(false);
        if (title != null && title.length() > 0)
            dialog.setTitle(title);
        if (showProgress && dialog!=null && !dialog.isShowing()) {
            dialog.show();
        }
        this.activity = activity;
    }

    @Override
    public void onResponse(Response<ResponseBody> response, Retrofit retrofit) {

        try {
            if(dialog!=null && dialog.isShowing())
            dialog.dismiss();
            if (!response.isSuccess()) {
                Utils.showSnackBarLongTime(activity, Utils.ERROR_SOMETHING);
                listner.onSuccess(response.message(), 2, null);
            } else {
                String res = response.body().string();
                JSONObject obj = new JSONObject(res);
                if (obj.isNull("success") || !obj.getBoolean("success")) {
                    showError(obj );
                } else
                    listner.onSuccess(res, 0, null);
            }
        } catch (IOException | JSONException  | IllegalArgumentException e) {
            e.printStackTrace();
            Utils.showSnackBarLongTime(activity, Utils.ERROR_SOMETHING);
            listner.onSuccess("", 2, null);
        }
    }

    public void showError(JSONObject obj) {
        if (!obj.isNull("message")) {
            try {
                Utils.showSnackBarLongTime(activity, obj.getJSONArray("message").getString(0));
            } catch (JSONException e) {
                e.printStackTrace();
                Utils.showSnackBar(activity, Utils.ERROR_SOMETHING);
            } finally {
                listner.onSuccess("", 2, null);
            }
        } else {
            Utils.showSnackBar(activity, Utils.ERROR_SOMETHING);
            listner.onSuccess("", 2, null);
        }

    }

    @Override
    public void onFailure(Throwable t) {
        if(dialog!=null && dialog.isShowing())
        dialog.dismiss();
        listner.onSuccess("", 1, t);

    }
}
