package com.caiji.android.reflecandinocation.bean;

/**
 * Created by lj on 2016/6/28.
 */
public class Paging {

    public String next_url;

    @Override
    public String toString() {
        return "Paging{" +
                "next_url='" + next_url + '\'' +
                '}';
    }

    public String getNext_url() {
        return next_url;
    }

    public void setNext_url(String next_url) {
        this.next_url = next_url;
    }
}
