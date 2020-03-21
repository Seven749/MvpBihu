package com.seven749.mvpbihu.model;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.seven749.mvpbihu.base.BaseModel;
import com.seven749.mvpbihu.contract.RegisterContract;
import com.seven749.mvpbihu.uitls.httphelper.CallBack;
import com.seven749.mvpbihu.uitls.httphelper.NetUtil;
import com.seven749.mvpbihu.uitls.httphelper.Request;

public class RegisterModel extends BaseModel implements RegisterContract.Model {

    private static final String TAG = "RegisterModel";

    public RegisterModel(Handler handler) {
        super(handler);
    }

    @Override
    public void register(Request request) {
        Log.d(TAG, "Register: start");
        NetUtil.getInstance().execute(request, new CallBack() {
            @Override
            public void onResponse(String response) {
                Message message = new Message();
                message.what = 1;
                message.obj = response;
                sendMessage(message);
                Log.d(TAG, "RegisterSendMessage");
            }

            @Override
            public void onFailed(Exception e) {
                e.printStackTrace();
            }
        });
        Log.d(TAG, "Register: end");
    }
}
