package com.seven749.mvpbihu.view;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.seven749.mvpbihu.R;
import com.seven749.mvpbihu.base.BaseActivity;
import com.seven749.mvpbihu.contract.RegisterContract;
import com.seven749.mvpbihu.presenter.RegisterPresenter;
import com.seven749.mvpbihu.uitls.httphelper.Request;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends BaseActivity<RegisterContract.Presenter> implements RegisterContract.View{

    private Button buttonPostRegister;
    private EditText editUsername, editPassword, editPasswordAgain;
    private String username, password, passwordAgain;
    private final String lastUrl = "register.php";
    private static final String TAG = "RegisterActivity";
    private Map<String, Object> data = new HashMap<String, Object>();

    @Override
    public RegisterPresenter initPresenter() {
        return new RegisterPresenter(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
        editUsername = (EditText)findViewById(R.id.username_r);
        editPassword = (EditText)findViewById(R.id.password_r);
        editPasswordAgain = (EditText)findViewById(R.id.password_again_r);
        buttonPostRegister = (Button)findViewById(R.id.post_register);
        buttonPostRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = editUsername.getText().toString();
                password = editPassword.getText().toString();
                passwordAgain = editPasswordAgain.getText().toString();
                if ("".equals(username) || "".equals(password) || "".equals(passwordAgain)) {
                    Toast.makeText(RegisterActivity.this, "用户名和密码不能为空",
                            Toast.LENGTH_SHORT).show();
                } else if (3 > username.length() || username.length() > 14) {
                    Toast.makeText(RegisterActivity.this, "用户名3~14位",
                            Toast.LENGTH_SHORT).show();
                } else if (8 > password.length() || password.length() > 16) {
                    Toast.makeText(RegisterActivity.this, "密码8~16位",
                            Toast.LENGTH_SHORT).show();
                } else if (!passwordAgain.equals(password)){
                    Toast.makeText(RegisterActivity.this, "两次密码不一致！",
                            Toast.LENGTH_SHORT).show();
                } else {
                    // post register
                    data.put("username", username);
                    data.put("password", password);
                    Request request = new Request.Builder()
                            .url(MainActivity.baseUrl+lastUrl)
                            .method("POST")
                            .hashMap(data)
                            .build();
                    mPresenter.register(request);
                }
            }
        });
    }

    @Override
    public void registerResponse(Message message) {
        Log.d(TAG, "RegisterResponse: ");
        parseJSON(message.obj.toString());
    }

    private void parseJSON(String responseData) {
        try {
            JSONObject jsonObject = new JSONObject(responseData);
            String status = jsonObject.getString("status");
            String info = jsonObject.getString("info");
            if (status.equals("200")) {
                Toast.makeText(RegisterActivity.this, "注册成功！",
                        Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(RegisterActivity.this, info + "，注册失败...",
                        Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
