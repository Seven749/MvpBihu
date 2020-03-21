package com.seven749.mvpbihu.presenter;

import android.os.Handler;
import android.os.Message;

import com.seven749.mvpbihu.base.BasePresenter;
import com.seven749.mvpbihu.contract.RegisterContract;
import com.seven749.mvpbihu.model.RegisterModel;
import com.seven749.mvpbihu.uitls.httphelper.Request;
import com.seven749.mvpbihu.view.RegisterActivity;

public class RegisterPresenter extends BasePresenter<RegisterContract.Model, RegisterContract.View> implements RegisterContract.Presenter {

    public RegisterPresenter(RegisterActivity view) {
        super(view);
    }

    @Override
    public RegisterModel initModel(Handler handler) {
        return new RegisterModel(handler);
    }

    @Override
    public void modelResponse(Message msg) {
        switch (msg.what) {
            case 1:
                mView.registerResponse(msg);
        }
    }

    @Override
    public void register(Request request) {
        mModel.register(request);
    }
}
