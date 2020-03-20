package com.seven749.mvpbihu.view;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.seven749.mvpbihu.R;
import com.seven749.mvpbihu.base.BaseActivity;
import com.seven749.mvpbihu.presenter.MainPresenter;

public class MainActivity extends BaseActivity<MainPresenter>{

    public static final String baseUrl = "http://bihu.jay86.com/";
    private TextView loginText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
        loginText = (TextView)findViewById(R.id.text_login);
        loginText.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivityForResult(intent, 1);

        });
    }

    @Override
    public MainPresenter initPresenter() {
        return new MainPresenter(this);
    }
}
