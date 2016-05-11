package com.amzur.pilot.utilities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;

import com.amzur.pilot.MainActivity;
import com.amzur.pilot.MyApplication;
import com.amzur.pilot.R;
import com.amzur.pilot.interfaces.ConformationListener;

/**
 * Created by MRamesh on 11-05-2016.
 */
public class Utils {
    public static final String ERROR_SOMETHING="Something went wrong";
    public interface ErrorAlertCompleted {
        void OkaySelected();
    }
    public static void shakeView(Context c, View v)
    {
        Animation utils= AnimationUtils.loadAnimation(c, R.anim.shake);
        v.startAnimation(utils);
    }

    public static void showErrorAlert(String title, final String msg, final Activity act, final ErrorAlertCompleted alertCompleted) {
        AlertDialog.Builder builder = new AlertDialog.Builder(act);
        if(title!=null)
            builder.setTitle(title);
        else
            builder.create().requestWindowFeature(Window.FEATURE_NO_TITLE);
        builder.setMessage(msg);
        if (msg.equals(UtilsServer.MSG_AUTH_FAILED)) {
            parseJsonFeed(act);
        }
        builder.setNeutralButton("OK", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();
                if (alertCompleted != null)
                    alertCompleted.OkaySelected();

            }
        });
        builder.setCancelable(false);
        builder.setIcon(android.R.drawable.ic_dialog_alert);
        builder.show();
    }
    public static void showSuccessAlert(String title, final String msg, final Activity act, final ErrorAlertCompleted alertCompleted) {
        AlertDialog.Builder builder = new AlertDialog.Builder(act);
        builder.setTitle(title);
        builder.setMessage(msg);
        if (msg.equals(UtilsServer.MSG_AUTH_FAILED)) {
            parseJsonFeed(act);
        }
        builder.setNeutralButton("OK", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();
                if (alertCompleted != null)
                    alertCompleted.OkaySelected();

            }
        });
        builder.setCancelable(false);
        //builder.setIcon(android.R.drawable.ic_menu_save);
        builder.show();
    }
    public static void showSnackBar(Activity act,String msg){
        if (msg.contains(UtilsServer.MSG_AUTH_FAILED)) {
            parseJsonFeed(act);
        }
        final Snackbar bar=Snackbar.make(act.findViewById(android.R.id.content),msg,Snackbar.LENGTH_INDEFINITE);
        bar.setAction("OK", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bar.dismiss();
            }
        });
        bar.show();
    }
    public static void showSnackBarLongTime(Activity act,String msg){
        if(act==null)
            act= MyApplication.getInstance().getCurrentActivity();
        if (msg.contains(UtilsServer.MSG_AUTH_FAILED)) {
            parseJsonFeed(act);
        }
        Snackbar.make(act.findViewById(android.R.id.content),msg,Snackbar.LENGTH_LONG).show();

    }
    public static Snackbar showSnackBarLongTime2(Activity act,String msg){
        if (msg.equals(UtilsServer.MSG_AUTH_FAILED)) {
            parseJsonFeed(act);
        }
        return  Snackbar.make(act.findViewById(android.R.id.content),msg,Snackbar.LENGTH_LONG);

    }
    public static void showSnackBarShortTime(Activity act,String msg){
        if (msg.equals(UtilsServer.MSG_AUTH_FAILED)) {
            parseJsonFeed(act);
        }
        Snackbar.make(act.findViewById(android.R.id.content),msg,Snackbar.LENGTH_SHORT).show();
    }
    public static void showSnackBarOnTop(Activity act,String str)
    {
        Snackbar snack = Snackbar.make(act.findViewById(android.R.id.content), str, Snackbar.LENGTH_LONG);
        View view = snack.getView();
        FrameLayout.LayoutParams params =(FrameLayout.LayoutParams)view.getLayoutParams();
        params.gravity = Gravity.TOP;
        view.setLayoutParams(params);
        snack.show();
    }
    public static void showConformationDialog(Activity act, String title, String msg, final ConformationListener cl) {
        AlertDialog.Builder builder = new AlertDialog.Builder(act);
        if(title!=null)
            builder.setTitle(title);
        else
            builder.create().requestWindowFeature(Window.FEATURE_NO_TITLE);
        builder.setMessage(msg);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (cl != null)
                    cl.conformed();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();
            }
        });
        builder.setCancelable(false);
        builder.setIcon(android.R.drawable.ic_dialog_alert);
        builder.show();
    }
    public static void showSuccessDialog(Activity act, String title, String msg, final ConformationListener cl) {
        AlertDialog.Builder builder = new AlertDialog.Builder(act);
        if(title!=null)
            builder.setTitle(title);
        else
            builder.create().requestWindowFeature(Window.FEATURE_NO_TITLE);
        builder.setMessage(msg);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (cl != null)
                    cl.conformed();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();
            }
        });
        builder.setCancelable(false);

        builder.show();
    }
    public static void showOkayDialog(Activity act, String title, String msg, final ConformationListener cl) {
        AlertDialog.Builder builder = new AlertDialog.Builder(act);
        if(title!=null)
            builder.setTitle(title);
        else
            builder.create().requestWindowFeature(Window.FEATURE_NO_TITLE);
        builder.setMessage(msg);
        builder.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                if (cl != null)
                    cl.conformed();
            }
        });
        builder.setCancelable(true);
        builder.show();
    }
    public static void parseJsonFeed(Activity act) {
        Intent in = new Intent(act, MainActivity.class);
        in.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
     PreferenceData.putLoginStatus(act, false);
        act.startActivity(in);

    }
}
