package com.gachon.rental_system;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.text.SimpleDateFormat;
import java.util.Date;
//관리자 대여 승인 화면

public class Fragment3 extends Fragment {
    private boolean running;
    ViewGroup v;
    private TextView rentalT;
    private TextView returnT;
    Button button1;
    Button button2;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = (ViewGroup) inflater.inflate(R.layout.fragment3, container, false);

        rentalT = (TextView) v.findViewById(R.id.txt1);
        returnT = (TextView) v.findViewById(R.id.txt2);
        button1=(Button) v.findViewById(R.id.okB);
        button2=(Button) v.findViewById(R.id.noB);

        String[] names = {"소프트웨어학과", "201835477", "유은석","우산"};
        final ListView listView = (ListView) v.findViewById(R.id.listview);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1,names);
        listView.setAdapter(adapter);
/*
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String  itemValue = (String) listView.getItemAtPosition(position);
                Toast.makeText(getActivity(),itemValue, Toast.LENGTH_SHORT).show();
            }
        });
 */

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ////////////////////////////////////////////////////////////////////////////////////
                //서버에 대여 신청을 승인한다고 알려주는 부분
                ////////////////////////////////////////////////////////////////////////////////////
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ////////////////////////////////////////////////////////////////////////////////////
                //서버에 대여 신청을 반려한다고 알려주는 부분
                ////////////////////////////////////////////////////////////////////////////////////
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

                }catch(Exception ex){}
            }
        }
    }

}