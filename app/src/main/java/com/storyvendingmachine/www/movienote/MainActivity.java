package com.storyvendingmachine.www.movienote;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private boolean saveLoginData;
    private String id;
    private String pwd;

    private EditText userEmailTextbox;
    private EditText userPasswordTextbox;
    private CheckBox checkBox;
    private Button loginButton;

    private SharedPreferences appData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        appData = getSharedPreferences("appData", MODE_PRIVATE);
        load();

//        final EditText userEmailTextbox = (EditText) findViewById(R.id.userEmailTextbox);
//        final EditText userPasswordTextbox = (EditText) findViewById(R.id.userPasswordTextbox);
          userEmailTextbox = (EditText) findViewById(R.id.userEmailTextbox);
          userPasswordTextbox = (EditText) findViewById(R.id.userPasswordTextbox);
        //CheckBox checkBox = (CheckBox) findViewById(R.id.checkbox);
          checkBox = (CheckBox) findViewById(R.id.checkbox);

        // 이전에 로그인 정보를 저장시킨 기록이 있다면
        if (saveLoginData) {
            userEmailTextbox.setText(id);
            userPasswordTextbox.setText(pwd);
            checkBox.setChecked(saveLoginData);
        }

//        Button loginButton = (Button) findViewById(R.id.loginButton);
        loginButton = (Button) findViewById(R.id.loginButton);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String user_email = userEmailTextbox.getText().toString();
                String user_password = userPasswordTextbox.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");
                            if(success){
                                //로그인 성공
                                String user_email = jsonObject.getString("user_email");
                                String user_password = jsonObject.getString("user_password");
                                String user_name = jsonObject.getString("user_name");
                                String user_nickname = jsonObject.getString("user_nickname");

                                Intent intent = new Intent(MainActivity.this, MemberMainActivity.class);
                                intent.putExtra("user_email", user_email);
                                intent.putExtra("user_password", user_password);
                                intent.putExtra("user_name", user_name);
                                intent.putExtra("user_nickname", user_nickname);
                                save();
                                MainActivity.this.startActivity(intent);
                                MainActivity.this.finish();



                            }else{
                                //로그인 실패
                                AlertDialog.Builder notifier = new AlertDialog.Builder(MainActivity.this);
                                notifier.setTitle("로그인 실패")
                                        .setMessage("이메일과 비밀번호를 확인해주세요")
                                        .setNegativeButton("다시시도", null)
                                        .create()
                                        .show();
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                            AlertDialog.Builder notifier = new AlertDialog.Builder(MainActivity.this);
                            notifier.setTitle("접속 안됨")
                                    .setMessage("접속 안됨")
                                    .setNegativeButton("다시시도", null)
                                    .create()
                                    .show();
                        }
                    }
                };

             LoginRequest loginRequest = new LoginRequest(user_email, user_password, responseListener);
                RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
                queue.add(loginRequest);

            }
        });

        TextView registerButton = (TextView) findViewById(R.id.registerButton);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent registerintent = new Intent(MainActivity.this, RegisterActivity.class);
                MainActivity.this.startActivity(registerintent);
            }
        });
    }

    // 설정값을 저장하는 함수
    private void save() {
        // SharedPreferences 객체만으론 저장 불가능 Editor 사용
        SharedPreferences.Editor editor = appData.edit();

        // 에디터객체.put타입( 저장시킬 이름, 저장시킬 값 )
        // 저장시킬 이름이 이미 존재하면 덮어씌움
        editor.putBoolean("SAVE_LOGIN_DATA", checkBox.isChecked());
        editor.putString("ID", userEmailTextbox.getText().toString().trim());
        editor.putString("PWD", userPasswordTextbox.getText().toString().trim());

        // apply, commit 을 안하면 변경된 내용이 저장되지 않음
        editor.apply();
    }

    // 설정값을 불러오는 함수
    private void load() {
        // SharedPreferences 객체.get타입( 저장된 이름, 기본값 )
        // 저장된 이름이 존재하지 않을 시 기본값
        saveLoginData = appData.getBoolean("SAVE_LOGIN_DATA", false);
        id = appData.getString("ID", "");
        pwd = appData.getString("PWD", "");
    }






}
