package com.yin.myapplication;

public class MyRecyclerItem {
    private String reward;
    private String date;
    private String vol;

    public MyRecyclerItem(String reward, String date, String vol) {
        this.reward = reward;
        this.date = date;
        this.vol = vol;
    }

    public String getReward() {
        return reward;
    }

    public String getDate() {
        return date;
    }

    public String getVol() {
        return vol;
    }
}
