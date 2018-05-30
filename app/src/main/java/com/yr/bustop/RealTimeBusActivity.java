package com.yr.bustop;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class RealTimeBusActivity extends AppCompatActivity {

    String ROUTE_NUM;

    TabLayout mTablayout;
    ViewPager mViewPager;
    List<RealTimeBusPage> pageList;

    //假資料
    String[] fakeName = new String[]{"AAAA","ETYMJN","YVGBHN","GHVUIJK","UILWEAF"};
    String[] fakeTime = new String[]{"20","18","13","5","1"};
    String[] fakeHand = new String[]{"0","0","0","1","0"};

    ArrayList<Stop> StopsData_Go;
    ArrayList<Stop> StopsData_Back;

    StopAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_real_time_bus);

        Bundle bundle = this.getIntent().getExtras();
        ROUTE_NUM = bundle.getString("RouteNum");

        //ActionBar 文字
        setTitle(ROUTE_NUM + " 路線");

        /*去程*/
        StopsData_Go = new ArrayList<Stop>();
        for (int i=0;i<5;i++){
            Stop stop = new Stop(fakeName[i],fakeTime[i],fakeHand[i]);
            StopsData_Go.add(stop);
        }
        Log.d("StopsData_Go", StopsData_Go.toString());


        /*返程*/
        StopsData_Back = new ArrayList<Stop>();
        for (int i=4;i>=0;i--){
            Stop stop = new Stop(fakeName[i],fakeTime[i],fakeHand[i]);
            StopsData_Back.add(stop);
        }
        Log.d("StopsData_Back", StopsData_Back.toString());

        initData();
        initView();

    }

    private void initData() {
        pageList = new ArrayList<RealTimeBusPage>();
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

    }
}
