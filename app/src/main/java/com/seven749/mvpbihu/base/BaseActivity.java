package com.seven749.mvpbihu.base;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.seven749.mvpbihu.uitls.MyUtil;

public abstract class BaseActivity<P extends IPresenter> extends AppCompatActivity implements IView{

    public P mPresenter;

    public abstract P initPresenter();

    protected final String TAG = this.getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: ");
        super.onCreate(savedInstanceState);
        MyUtil.addActivity(this);
        //初始化mPresenter
        mPresenter = initPresenter();
        //绑定生命周期
        getLifecycle().addObserver(mPresenter);
    }

    @Override
    protected void onStop() {
        Log.d(TAG, "onStop: ");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.d(TAG, "onDestroy: ");
        super.onDestroy();
        MyUtil.removeActivity(this);
        //解绑P层 避免内存泄漏
        getLifecycle().removeObserver(mPresenter);
        mPresenter = null;
        //通知系统进行一次回收
        System.gc();
    }

    @Override
    protected void onResume() {
        Log.d(TAG, "onResume: ");
        super.onResume();
    }

    @Override
    protected void onStart() {
        Log.d(TAG, "onStart: ");
        super.onStart();
    }

    @Override
    protected void onRestart() {
        Log.d(TAG, "onRestart: ");
        super.onRestart();
    }

    @Override
    protected void onPause() {
        Log.d(TAG, "onPause: ");
        super.onPause();
    }


    /**
     * 展示提示信息
     *
     * @param message 要提示的信息
     */
    void showMessage(String message) {}

    /**
     * 显示加载进度.
     */
    void showProgress(){}

    /**
     * 隐藏加载进度.
     */
    void dismissProgress(){}

    /**
     * 判断是否有网络
     *
     * @return 是否有网络
     */
    boolean hasNetwork(Context context){return false;}
}
