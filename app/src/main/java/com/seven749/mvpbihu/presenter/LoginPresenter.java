package com.seven749.mvpbihu.presenter;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.seven749.mvpbihu.base.BasePresenter;
import com.seven749.mvpbihu.contract.LoginContract;
import com.seven749.mvpbihu.model.LoginModel;
import com.seven749.mvpbihu.uitls.httphelper.Request;
import com.seven749.mvpbihu.view.LoginActivity;

public class LoginPresenter extends BasePresenter<LoginContract.Model, LoginContract.View> implements LoginContract.Presenter{

    public LoginPresenter(LoginActivity view) {
        super(view);
    }

    @Override
    public LoginModel initModel(Handler handler) {
        return new LoginModel(handler);
    }

    @Override
    public void modelResponse(Message msg) {
        switch (msg.what) {
            case 0:
                Log.d("LoginPresenter", "LoginResponse: ");
                mView.loginResponse(msg);
                break;
        }
    }

    @Override
    public void login(Request request) {
        Log.d("LoginPresenter", "login: ");
        mModel.login(request);
    }

}
