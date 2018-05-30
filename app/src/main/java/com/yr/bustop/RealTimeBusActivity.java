package com.yr.bustop;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.List;

import java.util.Map;


public class RealTimeBusActivity extends AppCompatActivity {

    String ROUTE_NUM;

    TabLayout mTablayout;
    ViewPager mViewPager;
    List<RealTimeBusPage> pageList;

    //假資料
    String[] fakeName = new String[]{"AAAA","ETYMJN","YVGBHN","GHVUIJK","UILWEAF"};
    String[] fakeTime = new String[]{"20","18","13","5","1"};
    String[] fakeHand = new String[]{"0","0","0","1","0"};

    ArrayList<Map<String,String>> StopsData_Go;
    ArrayList<Map<String,String>> StopsData_Back;
//
//    StopAdapter adapter;
//
//    RequestQueue mQueue;
//    StringRequest mRequest;
//    ArrayList<Map<String, String>> dataArray;
//    private final String APPID = "3c592686f21841ee9b71206041bbcee3";
//    private final String APPKey = "QCdK2gOWiSZEyfrr7xN655y9f6A";
//    String Signature,sAuth,xdate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_real_time_bus);

        Bundle bundle = this.getIntent().getExtras();
        ROUTE_NUM = bundle.getString("RouteNum");
//        mQueue = Volley.newRequestQueue(this);
//        getEstimatedTimeOfArrival();
        new GetCityBusData(this){
            @Override
            void handleResult(ArrayList<Map<String, String>> dataArray0,ArrayList<Map<String, String>> dataArray1) {
                //TODO
            }
        };
        //ActionBar 文字
        setTitle(ROUTE_NUM + " 路線");

        /*去程*/
        StopsData_Go = new ArrayList<Map<String, String>>();
        for (int i=0;i<5;i++){
            Map map =new HashMap();
            map.put("name",fakeName[i]);
            map.put("time",fakeTime[i]);
            map.put("hand",fakeHand[i]);

            StopsData_Go.add(map);
        }
        Log.d("StopsData_Go", StopsData_Go.toString());


        /*返程*/
        StopsData_Back = new ArrayList<Map<String, String>>();
        for (int i=4;i>=0;i--){
            Map map =new HashMap();
            map.put("name",fakeName[i]);
            map.put("time",fakeTime[i]);
            map.put("hand",fakeHand[i]);

            StopsData_Back.add(map);
        }
        Log.d("StopsData_Back", StopsData_Back.toString());

        initData();
        initView();

    }

    private void initData() {
        pageList = new ArrayList<>();
        pageList.add(new RealTimeBusPage(RealTimeBusActivity.this,StopsData_Go));
        pageList.add(new RealTimeBusPage(RealTimeBusActivity.this,StopsData_Back));
    }

    private void initView() {
        mTablayout = findViewById(R.id.tabs);
        mTablayout.addTab(mTablayout.newTab().setText("去程"));
        mTablayout.addTab(mTablayout.newTab().setText("返程"));

        mViewPager = findViewById(R.id.pager);
        mViewPager.setAdapter(new SamplePagerAdapter());
        initListener();
    }

    private void initListener() {
        mTablayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTablayout));
    }

    private class SamplePagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return pageList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object o) {
            return o == view;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(pageList.get(position));
            return pageList.get(position);
        }
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }


        //TODO 更新畫面
    }
//    private void getEstimatedTimeOfArrival(){
//        xdate = getServerTime();
//        System.out.println(xdate);
//        String SignDate = "x-date: " + xdate;
//        Signature="";
//        try {
//            //取得加密簽章
//            Signature = HMAC_SHA1.Signature(SignDate, APPKey);
//        } catch (SignatureException e1) {
//            e1.printStackTrace();
//        }
//
//        System.out.println("Signature :" + Signature);
//        sAuth = "hmac username=\"" + APPID + "\", algorithm=\"hmac-sha1\", headers=\"x-date\", signature=\"" + Signature + "\"";
//        dataArray = new ArrayList<>();
//        mRequest = new StringRequest(Request.Method.GET, EstimatedTimeOfArrival_URL, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                Log.d("Response",response);
//                try {
//                    JSONArray jsonArray = new JSONArray(response);
//                    for(int i=0; i<jsonArray.length();i++){
//                        JSONObject object = jsonArray.getJSONObject(i);
//                        HashMap<String, String> map = new HashMap<>();
//                        String route = object.getString(TAG_RouteID);
//                        if(route.equals("55")){
//                            map.put(TAG_StopName, object.getString(TAG_StopName));
//                            map.put(TAG_Direction, object.getInt(TAG_Direction)+"");
//                            map.put(TAG_EstimateTime, object.getInt(TAG_EstimateTime)+"");
//                        }
//                        dataArray.add(map);
//                    }
//                    Log.d("JSON",dataArray.toString());
//                } catch (JSONException e) {
//                    Log.e("JSON Error", e.getMessage());
//                }
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                try {
//                    Log.d("JSON", new String(error.networkResponse.data,"UTF-8"));
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//                if( error instanceof NetworkError) {
//                    Toast.makeText(RealTimeBusActivity.this,"網路發生問題",Toast.LENGTH_LONG).show();
//                } else if( error instanceof ServerError) {
//                    Toast.makeText(RealTimeBusActivity.this,"主機發生問題",Toast.LENGTH_LONG).show();
//                }else if( error instanceof NoConnectionError) {
//                    Toast.makeText(RealTimeBusActivity.this,"無法連線至主機",Toast.LENGTH_LONG).show();
//                } else if( error instanceof TimeoutError) {
//                    Toast.makeText(RealTimeBusActivity.this,"連線至主機時逾時",Toast.LENGTH_LONG).show();
//                }else {
//                    Toast.makeText(RealTimeBusActivity.this,"連線至主機時候發生問題",Toast.LENGTH_LONG).show();
//                }
//            }
//        }){
//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                HashMap<String, String> map = new HashMap<>();
//                map.put("Authorization",sAuth);
//                map.put("x-date",xdate);
//                Log.d("JSON",sAuth);
//                Log.d("x-date",xdate);
//                return map;
//            }
//        };
//
//        mQueue.add(mRequest);
//
//    }
//
//    public static String getServerTime() {
//        Calendar calendar = Calendar.getInstance();
//        SimpleDateFormat dateFormat = new SimpleDateFormat(
//                "EEE, dd MMM yyyy HH:mm:ss z", Locale.US);
//        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
//        return dateFormat.format(calendar.getTime());
//    }
}
