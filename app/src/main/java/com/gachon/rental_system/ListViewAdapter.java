package com.gachon.rental_system;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ListViewAdapter extends BaseAdapter {

    private TextView itemName;
    private TextView itemRentalDate;
    private TextView itemReturnDate;

    //Adapter에 추가된 데이터를 저장하기 위한 ArrayList
    private ArrayList<com.gachon.rental_system.ListViewItem> listViewItemList = new ArrayList<com.gachon.rental_system.ListViewItem>();

    //ListViewAdapter의 생성자
    public ListViewAdapter() {

    }

    //Adapter에 사용되는 데이터의 개수 리턴
    @Override
    public int getCount() {
        return listViewItemList.size();
    }

    @Override
    public Object getItem(int position) {
        return listViewItemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    //position에 위치한 데이터를 화면에 출력하는데 사용될 View를 리턴

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        //fragment1_itemlist layout을 inflate하여 convertView 참조획득
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.fragment1_itemlist, parent, false);
        }

        //화면에 표시될 view(layout이 inflate된)로 부터 위젯에 대한 참조 획득
        itemName = (TextView) convertView.findViewById(R.id.item_name);
        itemRentalDate = (TextView) convertView.findViewById(R.id.item_rental_date);
        itemReturnDate = (TextView) convertView.findViewById(R.id.item_return_date);

        com.gachon.rental_system.ListViewItem listViewItem = listViewItemList.get(position);

        // 아이템 내 각 위젯에 데이터 반영
        itemName.setText(listViewItem.getName());
        itemRentalDate.setText(listViewItem.getRentalDate());
        itemReturnDate.setText(listViewItem.getReturnDate());

        return convertView;
    }

     public void addItem(String Name, String RentalDate, String ReturnDate){
        com.gachon.rental_system.ListViewItem item = new com.gachon.rental_system.ListViewItem();

        item.setName(Name);
        item.setRentalDate(RentalDate);
        item.setReturnDate(ReturnDate);

        listViewItemList.add(item);
     }
}
