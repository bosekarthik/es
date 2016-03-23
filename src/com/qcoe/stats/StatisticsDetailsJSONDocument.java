/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.qcoe.stats;

import java.util.ArrayList;

/**
 *
 * @author kbose
 */
public class StatisticsDetailsJSONDocument {
    
    private String serviceType;
    private String startTimeStamp;
    private String endTimeStamp;
    private String serviceName;
    private  ArrayList<StatisticsByServerHost> statisticsByServerHost;

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public String getStartTimeStamp() {
        return startTimeStamp;
    }

    public void setStartTimeStamp(String startTimeStamp) {
        this.startTimeStamp = startTimeStamp;
    }

    public String getEndTimeStamp() {
        return endTimeStamp;
    }

    public void setEndTimeStamp(String endTimeStamp) {
        this.endTimeStamp = endTimeStamp;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public ArrayList<StatisticsByServerHost> getStatisticsByServerHost() {
        return statisticsByServerHost;
    }

    public void setStatisticsByServerHost(ArrayList<StatisticsByServerHost> statisticsByServerHost) {
        this.statisticsByServerHost = statisticsByServerHost;
    }
    
    
    
    
}
