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
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import static com.yr.bustop.Global.EstimatedTimeOfArrival_URL;
import static com.yr.bustop.Global.RealTimeNearStop_URL;
import static com.yr.bustop.Global.TAG_Direction;
import static com.yr.bustop.Global.TAG_EstimateTime;
import static com.yr.bustop.Global.TAG_RouteID;
import static com.yr.bustop.Global.TAG_StopName;

abstract class GetCityBusData {
    Context c;
    RequestQueue mQueue;
    StringRequest mRequest;
    ArrayList<Map<String, String>> estimatedTimeData, RealTimeNearStopData;
    private final String APPID = "3c592686f21841ee9b71206041bbcee3";
    private final String APPKey = "QCdK2gOWiSZEyfrr7xN655y9f6A";
    String Signature,sAuth,xdate;
    GetCityBusData(Context context){
        c = context;
        initial();
        getEstimatedTimeOfArrival();
        getRealTimeNearStop();
    }

    abstract void handleResult(ArrayList<Map<String, String>> dataArray);

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
        estimatedTimeData = new ArrayList<>();
        mRequest = new StringRequest(Request.Method.GET, EstimatedTimeOfArrival_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("Response",response);
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for(int i=0; i<jsonArray.length();i++){
                        JSONObject object = jsonArray.getJSONObject(i);
                        HashMap<String, String> map = new HashMap<>();
                        String route = object.getString(TAG_RouteID);
                        map.put(TAG_StopName, object.getString(TAG_StopName));
                        map.put(TAG_Direction, object.getInt(TAG_Direction)+"");
                        map.put(TAG_EstimateTime, object.getInt(TAG_EstimateTime)+"");
                        estimatedTimeData.add(map);
                    }
                    Log.d("JSON",estimatedTimeData.toString());
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
        RealTimeNearStopData = new ArrayList<>();
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
                        String route = object.getString(TAG_RouteID);
                        map.put(TAG_StopName, object.getString(TAG_StopName));
                        map.put(TAG_Direction, object.getInt(TAG_Direction)+"");
                        map.put(TAG_EstimateTime, object.getInt(TAG_EstimateTime)+"");
                        RealTimeNearStopData.add(map);
                    }
                    Log.d("JSON",RealTimeNearStopData.toString());
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
}
