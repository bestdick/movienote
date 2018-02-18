package com.storyvendingmachine.www.movienote;

        import com.android.volley.Response;
        import com.android.volley.toolbox.StringRequest;

        import java.net.URL;
        import java.util.HashMap;
        import java.util.Map;

/**
 * Created by Administrator on 2018-01-18.
 */

public class LoginRequest extends StringRequest {
    final static private String URL= "http://joonandhoon.dothome.co.kr/mn/php/login.php";
    private Map<String, String> parameters;

    public LoginRequest(String user_email, String user_password, Response.Listener<String> responseListener){
        super(Method.POST, URL, responseListener, null);
        parameters = new HashMap<>();
        parameters.put("user_email", user_email);
        parameters.put("user_password", user_password);
    }
    @Override
    public Map<String, String> getParams(){
        return parameters;
    }
}
