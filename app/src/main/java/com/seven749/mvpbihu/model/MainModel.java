package com.seven749.mvpbihu.model;

import android.os.Handler;
import android.os.Message;

import com.seven749.mvpbihu.base.BaseModel;
import com.seven749.mvpbihu.contract.MainContract;
import com.seven749.mvpbihu.uitls.httphelper.CallBack;
import com.seven749.mvpbihu.uitls.httphelper.NetUtil;
import com.seven749.mvpbihu.uitls.httphelper.Request;

public class MainModel extends BaseModel implements MainContract.Model {

    public MainModel(Handler handler) {
        super(handler);
    }

    @Override
    public void getQuestionList(Request request) {
        NetUtil.getInstance().execute(request, new CallBack() {
            @Override
            public void onResponse(String response) {
                Message message = new Message();
                message.what = 2;
                message.obj = response;
                sendMessage(message);
            }

            @Override
            public void onFailed(Exception e) {
                e.printStackTrace();
            }
        });
    }
}
