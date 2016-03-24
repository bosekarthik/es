/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.qcoe.main;

import com.qcoe.config.Configuration;
import com.qcoe.jdbc.ConnectionPool;
import com.qcoe.jdbc.JDBCProperties;
import com.qcoe.stats.StatisticsDetails;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.qcoe.config.Constants;
import com.qcoe.tem.IncidentProcessor;
import com.qcoe.tem.IncidentProperties;
import java.io.File;
/**
 *
 * @author kbose
 */
public class ES {

    
  
    
    public void init() throws SQLException, ClassNotFoundException{
    
        boolean workOnline = Configuration.WORK_ONLINE;
       
        if(workOnline){
            JDBCProperties jdbcProp= Configuration.jdbcProperties;
            cp= ConnectionPool.getInstance(jdbcProp);
        }
    
        //IncidentProperties inc = Configuration.incProperties;
    }
    
     public void end() throws SQLException{
        if(cp!=null){
            cp.closeALL();
        }
    }
    
    public void doWork(CommandLineProperties cLine) throws SQLException, InterruptedException, IOException{
        
        String app = cLine.getAppName();
        
        if(app.equalsIgnoreCase(Constants.APPNAME_STERLING)){
            String sDate = cLine.getsDate();
            String eDate = cLine.geteDate();
            statististicsDetails(sDate,eDate);
        }else if(app.equalsIgnoreCase(Constants.APPNAME_TEM)){
            String tDirectory = cLine.gettDirectory();
          
            incidentDetails(tDirectory);
            
        }
    
    }
    
    
    private void incidentDetails(String tDirectory) throws IOException{
    
        File f = new File(tDirectory);
        File[] fList = f.listFiles();
    
        IncidentProcessor inc = new IncidentProcessor(fList);
        inc.doWork();
    
    }
    
    private void statististicsDetails(String sDate,String eDate) throws SQLException, InterruptedException, IOException{
    
        StatisticsDetails sd = new StatisticsDetails();
        sd.getGIVStatisticsDetails(sDate, eDate);
        
        
    }
    
    
    
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        ES es = new ES();
        
        CommandLineProperties cLine = new CommandLineProperties();
        System.out.println("ES Version 1.0.1");
        cLine.setAppName(args[0]);
        // cLine.setAppName("TEM");
              
        try {
        
            String app = cLine.getAppName();
            
            
            
            if(app.equalsIgnoreCase(Constants.APPNAME_STERLING)){
                cLine.setsDate(args[1]);
                cLine.seteDate(args[2]);
              //  cLine.setsDate("2016031713");
              //  cLine.seteDate("2016031719");
                es.init();
                
            }else if(app.equalsIgnoreCase(Constants.APPNAME_TEM)){
                cLine.settDirectory(args[1]);
               //  cLine.settDirectory("D:/Perf");
            }
            
            es.doWork(cLine);
            
        } catch (SQLException | ClassNotFoundException | InterruptedException | IOException ex) {
            Logger.getLogger(Configuration.LOGFILE).log(Level.SEVERE, ex.getMessage(), ex);
        }finally{
            try {
                es.end();
            } catch (SQLException ex) {
                Logger.getLogger(Configuration.LOGFILE).log(Level.SEVERE, ex.getMessage(), ex);
            }
        
        }
        
     
    }
    
    
    private ConnectionPool cp;
}
