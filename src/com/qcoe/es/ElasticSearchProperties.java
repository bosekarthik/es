/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.qcoe.es;

/**
 *
 * @author kbose
 */
public class ElasticSearchProperties {
    
    private String clusterName;
    private String[] hostIPs;
    private int portNo;

    public String getClusterName() {
        return clusterName;
    }

    public void setClusterName(String clusterName) {
        this.clusterName = clusterName;
    }

    public String[] getHostIPs() {
        return hostIPs;
    }

    public void setHostIPs(String[] hostIPs) {
        this.hostIPs = hostIPs;
    }

    public int getPortNo() {
        return portNo;
    }

    public void setPortNo(int portNo) {
        this.portNo = portNo;
    }
    
    
    
}
