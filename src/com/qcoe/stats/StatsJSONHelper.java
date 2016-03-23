/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.qcoe.stats;

import static com.qcoe.config.StatsConstants.jn_END_TIME_STAMP;
import static com.qcoe.config.StatsConstants.jn_HOSTNAME;
import static com.qcoe.config.StatsConstants.jn_SERVER_NAME;
import static com.qcoe.config.StatsConstants.jn_SERVICE_NAME;
import static com.qcoe.config.StatsConstants.jn_SERVICE_TYPE;
import static com.qcoe.config.StatsConstants.jn_START_TIME_STAMP;
import static com.qcoe.config.StatsConstants.jn_STATISTICS_DETAIL_KEY;
import java.util.ArrayList;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author kbose
 */
public class StatsJSONHelper {
    
   
    
    public StatsJSONHelper()  {
      
       
    }
    
   
    
    public String getStatisticsDetailDocument(StatisticsDetailsJSONDocument sdjd){
    
        String startTimeStamp = sdjd.getStartTimeStamp();
        String endTimeStamp = sdjd.getEndTimeStamp();
        String serviceName = sdjd.getServiceName();
        String serviceType = sdjd.getServiceType();
        
        ArrayList<StatisticsByServerHost> sbshs = sdjd.getStatisticsByServerHost();
       
        JSONObject jsObjMaster = new JSONObject();
        JSONArray detailsArray = new JSONArray();
     
        jsObjMaster.put(jn_START_TIME_STAMP, startTimeStamp);
        jsObjMaster.put(jn_END_TIME_STAMP, endTimeStamp);
        jsObjMaster.put(jn_SERVICE_NAME, serviceName);
        jsObjMaster.put(jn_SERVICE_TYPE, serviceType);

        for(StatisticsByServerHost sbsh :sbshs){
        
           
            JSONArray statisticsArray = new JSONArray();
        
            String serverName=sbsh.getServerName();
            String hostName=sbsh.getHostName();
            ArrayList<Statistics> statistics = sbsh.getStatistics();
            
            JSONObject jsObjTemp = new JSONObject();
            jsObjTemp.put(jn_SERVER_NAME, serverName);
            detailsArray.add(jsObjTemp);

            jsObjTemp = new JSONObject();
            jsObjTemp.put(jn_HOSTNAME, hostName);
            detailsArray.add(jsObjTemp);

            for(Statistics statistic:statistics ){
            
                jsObjTemp = new JSONObject();
                jsObjTemp.put(statistic.getStatisticsName(), statistic.getStatisticsValue());
                jsObjTemp.put(jn_STATISTICS_DETAIL_KEY, statistic.getStatisticsDetailKey());
                statisticsArray.add(jsObjTemp);
            
            }//for
            
            jsObjTemp = new JSONObject();
            jsObjTemp.put("Statistics",statisticsArray);
            detailsArray.add(jsObjTemp);
        
        }//statisticsdetailsbyserverhost
        
        jsObjMaster.put("Details", detailsArray);
        
        System.out.println(jsObjMaster.toJSONString());
            
                 
         
        
        return jsObjMaster.toJSONString();
    }
    
   
}
