package com.seven749.mvpbihu.uitls.httphelper;

public interface CallBack {

    void onResponse(String response);

    void onFailed(Exception e);
}
