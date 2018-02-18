package com.storyvendingmachine.www.movienote;

import android.graphics.Bitmap;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2018-01-25.
 */

public class WriteRequest extends StringRequest {
    final static private String URL = "http://joonandhoon.dothome.co.kr/mn/php/write_request.php";
    private Map<String, String> parameters;

    public WriteRequest(String user_email, String movie_title, String movie_main, String thumb_nail, String image_names, Response.Listener<String> listener){
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("user_email", user_email);
        parameters.put("movie_title", movie_title);
        parameters.put("movie_main", movie_main);
        parameters.put("thumb_nail", thumb_nail);
        parameters.put("image_names", image_names);
    }
    @Override
    public Map<String, String> getParams(){
        return parameters;
    }
}
