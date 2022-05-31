package com.gachon.rental_system;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    private AlertDialog dialog;
    public static Context context_main;
    public String UserId;
    public String UserPwd;
    public EditText idText;
    public EditText passwordText;
    public Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        context_main = this;

        //TextView 객체로 변수선언 후 클릭 이벤트 발생
        TextView registerButton = (TextView) findViewById(R.id.sign_up_Button);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent로 LoginActivity > RegisterActivity로 화면 전환
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                LoginActivity.this.startActivity(intent);
            }
        });

        //로그인 id, password, button 초기화
        idText = (EditText) findViewById(R.id.idText);
        passwordText = (EditText) findViewById(R.id.passwordText);
        loginButton = (Button) findViewById(R.id.loginButton);

        //로그인 버튼 이벤트처리
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserId = idText.getText().toString();
                UserPwd = passwordText.getText().toString();

                String url = "http://ikmin7373.dothome.co.kr/Login.php";

                Map<String,String> parameters = new HashMap<String, String>();
                parameters.put("UserId", UserId);
                parameters.put("UserPwd", UserPwd);
                //빈칸이 있을 때
                if(UserId.equals("")||UserPwd.equals(""))
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                    dialog = builder.setMessage("빈 칸 없이 입력해주세요.")
                            .setNegativeButton("확인", null)
                            .create();
                    dialog.show();
                    return;
                }

                final JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(parameters),
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try
                                {   //밑에서 Request가 보내지고 결과로 온 Reponse가 JsonResponse를 통해 다뤄진다.
                                    JSONObject jsonResponse = response;
                                    boolean success = jsonResponse.getBoolean("success");
                                    //로그인 성공 : (Intent) 로그인 액티비티 >> 메인 액티비티
                                    if(success){
                                        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                                        dialog = builder.setMessage("로그인에 성공했습니다.")
                                                .setPositiveButton("확인", null)
                                                .create();
                                        dialog.show();

                                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                        LoginActivity.this.startActivity(intent);
                                        finish();//LoginActivity 닫아줌
                                    } else {//로그인 실패
                                        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                                        dialog = builder.setMessage("계정을 다시 확인하세요.")
                                                .setNegativeButton("다시 시도", null)
                                                .create();
                                        dialog.show();
                                    }
                                }
                                catch (Exception e)//예외처리
                                {
                                    e.printStackTrace();//간단하게 예외처리함
                                }
                            }
                },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(LoginActivity.this, "DB 연동 에러", Toast.LENGTH_LONG).show();
                            }
                        }){

                };
                //실제로 로그인을 보낼 수 있는 Request : userID, password를 받아 리스너를 보냄
                RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
                queue.add(jsObjRequest);
            }
        });
    }

    //현재 액티비티가 종료 되었다면
    @Override
    protected void onStop(){
        super.onStop();
        //현재 다이얼로그가 켜져있을 때 함부로 종료되지 않도록 한다.
        if (dialog != null){
            dialog.dismiss();;
            dialog = null;
        }
    }

}
