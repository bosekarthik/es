/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.qcoe.main;

/**
 *
 * @author kbose
 */
public class CommandLineProperties {
    
    private String appName;
    private String sDate;
    private String eDate;
    private String tDirectory;

    public String gettDirectory() {
        return tDirectory;
    }

    public void settDirectory(String tDirectory) {
        this.tDirectory = tDirectory;
    }
    
      
    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getsDate() {
        return sDate;
    }

    public void setsDate(String sDate) {
        this.sDate = sDate;
    }

    public String geteDate() {
        return eDate;
    }

    public void seteDate(String eDate) {
        this.eDate = eDate;
    }
    
    
    
    
}
