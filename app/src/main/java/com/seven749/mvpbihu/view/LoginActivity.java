package com.seven749.mvpbihu.view;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.seven749.mvpbihu.R;
import com.seven749.mvpbihu.base.BaseActivity;
import com.seven749.mvpbihu.presenter.LoginPresenter;
import com.seven749.mvpbihu.uitls.DoubleClickExitHelper;
import com.seven749.mvpbihu.uitls.httphelper.Request;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends BaseActivity<LoginPresenter> implements View.OnClickListener{
    private static final String TAG = "LoginActivity";
    private Button buttonPostLogin;
    private TextView toRegister;
    private EditText editUsername, editPassword;
    private String username,password;
    private final static String lastUrl = "login.php";
    private Map<String, Object> postDate = new HashMap<>();
    private DoubleClickExitHelper doubleClickExitHelper = new DoubleClickExitHelper(LoginActivity.this);


    @Override
    public LoginPresenter initPresenter() {
        return new LoginPresenter(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
        buttonPostLogin = (Button)findViewById(R.id.post_login);
        toRegister = (TextView)findViewById(R.id.to_register);
        buttonPostLogin.setOnClickListener(this);
        toRegister.setOnClickListener(this);
        editUsername = (EditText)findViewById(R.id.username_l);
        editPassword = (EditText)findViewById(R.id.password_l);
    }

    public void loginResponse(Message message) {
        Log.d(TAG, "loginResponse: ");
        parseJSON(message.obj.toString());
    }

    private void parseJSON(String responseData) {
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(responseData);
            String status = jsonObject.getString("status");
            String info =  jsonObject.getString("info");
            if (status.equals("200")){
                try {
                    String id = jsonObject.getJSONObject("data").getString("id");
                    String username = jsonObject.getJSONObject("data").getString("username");
                    final String token = jsonObject.getJSONObject("data").getString("token");
                    MainActivity.token = token;
                    Toast.makeText(LoginActivity.this, "登录成功！",
                            Toast.LENGTH_SHORT).show();
                }catch (Exception e) {
                    e.printStackTrace();
                }
                setResult(1);
                finish();
            } else {
                Toast.makeText(LoginActivity.this, info + "，登录失败...",
                        Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.post_login:
                username = editUsername.getText().toString();
                password = editPassword.getText().toString();
                if ("".equals(username) || "".equals(password)) {
                    Toast.makeText(LoginActivity.this, "用户名和密码不能为空",
                            Toast.LENGTH_SHORT).show();
                } else if (3 > username.length() || username.length() > 14) {
                    Toast.makeText(LoginActivity.this, "用户名3~14位",
                            Toast.LENGTH_SHORT).show();
                } else if (8 > password.length() || password.length() > 16) {
                    Toast.makeText(LoginActivity.this, "密码8~16位",
                            Toast.LENGTH_SHORT).show();
                } else {
                    // post login
                    postDate.put("username", username);
                    postDate.put("password", password);
                    Request request = new Request.Builder().url(MainActivity.baseUrl+lastUrl).method("POST").hashMap(postDate).build();
                    Log.d(TAG, "onClick: login");
                    mPresenter.login(request);
                }
                break;
            case R.id.to_register:
                Intent intent = new Intent(this, RegisterActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}
