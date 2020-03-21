package com.seven749.mvpbihu.view;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.seven749.mvpbihu.R;
import com.seven749.mvpbihu.base.BaseActivity;
import com.seven749.mvpbihu.contract.MainContract;
import com.seven749.mvpbihu.model.Question;
import com.seven749.mvpbihu.presenter.MainPresenter;
import com.seven749.mvpbihu.uitls.httphelper.Request;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends BaseActivity<MainContract.Presenter> implements MainContract.View{

    public static final String baseUrl = "http://bihu.jay86.com/";
    private static final String lastUrl = "getQuestionList.php";
    private List<Question> questionList = new ArrayList<>();
    private Map<String, Object> postData;
    public static String token;
    private TextView loginText;
    private RecyclerView recyclerView;
    public static QuestionAdapter adapter;
    public static boolean isLogin = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
        loginText = (TextView) findViewById(R.id.text_login);
        loginText.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivityForResult(intent, 1);
        });
        if (!isLogin) {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivityForResult(intent, 1);
        } else {
            initQuestion();
        }
        recyclerView = (RecyclerView) findViewById(R.id.question_recycler_r);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new QuestionAdapter(this, questionList);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public MainPresenter initPresenter() {
        return new MainPresenter(this);
    }

    public void initQuestion() {
        Log.d(TAG, "initQuestion: ");
/* 随机数
        final long l = System.currentTimeMillis();
        final int i = (int) (l % 11);
 */
        postData = new HashMap<String, Object>() {{
            put("page", 0);
            put("count", 20);
            put("token", token);
        }};
        questionList.clear();
        Request request = new Request.Builder()
                .url(MainActivity.baseUrl + lastUrl)
                .method("POST")
                .hashMap(postData)
                .build();
        mPresenter.getQuestionList(request);
    }

    @Override
    public void getListResponse(Message msg) {
        Log.d(TAG, "getListResponse: getQuestion");
        parseJSON(msg.obj.toString());
    }

    private  void parseJSON(String jsonData) {
        try {
            Log.d(TAG, "parseJSON: getQuestion");
            final JSONObject jsonObject = new JSONObject(jsonData);
            final String status = jsonObject.getString("status");
            final String info = jsonObject.getString("info");
            if (status.equals("200")) {
                try {
                    JSONArray jsonArray = new JSONArray(jsonObject.getJSONObject("data").get("questions").toString());
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonQuestion = jsonArray.getJSONObject(i);
                        final String idQ = jsonQuestion.getString("id");
                        final String titleQ = jsonQuestion.getString("title");
                        final String contentQ = jsonQuestion.getString("content");
//                               Log.d(TAG, "run: " + contentQ);
                        final String imagesQ = jsonQuestion.getString("images");
                        final String dateQ = jsonQuestion.getString("date");
                        final String authorNameQ = jsonQuestion.getString("authorName");
                        final String avatarQ = jsonQuestion.getString("authorAvatar");
                        final boolean isF = jsonQuestion.getBoolean("is_favorite");
                        Question question = new Question(idQ, titleQ, contentQ, imagesQ, dateQ, authorNameQ, avatarQ,isF);
                        questionList.add(question);
                        adapter.notifyDataSetChanged();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                Toast.makeText(this, info + "，刷新失败...",
                        Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1:
                if (resultCode == 1) {
                    initQuestion();
                }
                break;
            default:
        }
    }

}