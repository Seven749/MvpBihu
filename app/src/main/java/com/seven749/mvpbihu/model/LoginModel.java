package com.seven749.mvpbihu.model;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.seven749.mvpbihu.base.BaseModel;
import com.seven749.mvpbihu.contract.LoginContract;
import com.seven749.mvpbihu.uitls.httphelper.CallBack;
import com.seven749.mvpbihu.uitls.httphelper.NetUtil;
import com.seven749.mvpbihu.uitls.httphelper.Request;

public class LoginModel extends BaseModel implements LoginContract.Model {
    private final static String TAG = "LoginModel";
    public LoginModel(Handler handler) {
        super(handler);
    }

    @Override
    public void login(Request request) {
        Log.d(TAG, "login: start");
        NetUtil.getInstance().execute(request, new CallBack() {
            @Override
            public void onResponse(String response) {
                Message message = new Message();
                message.what = 0;
                message.obj = response;
                sendMessage(message);
                Log.d(TAG, "LoginSendMessage");
            }

            @Override
            public void onFailed(Exception e) {
                e.printStackTrace();
            }
        });
        Log.d(TAG, "login: end");
    }
}
