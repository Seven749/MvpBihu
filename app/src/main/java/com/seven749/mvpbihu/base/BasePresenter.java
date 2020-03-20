package com.seven749.mvpbihu.base;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;

import java.lang.ref.WeakReference;

public abstract class BasePresenter<M extends BaseModel,V extends IView> implements IPresenter{
    public V mView;
    public M mModel;

    @SuppressLint("HandlerLink")
    public BasePresenter(V view) {
        this.mView = view;
        this.mModel = initModel(getHandler());
    }

    /**
     *我们通过实现IPresenter中的OnDestroy方法来解除持有
     *
     * @param owner 生命周期管理者
     */
    @Override
    public void OnDestroy(@NonNull LifecycleOwner owner) {
        //解绑V层 避免导致内存泄漏
        mView = null;
        mModel.onDestroy();
        mModel = null;
    }

    public abstract M initModel(Handler handler);

    /**
     * 获取handler的方法
     *
     * @return BaseHandler
     */
    public Handler getHandler(){
        return new BaseHandler(this);
    }

    /**
     * 基础Handler 用于P层与M层通信
     */
    public static class BaseHandler extends Handler {

        //弱引用Activity或者Fragment 避免Handler持有导致内存泄漏
        private final WeakReference<BasePresenter> presenter;

        public BaseHandler(BasePresenter presenter) {
            this.presenter = new WeakReference<>(presenter);
        }

        @Override
        public void handleMessage(Message msg) {
            if(presenter.get()!=null&&presenter.get().mView!=null){
                presenter.get().modelResponse(msg);
            }
        }
    }

    //接收M层返回的消息
    public abstract void modelResponse(Message msg);
}
