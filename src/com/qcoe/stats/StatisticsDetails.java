/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.qcoe.stats;
import com.qcoe.config.Constants;
import com.qcoe.config.Configuration;
import com.qcoe.jdbc.QueryHandler;
import com.qcoe.jdbc.QueryProperties;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author kbose
 */
public class StatisticsDetails {
    
    
    
    public HashMap<String,String> getGIVStatisticsDetails(String sTime, String eTime) throws SQLException, InterruptedException, IOException{
    
       String query = getQuery(Constants.GIV_SCHEMA_NAME,sTime,eTime); 
       QueryProperties qp = new QueryProperties();
       qp.setApplication(QueryProperties.GIV);
       qp.setQuery(query);
      
       System.out.println(query);
       
       QueryHandler qHandler = new QueryHandler(qp);
       ArrayList<String> statsData= qHandler.collectData();
        
       for( String data : statsData){
           System.out.println(data);
       }
       
        return null;
    
    }
    
    
    private ArrayList<StatisticsDetailsJSONDocument> getStatisticsDetailsJSONDocuments( ArrayList<String> statsData){
    
         ArrayList<StatisticsDetailsJSONDocument>  statisticsDetailsJSONDocuments  = new ArrayList<>();
        
         String previousServiceName =null;
         ArrayList<String> cutLines = new ArrayList<>();
         
       
         for(String data : statsData){
         
            String[] sDetails = data.split(",");
            String serviceName = sDetails[5];
            
               if( previousServiceName!=null && (! previousServiceName.equalsIgnoreCase(serviceName)) ){
                    StatisticsDetailsJSONDocument sdd = getStatisticsDetailsJSONDocument(cutLines);
                    statisticsDetailsJSONDocuments.add(sdd);
                    cutLines = new ArrayList<>();
                    
                }
                           
                cutLines.add(data);
                previousServiceName = serviceName;
                
        }//for
         
         
        return statisticsDetailsJSONDocuments;
        
    }
    
    
    private StatisticsDetailsJSONDocument getStatisticsDetailsJSONDocument(ArrayList<String> cutData){
    
        
        StatisticsDetailsJSONDocument sdd = new StatisticsDetailsJSONDocument();
        
        for(String data : cutData){
        
            String[] sDetails = data.split(",");
            String statisticsDetailKey = sDetails[0];
            String startTimeStamp = sDetails[1];
            String endTimeStamp = sDetails[2];
            String serverName= sDetails[3];
            String hostName= sDetails[4];
            String serviceName = sDetails[5];
            String serviceType = sDetails[6];
            String statisticsName = sDetails[7];
            long statisticsValue = Long.parseLong(sDetails[8]);
        
            sdd.setEndTimeStamp(endTimeStamp);
            sdd.setServiceName(serviceName);
            sdd.setServiceType(serviceType);
            sdd.setStartTimeStamp(startTimeStamp);
            
            
            
        }
        
        
        return sdd;
    }
    
    private String getQuery(String schema, String sTime, String eTime){
    
        String[] cols = Configuration.qBuilder.getColumns();
        String whereCondition = Configuration.qBuilder.getWhereCondition();
        String orderBy = Configuration.qBuilder.getOrderBy();
        
        whereCondition = whereCondition.replace("<S1>", sTime);
        whereCondition = whereCondition.replace("<S2>", eTime);
        
        StringBuilder sbQuery= new StringBuilder();
        
        sbQuery.append("SELECT ");
        
        for (String col : cols){
        
            sbQuery.append(col);
            sbQuery.append(",");
        }
        
        String query = sbQuery.toString();
        
        query = query.substring(0, query.length()-1);
        sbQuery= new StringBuilder(query);
        sbQuery.append(" FROM "); 
        sbQuery.append(schema);sbQuery.append(".");sbQuery.append("YFS_STATISTICS_DETAIL");
        sbQuery.append(" WHERE ");
        sbQuery.append(whereCondition);
        sbQuery.append(" ORDER BY ");
        sbQuery.append(orderBy);
        
        query = sbQuery.toString();
        
        
        return query;
    }//method
    
}
