package com.caiji.android.reflecandinocation.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lj on 2016/6/27.
 */
public class Data {

    public ArrayList<Channels> channels;
    public ArrayList<Candidates> candidates;
    public ArrayList<Items> items;
    public Paging paging;

    @Override
    public String toString() {
        return "Data{" +
                "channels=" + channels +
                ", candidates=" + candidates +
                ", items=" + items +
                ", paging=" + paging +
                '}';
    }

    public ArrayList<Channels> getChannels() {
        return channels;
    }

    public void setChannels(ArrayList<Channels> channels) {
        this.channels = channels;
    }

    public ArrayList<Candidates> getCandidates() {
        return candidates;
    }

    public void setCandidates(ArrayList<Candidates> candidates) {
        this.candidates = candidates;
    }

    public ArrayList<Items> getItems() {
        return items;
    }

    public void setItems(ArrayList<Items> items) {
        this.items = items;
    }

    public Paging getPaging() {
        return paging;
    }

    public void setPaging(Paging paging) {
        this.paging = paging;
    }
}
