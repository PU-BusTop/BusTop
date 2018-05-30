package com.yr.bustop;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by YR on 2018/5/24.
 */

public class StopAdapter extends BaseAdapter {

    ArrayList<Stop> StopsData;
    private LayoutInflater inflater;

    StopAdapter(Context context, ArrayList<Stop> StopsData){
        this.StopsData = StopsData;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return StopsData.size();
    }

    @Override
    public Stop getItem(int i) {
        return StopsData.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        // 檢查convertView是否有值，有值表示是重複使用的
        if (view == null) {
            // 沒有值就要自己建立一個
            view = inflater.inflate(R.layout.real_time_item, null);
        }

        TextView StopName = view.findViewById(R.id.StopName);
        TextView StopTime = view.findViewById(R.id.StopTime);
        ImageView Hand = view.findViewById(R.id.Hand);
        // 將文字內容設定給TextView
        StopName.setText(StopsData.get(i).getName());
        StopTime.setText(StopsData.get(i).getTime() + " 分鐘");
        if(StopsData.get(i).getIfHand().equals("0")){
            Hand.setVisibility(View.INVISIBLE);
        }else if(StopsData.get(i).getIfHand().equals("1")){
            Hand.setVisibility(View.VISIBLE);
        }

        // 一定要將convertView回傳，供ListView呈現使用，並加入重用機制中
        return view;
    }
}


