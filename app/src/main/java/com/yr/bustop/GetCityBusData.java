package com.yr.bustop;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.SignatureException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import static com.yr.bustop.Global.EstimatedTimeOfArrival_URL;
import static com.yr.bustop.Global.RealTimeNearStop_URL;
import static com.yr.bustop.Global.TAG_Direction;
import static com.yr.bustop.Global.TAG_EstimateTime;
import static com.yr.bustop.Global.TAG_ORDER;
import static com.yr.bustop.Global.TAG_StopName;
import static com.yr.bustop.Global.TAG_zh_tw;

abstract class GetCityBusData {
    Context c;
    RequestQueue mQueue;
    StringRequest mRequest;
    ArrayList<Map<String, String>> estimatedTimeData0,estimatedTimeData1, RealTimeNearStopData0,RealTimeNearStopData1,finalData0,finalData1;
    private String[] route55 = new String[]{"豐原", "和平街", "媽祖廟", "臺灣企銀(豐原郵局)", "豐原電信局", "豐原高商", "豐原分局", "輸配電",
     "文化新村", "菸廠", "永豐螺絲", "校栗林", "中山祥和路口", "弘文中學", "矽品精密", "潭秀里", "潭子加工區", "潭子國小", "潭子火車站",
     "潭子區公所", "中山圓通南路口", "中山合作街口", "僑忠國小", "瓦窯", "頭家厝", "中山中興路口", "中山路一巷口", "舊社公園(北屯路)", "北新國中",
     "北屯崇德二路口", "特力屋北屯店", "北屯文心路口", "大坑口", "三光國中", "北屯", "北屯國小(北屯路)", "監理站", "寶覺寺", "新民高中(三民路)", "三民錦中街口",
     "一心市場", "中友百貨", "國立臺中科技大學", "中興堂", "臺中公園(雙十路)", "干城站", "臺中火車站", "民權繼光街口", "臺中女中", "地方法院",
      "臺中刑務所演武場", "光明國中(貴和街)"};

    private String[] route55_1 = new String[]{"豐原", "媽祖廟", "臺灣企銀(豐原郵局)", "豐原電信局", "豐原高商", "豐原分局", "輸配電",
            "文化新村", "菸廠", "永豐螺絲", "校栗林", "中山祥和路口", "弘文中學", "矽品精密", "潭秀里", "潭子加工區", "潭子國小", "潭子火車站",
            "潭子區公所", "中山圓通南路口", "中山合作街口", "僑忠國小", "瓦窯", "頭家厝", "中山中興路口", "中山路一巷口", "舊社公園(北屯路)", "北新國中",
            "北屯崇德二路口", "特力屋北屯店", "北屯文心路口", "大坑口", "三光國中", "北屯", "北屯國小(北屯路)", "監理站", "寶覺寺", "新民高中(三民路)", "三民錦中街口",
            "一心市場", "中友百貨", "國立臺中科技大學", "中興堂", "臺中公園(雙十路)", "干城站", "臺中火車站", "民權繼光街口", "臺中女中", "地方法院",
            "光明國中(貴和街)"};
    private final String APPID = "3c592686f21841ee9b71206041bbcee3";
    private final String APPKey = "QCdK2gOWiSZEyfrr7xN655y9f6A";
    String Signature,sAuth,xdate;
    GetCityBusData(Context context){
        c = context;
        initial();
        getEstimatedTimeOfArrival();
//        getRealTimeNearStop();

    }

    abstract void handleResult(ArrayList<Map<String, String>> dataArray0, ArrayList<Map<String, String>> dataArray1);

    private void initial(){
        mQueue = Volley.newRequestQueue(c);
        xdate = getServerTime();
        System.out.println(xdate);
        String SignDate = "x-date: " + xdate;
        Signature="";
        try {
            //取得加密簽章
            Signature = HMAC_SHA1.Signature(SignDate, APPKey);
        } catch (SignatureException e1) {
            e1.printStackTrace();
        }

        System.out.println("Signature :" + Signature);
        sAuth = "hmac username=\"" + APPID + "\", algorithm=\"hmac-sha1\", headers=\"x-date\", signature=\"" + Signature + "\"";
    }

    private void getEstimatedTimeOfArrival(){
        estimatedTimeData0 = new ArrayList<>();
        estimatedTimeData1 = new ArrayList<>();
        mRequest = new StringRequest(Request.Method.GET, EstimatedTimeOfArrival_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("Response",response);
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for(int i=0; i<jsonArray.length();i++){
                        JSONObject object = jsonArray.getJSONObject(i);
                        HashMap<String, String> map = new HashMap<>();
                        int direction = object.getInt(TAG_Direction);
                        String stopname = object.getJSONObject(TAG_StopName).getString(TAG_zh_tw);
                        map.put(TAG_StopName, stopname);
                        map.put(TAG_EstimateTime, object.getInt(TAG_EstimateTime)+"");
                        map.put(TAG_ORDER, java.util.Arrays.asList(route55).indexOf(stopname)+"");
                        if(direction==0)
                            estimatedTimeData0.add(map);
                        else if(direction==1)
                            estimatedTimeData1.add(map);
                    }
                    sortData();
                } catch (JSONException e) {
                    Log.e("JSON Error", e.getMessage());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                try {
                    Log.d("JSON", new String(error.networkResponse.data,"UTF-8"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if( error instanceof NetworkError) {
                    Toast.makeText(c,"網路發生問題",Toast.LENGTH_LONG).show();
                } else if( error instanceof ServerError) {
                    Toast.makeText(c,"主機發生問題",Toast.LENGTH_LONG).show();
                }else if( error instanceof NoConnectionError) {
                    Toast.makeText(c,"無法連線至主機",Toast.LENGTH_LONG).show();
                } else if( error instanceof TimeoutError) {
                    Toast.makeText(c,"連線至主機時逾時",Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(c,"連線至主機時候發生問題",Toast.LENGTH_LONG).show();
                }
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> map = new HashMap<>();
                map.put("Authorization",sAuth);
                map.put("x-date",xdate);
                return map;
            }
        };
        mQueue.add(mRequest);
    }

    private void getRealTimeNearStop(){
        RealTimeNearStopData0 = new ArrayList<>();
        RealTimeNearStopData1 = new ArrayList<>();
        mRequest = new StringRequest(Request.Method.GET, RealTimeNearStop_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //TODO
                Log.d("Response",response);
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for(int i=0; i<jsonArray.length();i++){
                        JSONObject object = jsonArray.getJSONObject(i);
                        HashMap<String, String> map = new HashMap<>();
                        int direction = object.getInt(TAG_Direction);
                        String stopname = object.getJSONObject(TAG_StopName).getString(TAG_zh_tw);
                        map.put(TAG_StopName,stopname);
                        map.put(TAG_ORDER, java.util.Arrays.asList(route55).indexOf(stopname)+"");
                        if(direction==0)
                            RealTimeNearStopData0.add(map);
                        else if(direction==1)
                            RealTimeNearStopData1.add(map);
                    }
                } catch (JSONException e) {
                    Log.e("JSON Error", e.getMessage());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                try {
                    Log.d("JSON", new String(error.networkResponse.data,"UTF-8"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if( error instanceof NetworkError) {
                    Toast.makeText(c,"網路發生問題",Toast.LENGTH_LONG).show();
                } else if( error instanceof ServerError) {
                    Toast.makeText(c,"主機發生問題",Toast.LENGTH_LONG).show();
                }else if( error instanceof NoConnectionError) {
                    Toast.makeText(c,"無法連線至主機",Toast.LENGTH_LONG).show();
                } else if( error instanceof TimeoutError) {
                    Toast.makeText(c,"連線至主機時逾時",Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(c,"連線至主機時候發生問題",Toast.LENGTH_LONG).show();
                }
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> map = new HashMap<>();
                map.put("Authorization",sAuth);
                map.put("x-date",xdate);
                return map;
            }
        };
        mQueue.add(mRequest);
    }

    public static String getServerTime() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "EEE, dd MMM yyyy HH:mm:ss z", Locale.US);
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        return dateFormat.format(calendar.getTime());
    }

    private void sortData(){
        Comparator<Map<String, String>> mapComparator = new Comparator<Map<String, String>>() {
            public int compare(Map<String, String> m1, Map<String, String> m2) {
                if(m1.get(TAG_ORDER).compareTo(m2.get(TAG_ORDER))!=0)
                    return m1.get(TAG_ORDER).compareTo(m2.get(TAG_ORDER));
                else
                    return m1.get(TAG_EstimateTime).compareTo(m2.get(TAG_EstimateTime));
            }
        };

        //刪除重複站牌到站時間
        Collections.sort(estimatedTimeData1,mapComparator);
        Collections.sort(estimatedTimeData0,mapComparator);
        for(int i=0;i<estimatedTimeData0.size()-1;i++){
            if(estimatedTimeData0.get(i).get(TAG_ORDER).equals(estimatedTimeData0.get(i+1).get(TAG_ORDER)))
                estimatedTimeData0.remove(i+1);
        }
        for(int i=0;i<estimatedTimeData1.size()-1;i++){
            if(estimatedTimeData1.get(i).get(TAG_ORDER).equals(estimatedTimeData1.get(i+1).get(TAG_ORDER))){
                estimatedTimeData1.remove(i+1);
                i--;
            }
        }

        //加入尚未發車
        if(!estimatedTimeData0.get(0).get(TAG_ORDER).equals(0)) {
            int num = Integer.parseInt(estimatedTimeData0.get(0).get(TAG_ORDER));
            for(int i=0;i<num;i++){
                HashMap<String, String> map = new HashMap<>();
                map.put(TAG_StopName, route55[i]);
                map.put(TAG_EstimateTime, "尚未發車");
                map.put(TAG_ORDER, i+"");
                estimatedTimeData0.add(i,map);
            }
        }


        if(!estimatedTimeData1.get(0).get(TAG_ORDER).equals(0)){
            int num = Integer.parseInt(estimatedTimeData1.get(0).get(TAG_ORDER));
            for(int i=0;i<num;i++) {
                HashMap<String, String> map = new HashMap<>();
                map.put(TAG_StopName, route55_1[i]);
                map.put(TAG_EstimateTime, "尚未發車");
                map.put(TAG_ORDER, i + "");
                estimatedTimeData1.add(i, map);
            }
        }

        Log.d("For Debug", estimatedTimeData0.toString());
        Log.d("For Debug", estimatedTimeData1.toString());
        finalData0 = estimatedTimeData0;
        finalData1 = estimatedTimeData1;
        handleResult(finalData0,finalData1);
    }
}
