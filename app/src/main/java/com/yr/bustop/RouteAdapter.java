package com.yr.bustop;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by YR on 2018/5/24.
 */

public class RouteAdapter extends BaseAdapter {

    ArrayList<Map<String,String>> routeData;
    private LayoutInflater inflater;

    RouteAdapter(Context context,ArrayList<Map<String,String>> routeData){
        this.routeData = routeData;
        inflater = LayoutInflater.from(context);
    }


    @Override
    public int getCount() {
        return routeData.size();
    }

    @Override
    public Object getItem(int i) {
        return routeData.get(i);
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
            view = inflater.inflate(R.layout.home_route_item, null);
        }

        // 找到TextView
        TextView RouteNum = view.findViewById(R.id.RouteNum);
        TextView RouteName = view.findViewById(R.id.RouteName);
        // 將文字內容設定給TextView
        RouteNum.setText(routeData.get(i).get("num"));
        RouteName.setText(routeData.get(i).get("name"));

        // 一定要將convertView回傳，供ListView呈現使用，並加入重用機制中
        return view;
    }
}
