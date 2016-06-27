package com.caiji.android.reflecandinocation.bean;

/**
 * Created by lj on 2016/6/27.
 */
public class ChoiceTitle {
    public String code;
    public Data data;

    @Override
    public String toString() {
        return "ChoiceTitle{" +
                "code='" + code + '\'' +
                ", data=" + data +
                '}';
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }
}
