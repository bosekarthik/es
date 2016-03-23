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
public class QueryProperties {
    
    public static final int OMS=1;
    public static final int GIV=2;
   
    private int application;
    private String query;
    private String outputFile;

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public void setApplication(int app){
        this.application =app;
    }
    
   public boolean isOMS(){
   
        return application ==1;
   }
   

    public String getOutputFile() {
        return outputFile;
    }

    public void setOutputFile(String outputFile) {
        this.outputFile = outputFile;
    }
    
    
    
    
    
}
