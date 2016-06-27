package com.caiji.android.reflecandinocation.bean;

import java.util.List;

/**
 * Created by lj on 2016/6/27.
 */
public class Data {

    public List<Channels> channels;
    public List<Candidates> candidates;

    @Override
    public String toString() {
        return "Data{" +
                "channelses=" + channels +
                ", Candidates=" + candidates +
                '}';
    }

    public List<Channels> getchannels() {
        return channels;
    }

    public void setChannelses(List<Channels> channels) {
        this.channels = channels;
    }

    public List<Candidates> getCandidates() {
        return candidates;
    }

    public void setCandidates(List<Candidates> candidates) {
        this.candidates = candidates;
    }
}
