package com.example.unive.httputil.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.app.Activity;

import com.example.unive.httputil.R;

public class BaseActivity extends Activity {
    private static ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public static void showProgressDialog(Context context){
        if (progressDialog == null){
            progressDialog=new ProgressDialog(context);
            progressDialog.setMessage("LOADING...");
            progressDialog.setCancelable(false);
        }
        progressDialog.show();
    }

    public static void closeProgressDialog(){
        if (progressDialog != null){
            progressDialog.dismiss();
        }
    }

}
