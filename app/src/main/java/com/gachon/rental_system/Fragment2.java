package com.gachon.rental_system;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class Fragment2 extends Fragment {
    ViewGroup v;
    String total;
    String a;
    String b;
    String c;
    String d;
    String a2;
    String b2;
    String c2;
    String d2;

    public TextView remain_mouse;
    public TextView remain_notebook;
    public TextView remain_umbrella;
    public TextView remain_phone;
        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            v = (ViewGroup) inflater.inflate(R.layout.fragment2, container, false);

            //텍스트뷰 초기화
            remain_mouse = (TextView) v.findViewById(R.id.number_remain_1);
            remain_notebook = (TextView) v.findViewById(R.id.number_remain_2);
            remain_umbrella = (TextView) v.findViewById(R.id.number_remain_3);
            remain_phone = (TextView) v.findViewById(R.id.number_remain_4);
            String url = "http://ikmin7373.dothome.co.kr/ItemRest.php";
            Map<String, String> parameters = new HashMap<String, String>();

            final JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(parameters),
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {   //밑에서 Request가 보내지고 결과로 온 Reponse가 JsonResponse를 통해 다뤄진다.
                                JSONObject jsonResponse = response;
                                //result = [{"list1":"50"},{"list2":"50"},{"list3":"50"},{"list4":"50"}]
                                total = jsonResponse.getString("result");
                                String[] array = total.split(",");
                                a = array[0];
                                b = array[1];
                                c = array[2];
                                d = array[3];
                                a2 = a.substring(11,a.length()-2);
                                b2 = b.substring(10,b.length()-2);
                                c2 = c.substring(10,c.length()-2);
                                d2 = d.substring(10,d.length()-3);
                                remain_mouse.setText(a2);
                                remain_notebook.setText(b2);
                                remain_umbrella.setText(c2);
                                remain_phone.setText(d2);

                            } catch (Exception e)//예외처리
                            {
                                e.printStackTrace();//간단하게 예외처리함
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(((LoginActivity) MainActivity.context_main), "DB 연동 에러", Toast.LENGTH_LONG).show();
                        }
                    }) {

            };
            //실제로 로그인을 보낼 수 있는 Request : userID, password를 받아 리스너를 보냄
            RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
            queue.add(jsObjRequest);

            return v;
        }
}