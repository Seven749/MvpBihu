package com.seven749.mvpbihu.contract;

import android.os.Message;

import com.seven749.mvpbihu.base.IModel;
import com.seven749.mvpbihu.base.IPresenter;
import com.seven749.mvpbihu.base.IView;
import com.seven749.mvpbihu.uitls.httphelper.Request;

public interface MainContract {
    interface Model extends IModel {
        void getQuestionList(Request request);
    }

    interface View extends IView{
        void getListResponse(Message msg);
    }

    interface Presenter extends IPresenter{
        void getQuestionList(Request request);
    }
}
