package com.uet.uetworks;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

public class CommonMethod {

    public static final String DISPLAY_MESSAGE_ACTION =
            "com.codecube.broking.gcm";

    public static final String EXTRA_MESSAGE = "message";

    public static boolean isNetworkAvailable(Context ctx) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public static void showToast( Activity context) {
        Toast.makeText(context,"Hãy kiểm tra lại kết nối internet",Toast.LENGTH_LONG).show();

    }
}