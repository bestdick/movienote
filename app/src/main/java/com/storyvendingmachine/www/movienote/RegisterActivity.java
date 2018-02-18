package com.storyvendingmachine.www.movienote;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.KeyListener;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;



import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class RegisterActivity extends AppCompatActivity {
    Boolean user_email_boolean = false;
    Boolean user_password_check_boolean = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final EditText user_email = (EditText) findViewById(R.id.r_userEmailTextbox);
        final TextView email_check = (TextView) findViewById(R.id.r_emailcheck);
        final EditText user_password = (EditText) findViewById(R.id.r_userPasswordTextbox);
        final EditText user_password_confirm = (EditText) findViewById(R.id.r_userPasswordConfirm);
        final TextView password_check = (TextView) findViewById(R.id.r_passwordCheck);
        EditText user_name = (EditText) findViewById(R.id.r_userName);
        EditText user_nickname = (EditText) findViewById(R.id.r_userNickname);


        Button register_button = (Button) findViewById(R.id.r_register);

        user_email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if(charSequence.length() > 8 || charSequence.toString().contains("@")) {
                    //String target = "http://joonandhoon.dothome.co.kr/mn/php/available_email_checker.php?user_email=" + charSequence.toString();
                    final String written_user_email = charSequence.toString();
                    BackgroundTask bt =new BackgroundTask();
                    bt.String("http://joonandhoon.dothome.co.kr/mn/php/available_email_checker.php?user_email=" + written_user_email);
                    bt.execute();
                }else if(charSequence.length() == 0){
                    email_check.setText("이메일을 입력해주세요");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        user_email.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                user_email.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    }
                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                        if(charSequence.length() > 0 || charSequence.toString().contains("@")) {
                            //String target = "http://joonandhoon.dothome.co.kr/mn/php/available_email_checker.php?user_email=" + charSequence.toString();
                            final String written_user_email = charSequence.toString();
                            BackgroundTask bt =new BackgroundTask();
                            bt.String("http://joonandhoon.dothome.co.kr/mn/php/available_email_checker.php?user_email=" + written_user_email);
                            bt.execute();
                        }else if(charSequence.length() == 0){
                            email_check.setText("이메일을 입력해주세요");
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable editable) {
                    }
                });
            }
        });



        user_password_confirm.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(user_password.getText().toString().equals(charSequence.toString())){
                    password_check.setText("비밀번호가 일치합니다");
                    password_check.setTextColor(getResources().getColor(R.color.colorGreen));
                    user_password_check_boolean = true;
                }else{
                    password_check.setText("비밀번호가 일치하지 않습니다");
                    password_check.setTextColor(getResources().getColor(R.color.colorRed));
                    user_password_check_boolean = false;
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(user_password.getText().toString().equals(editable.toString())){
                    password_check.setText("비밀번호가 일치합니다");
                    password_check.setTextColor(getResources().getColor(R.color.colorGreen));
                    user_password_check_boolean = true;
                }else{
                    password_check.setText("비밀번호가 일치하지 않습니다");
                    password_check.setTextColor(getResources().getColor(R.color.colorRed));
                    user_password_check_boolean = false;
                }
            }
        });




    register_button.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
                if(user_email_boolean && user_password_check_boolean){
                    if(user_password.getText().toString().equals(user_password_confirm.getText().toString())){

                    }else{

                    }

                }else{

                }
        }
    });


    }
    //여기까지가 onCreate() 끝나는 곳
    class BackgroundTask extends AsyncTask<Void, Void, String> {
        String target;
        TextView email_check = (TextView) findViewById(R.id.r_emailcheck);
        public void String(String string) {
            target = string;
        }
        @Override
        protected void onPreExecute(){ // 특정 php 타일을 초기화하는 상태
            //target = "http://joonandhoon.dothome.co.kr/mn/php/available_email_checker.php?user_email=" + written_user_email;
        }
        @Override
        protected String doInBackground(Void... voids){
            try{
                URL url = new URL(target);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String temp;
                StringBuilder stringBuilder = new StringBuilder();
                while((temp =bufferedReader.readLine())!=null){
                    stringBuilder.append(temp + "\n");
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return stringBuilder.toString().trim();
            }catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }
        @Override
        public void onProgressUpdate(Void... values){
            super.onProgressUpdate(values);
        }
        @Override
        public void onPostExecute (String result){
            if (result.toString().equals("usable")) {
                //사용 가능 한 이메일 일때
                email_check.setText("사용 가능한 이메일 입니다");
                email_check.setTextColor(getResources().getColor(R.color.colorGreen));
                user_email_boolean = true;


            }else if(result.toString().equals("taken")) {
                //이미 사용중인 이메일 일때
                email_check.setText("이미 사용중인 이메일 입니다");
                email_check.setTextColor(getResources().getColor(R.color.colorRed));
                user_email_boolean = false;
            }else if(result.toString().equals("notformatted")){
                email_check.setText("잘못된 이메일 형식 입니다");
                email_check.setTextColor(getResources().getColor(R.color.colorRed));
                user_email_boolean = false;
            }

        }
    }
}
