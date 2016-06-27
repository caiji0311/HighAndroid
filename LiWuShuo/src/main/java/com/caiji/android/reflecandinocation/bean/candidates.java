package com.caiji.android.reflecandinocation.bean;

/**
 * Created by lj on 2016/6/27.
 */
public class Candidates {
    public String editable;
    public String id;
    public String name;

    @Override
    public String toString() {
        return "Candidates{" +
                "editable='" + editable + '\'' +
                ", id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    public String getEditable() {
        return editable;
    }

    public void setEditable(String editable) {
        this.editable = editable;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}


