package com.seven749.mvpbihu.base;

import android.content.Context;

public interface IView {

    /**
     * 展示提示信息
     *
     * @param message 要提示的信息
     */
    void showMessage(String message);

    /**
     * 显示加载进度.
     */
    void showProgress();

    /**
     * 隐藏加载进度.
     */
    void dismissProgress();

    /**
     * 判断是否有网络
     *
     * @return 是否有网络
     */
    boolean hasNetwork(Context context);
}
