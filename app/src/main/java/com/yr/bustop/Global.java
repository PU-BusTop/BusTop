package com.yr.bustop;

public class Global {
    static String RealTimeNearStop_URL = "http://ptx.transportdata.tw/MOTC/v2/Bus/RealTimeNearStop/City/Taichung/55?$filter=RouteID%20eq%20'55'&$format=JSON";
    static String EstimatedTimeOfArrival_URL = "http://ptx.transportdata.tw/MOTC/v2/Bus/EstimatedTimeOfArrival/City/Taichung/55?$filter=EstimateTime%20ge%204%20and%20RouteID%20eq%20'55'&$format=JSON";
    static String TAG_zh_tw = "Zh_tw";
    static String TAG_StopName = "StopName";
    static String TAG_ORDER = "Number";
    static String TAG_Direction = "Direction";
    static String TAG_EstimateTime = "EstimateTime";
}
