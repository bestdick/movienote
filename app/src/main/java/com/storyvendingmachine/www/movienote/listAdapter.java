package com.storyvendingmachine.www.movienote;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.InputStream;
import java.net.URL;
import java.util.List;

/**
 * Created by Administrator on 2018-01-20.
 */

public class listAdapter extends BaseAdapter {
    private Context context;
    private List<list> list;

    Handler handler = new Handler();

    public listAdapter(Context context, List<list> list){
        this.context =context;
        this.list =list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = View.inflate(context, R.layout.list, null);
        TextView movie_title = (TextView) v.findViewById(R.id.movie_title);
        TextView author = (TextView) v.findViewById(R.id.auther);
        TextView written_date = (TextView) v.findViewById(R.id.written_date);
        final ImageView imageView = (ImageView) v.findViewById(R.id.imageImage);
        final String image_url = "http://joonandhoon.dothome.co.kr/mn/php/thumb_nails_sq/resized_"+list.get(i).getThumb_nail();


        Thread t = new Thread(new Runnable() {


            @Override
            public void run() {    // 오래 거릴 작업을 구현한다
                // TODO Auto-generated method stub
                try{
                    // 걍 외우는게 좋다 -_-;

                    URL url = new URL(image_url);
                    InputStream is = url.openStream();
                    final Bitmap bm = BitmapFactory.decodeStream(is);
                    handler.post(new Runnable() {

                        @Override
                        public void run() {  // 화면에 그려줄 작업
                            imageView.setImageBitmap(bm);

                        }
                    });
                    imageView.setImageBitmap(bm); //비트맵 객체로 보여주기


                } catch(Exception e){

                }

            }
        });

        t.start();

        movie_title.setText(list.get(i).getMovie_title());
        author.setText(list.get(i).getAuthor());
        written_date.setText(list.get(i).getW_date()+"  "+list.get(i).getW_time());

        v.setTag(list.get(i));

        return v;
    }
}
