package com.yr.bustop;

/**
 * Created by YR on 2018/5/29.
 */

//View Holder
public class Stop {

    String name;
    String time;
    String ifHand; //是否舉手

    Stop(String name, String time,String ifHand){
        this.name = name;
        this.time = time;
        this.ifHand = ifHand;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getIfHand() {
        return ifHand;
    }

    public void setIfHand(String ifHand) {
        this.ifHand = ifHand;
    }
}
