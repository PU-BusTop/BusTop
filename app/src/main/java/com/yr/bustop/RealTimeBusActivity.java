package com.yr.bustop;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

import java.io.UnsupportedEncodingException;
import java.security.SignatureException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import static com.yr.bustop.Global.EstimatedTimeOfArrival_URL;
import static com.yr.bustop.Global.TAG_Direction;
import static com.yr.bustop.Global.TAG_EstimateTime;
import static com.yr.bustop.Global.TAG_RouteID;
import static com.yr.bustop.Global.TAG_StopName;

public class RealTimeBusActivity extends AppCompatActivity {
    RequestQueue mQueue;
    StringRequest mRequest;
    ArrayList<Map<String, String>> dataArray;
    private final String APPID = "3c592686f21841ee9b71206041bbcee3";
    private final String APPKey = "QCdK2gOWiSZEyfrr7xN655y9f6A";
    String Signature,sAuth,x_date;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_real_time_bus);
        mQueue = Volley.newRequestQueue(this);
        getEstimatedTimeOfArrival();
        //TODO 更新畫面
    }
    private void getEstimatedTimeOfArrival(){
        x_date = getServerTime();
        Signature="";
        try {
            //取得加密簽章
            Signature = HMAC_SHA1.Signature("x-date: "+x_date, APPID);
        } catch (SignatureException e1) {
            e1.printStackTrace();
        }

        System.out.println("Signature :" + Signature);
        sAuth = "hmac username=\"" + APPID + "\", algorithm=\"hmac-sha1\", headers=\"x-date\", signature=\"" + Signature + "\"";
        dataArray = new ArrayList<>();
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
                        if(route.equals("55")){
                            map.put(TAG_RouteID,route);
                            map.put(TAG_StopName, object.getString(TAG_StopName));
                            map.put(TAG_Direction, object.getInt(TAG_Direction)+"");
                            map.put(TAG_EstimateTime, object.getInt(TAG_EstimateTime)+"");
                        }
                        dataArray.add(map);
                    }
                    Log.d("JSON",dataArray.toString());
                } catch (JSONException e) {
                    Log.e("JSON Error", e.getMessage());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                try {
                    Log.d("JSON", new String(error.networkResponse.data,"UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                if( error instanceof NetworkError) {
                    Toast.makeText(RealTimeBusActivity.this,"網路發生問題",Toast.LENGTH_LONG).show();
                } else if( error instanceof ServerError) {
                    Toast.makeText(RealTimeBusActivity.this,"主機發生問題",Toast.LENGTH_LONG).show();
                }else if( error instanceof NoConnectionError) {
                    Toast.makeText(RealTimeBusActivity.this,"無法連線至主機",Toast.LENGTH_LONG).show();
                } else if( error instanceof TimeoutError) {
                    Toast.makeText(RealTimeBusActivity.this,"連線至主機時逾時",Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(RealTimeBusActivity.this,"連線至主機時候發生問題",Toast.LENGTH_LONG).show();
                }
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> map = new HashMap<>();
                map.put("Authorization",sAuth);
                map.put("x-date",x_date);
                map.put("Accept-Encoding", "gzip");
                Log.d("JSON",sAuth);
                Log.d("x-date",x_date);
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
