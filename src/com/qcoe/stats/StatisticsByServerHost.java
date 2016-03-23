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
public class StatisticsByServerHost {
    
    private String serverName;
    private String hostName;
    private ArrayList<Statistics> statistics;

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public ArrayList<Statistics> getStatistics() {
        return statistics;
    }

    public void setStatistics(ArrayList<Statistics> statistics) {
        this.statistics = statistics;
    }
    
    
    
    
}
