/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.forall.modell;

import java.io.Serializable;

/**
 *
 * @author jd
 */
public class DataProxy implements Serializable {

    private Long id;

    private double data;

    private long timeStamp;

    public DataProxy() {

    }

    public DataProxy(double data) {
        this.data = data;
    }

    public Long getId() {
        return id;
    }

    public double getData() {
        return data;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setData(double data) {
        this.data = data;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }
}
