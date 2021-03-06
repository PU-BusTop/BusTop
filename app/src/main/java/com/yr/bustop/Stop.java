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
        try {
            return (Integer.parseInt(time)/60)+"分鐘";
        }catch (Exception e){
            return time;
        }
    }

    public void setTime(String time) {
        this.time = time;
    }

    public boolean getIfHand() {
        if(ifHand!=null && ifHand.equals("0"))
            return false;
        else if(ifHand!=null && ifHand.equals("1"))
            return true;
        else
            return false;
    }

    public void setIfHand(String ifHand) {
        this.ifHand = ifHand;
    }
}
