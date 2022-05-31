package com.gachon.rental_system;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
//사용자 반납 신청 화면

public class Fragment5 extends Fragment {
    private boolean running;
    ViewGroup v;
    private TextView rentalT;
    private TextView returnT;
    Button button;
    public String itemValue;
    public String x;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = (ViewGroup) inflater.inflate(R.layout.fragment5,container,false);

        rentalT = (TextView) v.findViewById(R.id.txt1);
        returnT = (TextView) v.findViewById(R.id.txt2);
        button=(Button) v.findViewById(R.id.returnB);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "http://ikmin7373.dothome.co.kr/ItemReturn.php";
                Map<String, String> parameters = new HashMap<String, String>();
                parameters.put("UserId", ((com.gachon.rental_system.LoginActivity) com.gachon.rental_system.LoginActivity.context_main).UserId);
                Toast.makeText(getActivity(), " 반납이 완료되었습니다.", Toast.LENGTH_SHORT).show();

                final JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(parameters),
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {   //밑에서 Request가 보내지고 결과로 온 Reponse가 JsonResponse를 통해 다뤄진다.
                                    JSONObject jsonResponse = response;
                                    //result = [{"userID":"bae","userStudentID":""}]
                                } catch (Exception e)//예외처리
                                {
                                    e.printStackTrace();//간단하게 예외처리함
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(((com.gachon.rental_system.LoginActivity) com.gachon.rental_system.MainActivity.context_main), "DB 연동 에러", Toast.LENGTH_LONG).show();
                            }
                        }) {

                };
                //실제로 로그인을 보낼 수 있는 Request : userID, password를 받아 리스너를 보냄
                RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
                queue.add(jsObjRequest);
            }
        });

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        running = true;
        Thread thread1 = new BackgroundThread();
        thread1.start();
    }
    @Override
    public void onPause() {
        super.onPause();
        running = false;
    }
    class BackgroundThread extends Thread {
        public void run(){
            while (running){
                try{
                    Thread.sleep(1000);

                    long now = System.currentTimeMillis();
                    Date date1 = new Date(now);
                    SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy년 MM월 dd일 hh:mm:ss");
                    String getTime = simpleDate.format(date1);
                    rentalT.setText(getTime); // 현재 날짜, 시각을 사용자에게 보여줌
/*
                    Calendar cal = Calendar.getInstance();
                    Date date =simpleDate.parse(getTime);
                    cal.setTime(date);
                    cal.add(Calendar.DATE, 2); //대여가능 기간이 2일이라고 가정
                    Date date2=new Date(cal.getTimeInMillis());
                    String getTime2=simpleDate.format(date2);
                    returnT.setText(getTime2); // 반납 날짜, 시각
 */
                    returnT.setText("테스트 중");
                    ////////////////////////////////////////////////////////////////////////////////
                    //데이터베이스에서 대여한 물품의 마감 일시를 가져와서 띄워줘야함
                    ////////////////////////////////////////////////////////////////////////////////

                }catch(Exception ex){}
            }
        }
    }
}
