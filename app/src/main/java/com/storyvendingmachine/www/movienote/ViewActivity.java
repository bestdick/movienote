package com.storyvendingmachine.www.movienote;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class ViewActivity extends AppCompatActivity {

String movie_title;
String movie_main;
String author;
String w_date;
String w_time;
String w_year;
String images;
String makeUrl;


    Bitmap bmImg;






int i =0;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        ArrayList<String> url_array = null;
        ArrayList<Integer> id_num_array = null;



        LinearLayout inner_layout = (LinearLayout) findViewById(R.id.layout);
        Intent intent = getIntent();
        TextView titleTextView = (TextView) findViewById(R.id.texView);


        movie_main = intent.getStringExtra("movie_main");
        movie_title = intent.getStringExtra("movie_title");
        author = intent.getStringExtra("author");
        w_date = intent.getStringExtra("w_date");
        w_time = intent.getStringExtra("w_time");
        w_year = intent.getStringExtra("w_year");
        images = intent.getStringExtra("images");

        titleTextView.setText(movie_title);// 무비 타이틀

        String[] items = movie_main.split("####");
        int size = items.length;




        //for(int i=0; i<size; i++){
while(i<size){

            if(i%2 == 0){
                //even
                TextView mainTextView = new TextView(ViewActivity.this);
                mainTextView.setId(100+i);//생성되는 editText의 아이디는 100+1  ,101+1 이므로 101, 102, 103 ---이런식의 아이디를 갖게된다.
                mainTextView.setBackgroundResource(0);
                LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                mainTextView.setLayoutParams(layoutParams);
                mainTextView.setTextSize(TypedValue.COMPLEX_UNIT_DIP,15);
                mainTextView.setText(items[i].toString());
                inner_layout.addView(mainTextView);

            }else{
                //odd
                makeUrl = "http://joonandhoon.dothome.co.kr/mn/php/usable_image/resized_"+items[i].replace("#$%#$%", ".");
                Toast.makeText(ViewActivity.this, i +" and " + makeUrl, Toast.LENGTH_LONG).show();
                ImageView imageView= new ImageView(ViewActivity.this);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                layoutParams.gravity = Gravity.CENTER;
                imageView.setId(100000+i);
                imageView.setLayoutParams(layoutParams);
                inner_layout.addView(imageView);


                back backgroundTask = new back();
                backgroundTask.url_made=makeUrl;
                backgroundTask.num=100000+i;
                backgroundTask.execute();

                //url_array.add(makeUrl);
                //id_num_array.add(100000+i);

            }
    i++;
        }




    }

    private class back extends AsyncTask<Void, Void, Bitmap>{
//        ArrayList<String> url_array;
//        ArrayList<Integer> id_num_array;
//        ArrayList<Bitmap> bitmap_array;
        int num;
        String url_made;
        @Override
        protected void onPreExecute(){ // 특정 php 타일을 초기화하는 상태
        }

        @Override
        protected Bitmap doInBackground(Void... Void) {
            // TODO Auto-generated method stub
            try{

                    URL url = new URL(url_made);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setDoInput(true);
                    conn.connect();
                    InputStream is = conn.getInputStream();
                    //bmImg = BitmapFactory.decodeStream(is);

                    //bitmap_array.add(BitmapFactory.decodeStream(is));

                bmImg = BitmapFactory.decodeStream(is);

            }catch(IOException e){
                e.printStackTrace();
            }
            return bmImg;
        }

        @Override
        public void onProgressUpdate(Void... values){
            super.onProgressUpdate(values);
        }

        protected void onPostExecute(Bitmap img){
            ImageView find = (ImageView) findViewById(num);
            find.setImageBitmap(bmImg);
        }

    }




}
