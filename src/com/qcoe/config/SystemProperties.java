/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.qcoe.config;

import com.qcoe.es.ElasticSearchProperties;
import com.qcoe.tem.IncidentProperties;
import com.qcoe.jdbc.JDBCProperties;
import com.qcoe.jdbc.QueryBuilder;
import com.qcoe.tem.TEMJSONHelper;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.parser.ParseException;


/**
 *
 * @author kbose
 */
public class SystemProperties {
    
     private static SystemProperties  sysProp = null;

     private final JDBCProperties jdbcProperties;
     private final ElasticSearchProperties esProperties;
     private IncidentProperties incProperties;
     private final QueryBuilder qBuilder;
     private boolean workOnline;
     private String logFile;
     
    static SystemProperties getInstance() {
    
        if(sysProp==null){
          sysProp = new SystemProperties();
        }
        
        return sysProp;
    } 

    private SystemProperties() {
        jdbcProperties = new JDBCProperties();
        esProperties = new ElasticSearchProperties();
        qBuilder = new QueryBuilder();
        loadProperties();
    }

    
    ElasticSearchProperties getElasticSearchProperties(){
        return esProperties;
    }
         
    JDBCProperties getJdbcProperties() {
        return jdbcProperties;
    }

    QueryBuilder getqBuilder() {
        return qBuilder;
    }

    IncidentProperties getIncidentProperties() {
        return incProperties;
    }

    public String getLogFile() {
        return logFile;
    }

   
     
    public void loadProperties() {
    
         FileInputStream input=null;
        
         try {
            String file=Configuration.SYSTEMPATH+"/conf/es.properties";
            String jSonfile=Configuration.SYSTEMPATH+"/conf/incidentLookup.json";
            input = new FileInputStream(file);
            Properties prop = new Properties();
            prop.load(input);
            jdbcProperties.setOmsUserName( prop.getProperty("omsUserID").trim());
            jdbcProperties.setOmsPassWord(prop.getProperty("omsPwd").trim());
            jdbcProperties.setOmsURL( prop.getProperty("omsURL").trim());
            jdbcProperties.setOmsPoolSize(Integer.parseInt(prop.getProperty("omsPoolSize").trim()));
            jdbcProperties.setGivUserName( prop.getProperty("givUserId").trim());
            jdbcProperties.setGivPassWord(prop.getProperty("givPwd").trim());
            jdbcProperties.setGivURL( prop.getProperty("givURL").trim());
            jdbcProperties.setGivPoolSize(Integer.parseInt(prop.getProperty("givPoolSize").trim()));
            
            
            esProperties.setPortNo(Integer.parseInt(prop.getProperty("portNo").trim()));
            esProperties.setClusterName(prop.getProperty("clusterName").trim());
            esProperties.setHostIPs(prop.getProperty("hostIPs").trim().split(","));
            TEMJSONHelper jsonHelp = new TEMJSONHelper();
            incProperties = jsonHelp.getIncidentProperties(jSonfile);
            
            incProperties.setFailureFileName(prop.getProperty("failureFileName").trim());

            
            qBuilder.setColumns( prop.getProperty("statisticsColumns").trim().split(","));
            qBuilder.setOrderBy( prop.getProperty("orderBy").trim());
            qBuilder.setWhereCondition(prop.getProperty("whereCondition").trim());
             
            this.workOnline =  ! prop.getProperty("workOnline").trim().equalsIgnoreCase("0");
            this.logFile = prop.getProperty("logFile").trim();
            input.close();
        } catch (IOException | ParseException ex) {
           
            ex.printStackTrace();
             Logger.getLogger(logFile).log(Level.SEVERE, null, ex); 
        } 
        

    }

    boolean isWorkOnline() {
        return workOnline;
    }

   
}
