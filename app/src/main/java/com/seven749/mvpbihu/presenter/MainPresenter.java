package com.seven749.mvpbihu.presenter;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.seven749.mvpbihu.base.BasePresenter;
import com.seven749.mvpbihu.model.MainModel;
import com.seven749.mvpbihu.uitls.httphelper.Request;
import com.seven749.mvpbihu.view.MainActivity;

public class MainPresenter extends BasePresenter<MainModel, MainActivity> {

    public MainPresenter(MainActivity view) {
        super(view);
    }

    @Override
    public MainModel initModel(Handler handler) {
        return new MainModel(handler);
    }

    @Override
    public void modelResponse(Message msg) {
        switch (msg.what) {
            case 2:
                Log.d("MainPresenter", "getQuestionResponse: ");
                mView.getListResponse(msg);
                break;
        }
    }

    public void getQuestionList(Request request) {
        Log.d("MainPresenter", "getQuestionList: ");
        mModel.getQuestionList(request);
    }
}
