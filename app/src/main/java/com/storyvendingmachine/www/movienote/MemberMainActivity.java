package com.storyvendingmachine.www.movienote;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Member;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MemberMainActivity extends AppCompatActivity {

    private ListView listView;
    private listAdapter adapter;
    private List<list> list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_main);

        TextView welcomeTextView = (TextView) findViewById(R.id.welcomeText);
        Button write_button = (Button) findViewById(R.id.writebutton);
        Intent intent =getIntent();

        final String user_email = intent.getStringExtra("user_email");
        final String user_password = intent.getStringExtra("user_password");
        final String user_name = intent.getStringExtra("user_name");
        final String user_nickname = intent.getStringExtra("user_nickname");

        welcomeTextView.setText(user_nickname + " 님 환영합니다");

        new BackgroundTask().execute();

        write_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MemberMainActivity.this, WriteActivity.class);
                intent.putExtra("user_email", user_email);
                intent.putExtra("user_password", user_password);
                intent.putExtra("user_name", user_name);
                intent.putExtra("user_nickname", user_nickname);
                MemberMainActivity.this.startActivity(intent);
                MemberMainActivity.this.finish();
            }
        });
    }

    long pressedTime = 0;
    @Override
    public void onBackPressed() {
        if ( pressedTime == 0 ) {
            Toast.makeText(MemberMainActivity.this, " 한 번 더 누르면 종료됩니다." , Toast.LENGTH_LONG).show();
            pressedTime = System.currentTimeMillis();
        }else {
            int seconds = (int) (System.currentTimeMillis() - pressedTime);

            if ( seconds > 2000 ) {
                Toast.makeText(MemberMainActivity.this, " 한 번 더 누르면 종료됩니다." , Toast.LENGTH_LONG).show();
                pressedTime = 0 ;
            }else {
                super.onBackPressed();
                finish(); // app 종료 시키기
            }
        }
    }



class BackgroundTask extends AsyncTask<Void, Void, String> {
    String target;

    @Override
    protected void onPreExecute(){ // 특정 php 타일을 초기화하는 상태
        String user_email = getIntent().getStringExtra("user_email");
        target   = "http://joonandhoon.com/mn/php/test.php?author="+user_email;
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
        //할할일을 여기 하면된다
        listView = (ListView) findViewById(R.id.listView2);
        list = new ArrayList<list>();

        adapter = new listAdapter(getApplicationContext(), list);
        listView.setAdapter(adapter);


        try {
            JSONObject jsonObject = new JSONObject(result);
            JSONArray jsonArray = jsonObject.getJSONArray("response");
            int count = 0;
            String movie_title, movie_main, author, w_date, w_time, w_year, thumb_nail, image_names;
            while(count<jsonArray.length()){
                JSONObject object = jsonArray.getJSONObject(count);
                movie_title = object.getString("movie_title");
                movie_main = object.getString("movie_main");
                author = object.getString("author");
                w_date = object.getString("w_date");
                w_time = object.getString("w_time");
                w_year = object.getString("w_year");
                thumb_nail = object.getString("thumb_nail");
                image_names = object.getString("image_names");



//              list.add(new list(movie_title, movie_main, author, w_date, w_time, w_year));
                list added_list = new list(movie_title, movie_main, author, w_date, w_time, w_year, thumb_nail, image_names);
                list.add(added_list);

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                        Intent intent = new Intent(MemberMainActivity.this, ViewActivity.class);
                        intent.putExtra("movie_title", list.get(position).getMovie_title().toString());
                        intent.putExtra("movie_main", list.get(position).getMovie_main().toString());
                        intent.putExtra("author", list.get(position).getAuthor().toString());
                        intent.putExtra("w_date", list.get(position).getW_date().toString());
                        intent.putExtra("w_time", list.get(position).getW_time().toString());
                        intent.putExtra("w_year", list.get(position).getThumb_nail().toString());
                        intent.putExtra("image_names", list.get(position).getImage_names().toString());


                        MemberMainActivity.this.startActivity(intent);
                    }
                });


                count++;

            }
        }catch (Exception e){
            e.printStackTrace();
        }



//        String [] result_big_array;
//        String [] temp_buffer;
//
//        listView = (ListView) findViewById(R.id.listView2);
//          list = new ArrayList<list>();
//
//        result_big_array = result.split("!@#!@#");
//        int temp_big_array =result_big_array.length;
//
//        for(int i= 0; i <temp_big_array; i++){
//            temp_buffer = result_big_array[i].split("##");
//            list.add(new list(temp_buffer[0].toString(),temp_buffer[1].toString(),temp_buffer[2].toString(),temp_buffer[3].toString(),temp_buffer[4].toString(), temp_buffer[5].toString()));
//        }
//        adapter = new listAdapter(getApplicationContext(), list);
//        listView.setAdapter(adapter);




//        listView = (ListView) findViewById(R.id.listView2);
//        list = new ArrayList<list>();
//
//        list.add(new list("홍길동", "홍길동", "홍길동","12", "12", "21"));
//        list.add(new list("홍글동", "홍글동", "홍글동","12", "12", "21"));
//
//        adapter = new listAdapter(getApplicationContext(), list);
//        listView.setAdapter(adapter);

    }
}
}