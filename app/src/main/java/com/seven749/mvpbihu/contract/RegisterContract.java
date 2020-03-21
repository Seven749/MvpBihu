package com.seven749.mvpbihu.contract;

import android.os.Message;

import com.seven749.mvpbihu.base.IModel;
import com.seven749.mvpbihu.base.IPresenter;
import com.seven749.mvpbihu.base.IView;
import com.seven749.mvpbihu.uitls.httphelper.Request;

public interface RegisterContract {
    interface Model extends IModel{
        void register(Request request);
    }

    interface View extends IView{
        void registerResponse(Message msg);
    }

    interface Presenter extends IPresenter{
        void register(Request request);
    }
}
