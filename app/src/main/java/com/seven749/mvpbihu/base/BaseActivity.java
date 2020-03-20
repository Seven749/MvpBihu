package com.seven749.mvpbihu.base;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.seven749.mvpbihu.uitls.MyUtil;

public abstract class BaseActivity<P extends BasePresenter> extends AppCompatActivity implements IView{

    public P mPresenter;

    public abstract P initPresenter();

    public static final String TAG = "BaseActivity";

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

    @Override
    public void showMessage(String message) {

    }

    @Override
    public void showProgress() {

    }

    @Override
    public void dismissProgress() {

    }

    @Override
    public boolean hasNetwork(Context context) {
        return false;
    }
}
