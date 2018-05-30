package com.yr.bustop;

public class Global {
    static final String RealTimeNearStop_URL = "http://ptx.transportdata.tw/MOTC/v2/Bus/RealTimeNearStop/City/Taichung/55?$filter=RouteID%20eq%20'55'&$format=JSON";
    static final String EstimatedTimeOfArrival_URL = "http://ptx.transportdata.tw/MOTC/v2/Bus/EstimatedTimeOfArrival/City/Taichung/55?$filter=EstimateTime%20ge%204%20and%20RouteID%20eq%20'55'&$format=JSON";
    static final String BusTop_URL = "http://140.128.10.150:8080/busTop/stopStatusApp.php";
    static final String TAG_zh_tw = "Zh_tw";
    static final String TAG_StopName = "StopName";
    static final String TAG_ORDER = "Number";
    static final String TAG_Direction = "Direction";
    static final String TAG_EstimateTime = "EstimateTime";
    static final String TAG_STATUS = "status";
}
