package com.storyvendingmachine.www.movienote;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.util.ArrayMap;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class WriteActivity extends AppCompatActivity {
    private int counter = 0;
    private LinearLayout linearLayout;
    private Button add_button;
    ArrayList<String> image_name_array = new ArrayList<String>();
    ArrayList<String> imgPath_array = new ArrayList<String>();
    int serverResponseCode = 0;
    String user_email;
    String upLoadServerUri = "http://joonandhoon.dothome.co.kr/mn/php/upload_image_request.php";
    ProgressDialog dialog = null;
    ArrayList<Integer> Degree =new ArrayList<>();
    String imgPath;

    getScreeninfo info = new getScreeninfo();
    int S_height = info.getScreenHeight();
    int S_width = info.getScreenWidth();


    Intent CropIntent;
    Uri uri;

    final int REQ_CODE_SELECT_IMAGE=100;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);

        //permission
        ActivityCompat.requestPermissions(WriteActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 0);
        ActivityCompat.requestPermissions(WriteActivity.this, new String[]{Manifest.permission.INTERNET}, 0);
        ActivityCompat.requestPermissions(WriteActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
        ActivityCompat.requestPermissions(WriteActivity.this, new String[]{Manifest.permission.ACCESS_NETWORK_STATE}, 0);


        //permission





        linearLayout = (LinearLayout) findViewById(R.id.inner_layout);
        add_button = (Button) findViewById(R.id.add_button);




        Button main_button = (Button) findViewById(R.id.mainbutton);
        Button button =(Button) findViewById(R.id.add_button);
        EditText mainEditText_1 = (EditText) findViewById(R.id.mainEditText_1);
        final TextView welcomeMessage = (TextView) findViewById(R.id.welcomeText);

        Intent intent =getIntent();
         user_email = intent.getStringExtra("user_email");
        final String user_password = intent.getStringExtra("user_password");
        final String user_name = intent.getStringExtra("user_name");
        final String user_nickname = intent.getStringExtra("user_nickname");



        main_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WriteActivity.this, MemberMainActivity.class);
                intent.putExtra("user_email", user_email);
                intent.putExtra("user_password", user_password);
                intent.putExtra("user_name", user_name);
                intent.putExtra("user_nickname", user_nickname);
                WriteActivity.this.startActivity(intent);
                WriteActivity.this.finish();
            }
        });


        final ImageView image_1 = (ImageView) findViewById(R.id.image_1);


        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(view.getId() == add_button.getId()){
                    counter++; //먼저 카운터가 인크리멘트 된다

                    welcomeMessage.setText("TextView " + counter);


                    EditText editText = new EditText(WriteActivity.this);
                    editText.setHintTextColor(getResources().getColor(R.color.colorHint));
                    editText.setId(100+counter);//생성되는 editText의 아이디는 100+1  ,101+1 이므로 101, 102, 103 ---이런식의 아이디를 갖게된다.
                    editText.setBackgroundResource(0);
                    editText.setHint("내용을 입력해 주세요");
                    editText.setSingleLine(false);
                    editText.setImeOptions(EditorInfo.IME_FLAG_NO_ENTER_ACTION);
                    editText.setTextSize(TypedValue.COMPLEX_UNIT_DIP,15);

                    ImageView imageView= new ImageView(WriteActivity.this);
                    LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    layoutParams.gravity = Gravity.CENTER;
                    imageView.setLayoutParams(layoutParams);
                    //imageView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                    //imageView.setBackgroundResource(R.drawable.cam);
                    imageView.setBackgroundResource(0);
                    imageView.setId(10000+counter);//생성되는 imageView 아이디는 10000+0  ,10000 +1 이므로 10000, 10001, 10002, 10003 ---이런식의 아이디를 갖게된다.

                    linearLayout.addView(editText);
                    linearLayout.addView(imageView);



                    Intent intent = new Intent(Intent.ACTION_PICK);
                    intent.setType(android.provider.MediaStore.Images.Media.CONTENT_TYPE);
                    intent.setData(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, REQ_CODE_SELECT_IMAGE);



                }


            }//버튼 클릭시 실행되는 명령어가 끝나는 부분



        });//완전 끝나는 부분


        Button upload_button = (Button) findViewById(R.id.upload_button);
        upload_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if(success){
//                                AlertDialog.Builder builder = new AlertDialog.Builder(WriteActivity.this);
//                                builder.setMessage("작성하신 글을 업로드 하였습니다.")
//                                        .setPositiveButton("확인", null)
//                                        .create()
//                                        .show();

//                                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
//                                RegisterActivity.this.startActivity(intent);
                            }else{
//                                AlertDialog.Builder builder = new AlertDialog.Builder(WriteActivity.this);
//                                builder.setMessage("작성하신 글을 업로드 하지 못했습니다.")
//                                        .setNegativeButton("다시시도", null)
//                                        .create()
//                                        .show();
                            }
                        }
                        catch(JSONException e)
                        {
                            e.printStackTrace();
                        }
                    }
                };

                //모든 텍스트 가져오
                String movie_title;// 영화 제목
                EditText mt = (EditText) findViewById(R.id.titleEditText);
                movie_title = mt.getText().toString();

                String movie_main;//영화  줄거리
                EditText mm = (EditText) findViewById(R.id.mainEditText_1);
                movie_main = mm.getText().toString();

                ArrayList<String> whole_text_array = new ArrayList<String>(); // 나누어진 텍스트의 내용을 한번에 몹는것

             // whole_text_array.add(movie_main);

                int temp =1;


                while(temp <=counter){
                    EditText temp_text = (EditText) findViewById(100+temp);
                    whole_text_array.add(temp_text.getText().toString());
                    temp++;
                }

                int temp_temp =0;
                StringBuilder m_temp = new StringBuilder();
                StringBuilder i_temp = new StringBuilder();
                StringBuilder whole_temp = new StringBuilder();


                while(temp_temp<=counter-1){
                    m_temp.append(whole_text_array.get(temp_temp));
                    i_temp.append(image_name_array.get(temp_temp)+"##");
                     whole_temp.append(image_name_array.get(temp_temp)+ "####" +whole_text_array.get(temp_temp) +"####");
                    temp_temp++;
                }



              //  welcomeMessage.setText(user_email + "/////////" + movie_title + "/////////"+ movie_main+ whole_temp + "/////////"+i_temp);
                WriteRequest writeRequest = new WriteRequest(user_email, movie_title, movie_main +"####"+ whole_temp.toString(), image_name_array.get(0).toString(), i_temp.toString(), responseListener);
                RequestQueue queue = Volley.newRequestQueue(WriteActivity.this);
                queue.add(writeRequest);

                dialog = ProgressDialog.show(WriteActivity.this, "", "Uploading file...", true);
                new Thread(new Runnable() {
                    public void run() {
                        runOnUiThread(new Runnable() {
                            public void run() {
                                //messageText.setText("uploading started.....");
                            }
                        });
                        for(int i= 0; i<imgPath_array.size(); i++)
                       
                            uploadFile(imgPath_array.get(i), image_name_array.get(i));
                    }
                }).start();


            }////onclick upload 끝나는 것
        });

    }
    public void ImageCropFunction() {

        // Image Crop Code
        try {
            CropIntent = new Intent("com.android.camera.action.CROP");
            CropIntent.setDataAndType(uri, "image/*");

            CropIntent.putExtra("crop", "true");
            CropIntent.putExtra("outputX", 180);
            CropIntent.putExtra("outputY", 180);
            CropIntent.putExtra("aspectX", 3);
            CropIntent.putExtra("aspectY", 4);
            CropIntent.putExtra("scaleUpIfNeeded", true);
            CropIntent.putExtra("return-data", true);

            startActivityForResult(CropIntent, 1);

        } catch (ActivityNotFoundException e) {

        }
    }

    public Bitmap rotateImage(Bitmap src, float degree) {
        // Matrix 객체 생성
        Matrix matrix = new Matrix();
        // 회전 각도 셋팅
        matrix.postRotate(degree);
        // 이미지와 Matrix 를 셋팅해서 Bitmap 객체 생성
        return Bitmap.createBitmap(src, 0, 0, src.getWidth(),src.getHeight(), matrix, true);
    }

    public String getImageNameToUri(Uri data) {
        String[] proj = { MediaStore.Images.Media.DATA };
        Cursor cursor = managedQuery(data, proj, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        imgPath = cursor.getString(column_index);  // === >>> 이놈이 이미지 path 실재 이미지가 저장되어있는놈.
        imgPath_array.add(imgPath);
        String imgName = imgPath.substring(imgPath.lastIndexOf("/")+1);
//        Toast.makeText(getBaseContext(), "heheheh"+imgPath, Toast.LENGTH_SHORT).show();
         return imgName;
    }

    public Bitmap getResizedBitmap(Bitmap bm, int newHeight, int newWidth) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        Bitmap resizedBitmap;
        if(newHeight>=height || newWidth>=width){
            resizedBitmap = bm;
        }else{
            float scaleWidth = ((float) newWidth) / width;
            float scaleHeight = ((float) newHeight) / height;
            // create a matrix for the manipulation
            Matrix matrix = new Matrix();

            // resize the bit map
            matrix.postScale(scaleWidth, scaleHeight);

            // recreate the new Bitmap
            resizedBitmap = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, false);
        }

        return resizedBitmap;
    }



    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        Toast.makeText(getBaseContext(), "resultCode : "+resultCode,Toast.LENGTH_SHORT).show();

        if(requestCode == REQ_CODE_SELECT_IMAGE) {

            if(resultCode== Activity.RESULT_OK) {
                Degree.add(counter-1, 0);
                Toast.makeText(getBaseContext(), "디그리" +Degree.size(), Toast.LENGTH_SHORT ).show();
                try {
                    //Uri에서 이미지 이름을 얻어온다.
                    String name_Str = getImageNameToUri(data.getData());
                    //이미지 데이터를 비트맵으로 받아온다.
                  final Bitmap image_bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());

                  final Bitmap resized = getResizedBitmap(image_bitmap, (S_height/4)*3, S_width);


                    final ImageView image = (ImageView) findViewById(R.id.image_1);  /// <----- 여기에 이미지뷰 아이디를 넣는 곳이다.

                    LinearLayout.LayoutParams layoutParams = null;
                    if (image.getDrawable() != null) {
                        //이미지 뷰에 사진이 들어가있을때  ---- 두번째 이미지 부터....
                        int temp = 10000 + counter - 1;
                        final ImageView rest_image = (ImageView) findViewById(temp);
//
                        layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        rest_image.setLayoutParams(layoutParams);
                        rest_image.setScaleType(ImageView.ScaleType.FIT_XY);
                        rest_image.setImageBitmap(resized);
                        rest_image.setOnLongClickListener(new View.OnLongClickListener() {
                            @Override
                            public boolean onLongClick(View view) {
                                Intent intent = new Intent(WriteActivity.this, ImageFixActivity.class);
                                intent.putExtra("image" , rest_image.getDrawingCache());
                                startActivity(intent);

//                                Degree.set(counter - 1, Degree.get(counter - 1) + 90);
//                                rest_image.setImageBitmap(rotateImage(resized, Degree.get(1)));
//                                Toast.makeText(getBaseContext(), "클릭됨" + Degree.get(counter - 1).toString(), Toast.LENGTH_SHORT).show();
                                return false;
                            }
                        });
//                        rest_image.setOnTouchListener(new View.OnTouchListener() {
//                            @Override
//                            public boolean onTouch(View view, MotionEvent motionEvent) {
//
//                                Degree.set(counter - 1, Degree.get(counter - 1) + 90);
//                                rest_image.setImageBitmap(rotateImage(resized, Degree.get(1)));
//                                Toast.makeText(getBaseContext(), "클릭됨" + Degree.get(counter - 1).toString(), Toast.LENGTH_SHORT).show();
//                                return false;
//                            }
//                        });
                        Toast.makeText(getBaseContext(), "name_Str : " + name_Str, Toast.LENGTH_SHORT).show();
                        DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
                        Date date = new Date();
                        String[] separated = name_Str.split("\\.");
                        // image_name_array.add(name_Str);

                        StringBuilder image_name_builder = new StringBuilder();
                        image_name_builder.append(user_email);
                        image_name_builder.append(dateFormat.format(date).toString());
                        image_name_builder.append("#$%#$%");
                        image_name_builder.append(separated[1].toString());
                        image_name_array.add(image_name_builder.toString());
                        //Toast.makeText(getBaseContext(), "name_Str : "+image_name_builder.toString() , Toast.LENGTH_SHORT).show();

                    } else {
                        //이미지 뷰에 사진이 들어가있지 않을때 --- 처음 맨 처음 이미지

//                        image.setOnTouchListener(new View.OnTouchListener() {
//                            @Override
//                            public boolean onTouch(View view, MotionEvent motionEvent) {
//
//                                Degree.set(0, Degree.get(0) + 90);
//                                image.setImageBitmap(rotateImage(resized, Degree.get(0)));
//                                Toast.makeText(getBaseContext(), "클릭됨" + Degree.get(0).toString(), Toast.LENGTH_SHORT).show();
//                                return false;
//                            }
//                        });
                        image.setOnLongClickListener(new View.OnLongClickListener() {
                            @Override
                            public boolean onLongClick(View view) {
                                Degree.set(0, Degree.get(0) + 90);
                                image.setImageBitmap(rotateImage(resized, Degree.get(0)));
                                Toast.makeText(getBaseContext(), "클릭됨" + Degree.get(0).toString(), Toast.LENGTH_SHORT).show();
                                return false;
                            }
                        });
                        //배치해놓은 ImageView에 set
                        layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        image.setLayoutParams(layoutParams);
                        image.setScaleType(ImageView.ScaleType.FIT_XY);
                        image.setImageBitmap(resized);
                        Toast.makeText(getBaseContext(), "name_Str : " + name_Str, Toast.LENGTH_SHORT).show();
                        DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
                        Date date = new Date();
                        String[] separated = name_Str.split("\\.");
                        //  image_name_array.add(name_Str);

                        StringBuilder image_name_builder = new StringBuilder();
                        image_name_builder.append(user_email);
                        image_name_builder.append(dateFormat.format(date).toString());
                        image_name_builder.append("#$%#$%");
                        image_name_builder.append(separated[1].toString());
                        image_name_array.add(image_name_builder.toString());
                        //Toast.makeText(getBaseContext(), "name_Str : "+image_name_builder.toString() , Toast.LENGTH_SHORT).show();

                    }


                } catch (FileNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }





    ///이미지 업로드하는 프로세스

    public int uploadFile(String sourceFileUri, String image_Name) {
        String fileName = sourceFileUri;
        String imageName = image_Name;
        HttpURLConnection conn = null;
        DataOutputStream dos = null;
        String lineEnd = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";
        int bytesRead, bytesAvailable, bufferSize;
        byte[] buffer;
        int maxBufferSize = 100 * 1024 * 1024;
        File sourceFile = new File(sourceFileUri);

        if (!sourceFile.isFile()) {
            dialog.dismiss();
          //  Log.e("uploadFile", "Source File not exist :" +uploadFilePath + "" + uploadFileName);
            runOnUiThread(new Runnable() {
                public void run() {
               //     messageText.setText("Source File not exist :"
                 //           +uploadFilePath + "" + uploadFileName);
                }
            });
            return 0;
        }else {
            try {
                // open a URL connection to the Servlet
                FileInputStream fileInputStream = new FileInputStream(sourceFile);
                URL url = new URL(upLoadServerUri);
                // Open a HTTP  connection to  the URL
                conn = (HttpURLConnection) url.openConnection();
                conn.setDoInput(true); // Allow Inputs
                conn.setDoOutput(true); // Allow Outputs
                conn.setUseCaches(false); // Don't use a Cached Copy
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Connection", "Keep-Alive");
                conn.setRequestProperty("ENCTYPE", "multipart/form-data");
                conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
                conn.setRequestProperty("uploaded_file", fileName);
                conn.setRequestProperty("imageName", imageName);

                dos = new DataOutputStream(conn.getOutputStream());


                dos.writeBytes(twoHyphens + boundary + lineEnd);
                dos.writeBytes("Content-Disposition: form-data; name=\"uploaded_file\";filename=\"" + fileName + "\"" + lineEnd);
                dos.writeBytes(lineEnd);
                //  위는 파일

                // create a buffer of  maximum size
                bytesAvailable = fileInputStream.available();
                bufferSize = Math.min(bytesAvailable, maxBufferSize);
                buffer = new byte[bufferSize];
                // read file and write it into form...
                bytesRead = fileInputStream.read(buffer, 0, bufferSize);
                while (bytesRead > 0) {
                    dos.write(buffer, 0, bufferSize);
                    bytesAvailable = fileInputStream.available();
                    bufferSize = Math.min(bytesAvailable, maxBufferSize);
                    bytesRead = fileInputStream.read(buffer, 0, bufferSize);
                }
                // send multipart form data necesssary after file data...
                dos.writeBytes(lineEnd);


                dos.writeBytes(twoHyphens + boundary + lineEnd);
                dos.writeBytes("Content-Disposition: form-data; name=\"imageName\"" + lineEnd);
                dos.writeBytes(lineEnd);
                dos.writeBytes(imageName);
                dos.writeBytes(lineEnd);
                // 위는 text




                dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);
                // Responses from the server (code and message)
                serverResponseCode = conn.getResponseCode();
                String serverResponseMessage = conn.getResponseMessage();
                Log.i("uploadFile", "HTTP Response is : " + serverResponseMessage + ": " + serverResponseCode);
                if(serverResponseCode == 200){
                    runOnUiThread(new Runnable() {
                        public void run() {
                           // String msg = "File Upload Completed.\n\n See uploaded file here : \n\n" +uploadFileName;
                          //  messageText.setText(msg);
                            Toast.makeText(WriteActivity.this, "File Upload Complete.",
                                    Toast.LENGTH_SHORT).show();

                        }
                    });
                }

                //close the streams //
                fileInputStream.close();
                dos.flush();
                dos.close();
            } catch (MalformedURLException ex) {
                dialog.dismiss();
                ex.printStackTrace();
                runOnUiThread(new Runnable() {
                    public void run() {
                        TextView welcometext = (TextView) findViewById(R.id.welcomeText);
                        welcometext.setText("MalformedURLException Exception : check script url.");
                        Toast.makeText(WriteActivity.this, "MalformedURLException",
                                Toast.LENGTH_SHORT).show();
                    }
                });
                Log.e("Upload file to server", "error: " + ex.getMessage(), ex);
            } catch (final Exception e) {
                dialog.dismiss();
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    public void run() {
//                        messageText.setText("Got Exception : see logcat ");
                        Toast.makeText(WriteActivity.this, "Got Exception : see logcat ", Toast.LENGTH_SHORT).show();
                        TextView welcometext = (TextView) findViewById(R.id.welcomeText);

                        welcometext.setText(e.getStackTrace().toString());
                        //Toast.makeText(WriteActivity.this, e.getMessage().toString() + e.getStackTrace().toString(), Toast.LENGTH_LONG).show();
                    }
                });
                Log.e("Exception", "Exception : " + e.getMessage(), e);
            }
            dialog.dismiss();
            return serverResponseCode;
        } // End else block

    }





}
