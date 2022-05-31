package com.gachon.rental_system;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
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

import java.util.HashMap;
import java.util.Map;
//사용자 대여 신청 화면

public class Fragment1 extends Fragment {
    //위젯 초기화
    ViewGroup v;

    private ListView listview;
    private com.gachon.rental_system.ListViewAdapter adapter;

    public TextView UserName;
    public TextView StudentId;
    public TextView item_name;
    public TextView item_rental_date;
    public TextView item_return_date;
    public TextView charger_number;

    int i=3;
    String result;
    String result2;

    String a,b,c,d,e;
    String a2,b2,c2,d2,e2;
    String x_number;
    Integer real_number;

    String[] array;
    String[] array2;
    String[] item;
    String[] item2;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = (ViewGroup) inflater.inflate(R.layout.fragment1, container, false);

        //텍스트뷰 초기화
        StudentId = (TextView) v.findViewById(R.id.student_id);
        UserName = (TextView) v.findViewById(R.id.student_name);
        item_name = (TextView) v.findViewById(R.id.item_name);
        item_rental_date = (TextView) v.findViewById(R.id.item_rental_date);
        item_return_date = (TextView) v.findViewById(R.id.item_return_date);
        charger_number = (TextView) v.findViewById(R.id.charger_number);

        String url = "http://ikmin7373.dothome.co.kr/UserInfo.php";
        Map<String, String> parameters = new HashMap<String, String>();
        parameters.put("UserId", ((com.gachon.rental_system.LoginActivity) com.gachon.rental_system.LoginActivity.context_main).UserId);

        final JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(parameters),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {   //밑에서 Request가 보내지고 결과로 온 Reponse가 JsonResponse를 통해 다뤄진다.
                            JSONObject jsonResponse = response;
                            //result = [{"userID":"bae","userStudentID":""}]
                            result = jsonResponse.getString("result");
                            Log.d("result", result);

                            //=============사용자 이름, 학번 출력=============//
                            array = result.split(",");
                            a = array[0];
                            b = array[1];
                            a2 = a.substring(14, a.length() - 1);
                            b2 = b.substring(13, b.length() - 3);
                            StudentId.setText(b2);
                            UserName.setText(a2);

                            result2 = jsonResponse.getString("result2");
                            Log.d("result2", result2);

                            x_number = result2.substring(11,12);
                            real_number = Integer.parseInt(x_number);
                            real_number = 10 - real_number;
                            x_number = real_number.toString();
                            charger_number.setText(x_number);


                        } catch (Exception e)//예외처리
                        {
                            e.printStackTrace();//간단하게 예외처리함
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(((com.gachon.rental_system.LoginActivity) MainActivity.context_main), "DB 연동 에러", Toast.LENGTH_LONG).show();
                    }
                }) {

        };
        //실제로 로그인을 보낼 수 있는 Request : userID, password를 받아 리스너를 보냄
        RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
        queue.add(jsObjRequest);

        return v;
    }
}