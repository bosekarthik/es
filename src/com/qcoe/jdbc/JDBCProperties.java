/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.qcoe.jdbc;

/**
 *
 * @author kbose
 */
public class JDBCProperties {
    
    private String omsURL;
    private String omsUserName;
    private String omsPassWord;
    private int omsPoolSize;
    
    private String givURL;
    private String givUserName;
    private String givPassWord;
    private int givPoolSize;

    public String getOmsURL() {
        return omsURL;
    }

    public void setOmsURL(String omsURL) {
        this.omsURL = omsURL;
    }

    public String getOmsUserName() {
        return omsUserName;
    }

    public void setOmsUserName(String omsUserName) {
        this.omsUserName = omsUserName;
    }

    public String getOmsPassWord() {
        return omsPassWord;
    }

    public void setOmsPassWord(String omsPassWord) {
        this.omsPassWord = omsPassWord;
    }

    public int getOmsPoolSize() {
        return omsPoolSize;
    }

    public void setOmsPoolSize(int omsPoolSize) {
        this.omsPoolSize = omsPoolSize;
    }

    public String getGivURL() {
        return givURL;
    }

    public void setGivURL(String givURL) {
        this.givURL = givURL;
    }

    public String getGivUserName() {
        return givUserName;
    }

    public void setGivUserName(String givUserName) {
        this.givUserName = givUserName;
    }

    public String getGivPassWord() {
        return givPassWord;
    }

    public void setGivPassWord(String givPassWord) {
        this.givPassWord = givPassWord;
    }

    public int getGivPoolSize() {
        return givPoolSize;
    }

    public void setGivPoolSize(int givPoolSize) {
        this.givPoolSize = givPoolSize;
    }
    
    
  
    
    
}
