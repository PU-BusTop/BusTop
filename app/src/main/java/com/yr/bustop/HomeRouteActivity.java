package com.yr.bustop;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class HomeRouteActivity extends AppCompatActivity {

    ListView RouteListview;

    //假資料
    String[] fakeNum = new String[]{"6","12","14","48","55"};
    String[] fakeName = new String[]{"干城站 - 忠義里","明德高中(明德街) - 豐原鎮清宮","干城站 - 舊庄","台中火車站 - 嶺東科技大學","地方法院 - 豐原"};

    ArrayList<Map<String,String>> routeData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_route);

        RouteListview = findViewById(R.id.RouteListview);

        routeData = new ArrayList<Map<String,String>>();
        for(int i=0;i<5;i++){
            Map map = new HashMap();
            map.put("num",fakeNum[i]);
            map.put("name",fakeName[i]);

            routeData.add(map);
        }
        Log.d("routeData",routeData.toString());

        RouteAdapter adapter = new RouteAdapter(HomeRouteActivity.this,routeData);
        RouteListview.setAdapter(adapter);

        RouteListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //TODO: Intent
                Map route = routeData.get(i);
                //Toast.makeText(HomeRouteActivity.this,route.get("num")+" "+route.get("name"),Toast.LENGTH_SHORT).show();

                if(i==4){ //只有55號公車才換頁
                    Intent intent = new Intent(HomeRouteActivity.this,RealTimeBusActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("RouteNum",route.get("num").toString());
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            }
        });

    }
}
