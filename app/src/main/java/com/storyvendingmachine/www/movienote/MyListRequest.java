package com.storyvendingmachine.www.movienote;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2018-01-22.
 */

public class MyListRequest extends StringRequest {

    final static private String URL= "http://joonandhoon.dothome.co.kr/mn/php/test.php?author=ssijcfe@gmail.com";
    private Map<String, String> parameters;

    public MyListRequest(String user_email, String user_password, Response.Listener<String> responseListener){
        super(Request.Method.POST, URL, responseListener, null);
        parameters = new HashMap<>();
        parameters.put("user_email", user_email);

    }
    @Override
    public Map<String, String> getParams(){
        return parameters;
    }
}
