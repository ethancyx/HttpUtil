package com.example.unive.httputil.util;

public interface MyCallBack {
    //链接成功执行的方法
    void onFailure(int code);
    //链接失败执行的方法
    void onResponse(String json);

}
