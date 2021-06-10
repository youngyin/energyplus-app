package com.yin.myapplication;

public class MyRecyclerItem {
    private Integer reward;
    private String date;
    private Integer vol;

    public MyRecyclerItem(Integer reward, String date, Integer vol) {
        this.reward = reward;
        this.date = date;
        this.vol = vol;
    }

    public String getReward() {
        return "reward: " + reward;
    }

    public String getDate() {
        return date;
    }

    public String getVol() {
        return vol + "mL";
    }
}
