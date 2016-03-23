/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.qcoe.tem;


import static com.qcoe.config.TEMConstants.jn_AGILEHOURS;
import static com.qcoe.config.TEMConstants.jn_ASSIGNED_TO;
import static com.qcoe.config.TEMConstants.jn_ASSIGNEMENT_GROUP;
import static com.qcoe.config.TEMConstants.jn_BRODERCLASSIFICATION;
import static com.qcoe.config.TEMConstants.jn_CALLER_ID;
import static com.qcoe.config.TEMConstants.jn_CATEGORY;
import static com.qcoe.config.TEMConstants.jn_CLASSIFICATION;
import static com.qcoe.config.TEMConstants.jn_CLOSED_AT;
import static com.qcoe.config.TEMConstants.jn_CLOSE_NOTES;
import static com.qcoe.config.TEMConstants.jn_CREATE_ON;
import static com.qcoe.config.TEMConstants.jn_CST;
import static com.qcoe.config.TEMConstants.jn_CST_BEGIN;
import static com.qcoe.config.TEMConstants.jn_CST_END;
import static com.qcoe.config.TEMConstants.jn_DAY;
import static com.qcoe.config.TEMConstants.jn_DURATION;
import static com.qcoe.config.TEMConstants.jn_HOUR;
import static com.qcoe.config.TEMConstants.jn_INCIDENT_STATE;
import static com.qcoe.config.TEMConstants.jn_INC_NUMBER;
import static com.qcoe.config.TEMConstants.jn_MONTH;
import static com.qcoe.config.TEMConstants.jn_PRIORITY;
import static com.qcoe.config.TEMConstants.jn_SHORT_DESC;
import static com.qcoe.config.TEMConstants.jn_SUPPORT;
import static com.qcoe.config.TEMConstants.jn_TEAM;
import static com.qcoe.config.TEMConstants.jn_TIMELOST;
import static com.qcoe.config.TEMConstants.jn_WEEK;
import static com.qcoe.config.TEMConstants.jn_WEEKDAY;
import static com.qcoe.config.TEMConstants.jn_WEEKEND;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author kbose
 */
public class TEMJSONHelper {
    
  
    
    public IncidentProperties getIncidentProperties(String fileName) throws IOException, ParseException{
        
        JSONParser parser = new JSONParser();
        BufferedReader input = new BufferedReader(new FileReader(fileName));
        JSONObject parsedJSONObject = (JSONObject) parser.parse(input); 
        
        IncidentProperties incProp = new IncidentProperties();
        HashMap<String,String> teamLookUp = new HashMap<>();
        ArrayList<String> weekDaySupportHrs = new ArrayList<>();
        ArrayList<String> weekEndSupportHrs = new ArrayList<>();
      
        
        JSONArray teamDataList = (JSONArray)parsedJSONObject.get(jn_TEAM);
    
            for (Object name_team : teamDataList) {
                String name = (String) ((JSONArray)name_team).get(0);
                String team = (String) ((JSONArray)name_team).get(1);
                teamLookUp.put(name, team);
              //  System.out.println(name + " " +team);
            }
        
        incProp.setTeamLookUp(teamLookUp);
            
        JSONArray supportList = (JSONArray)parsedJSONObject.get(jn_SUPPORT);    
        
            JSONObject temp = (JSONObject) supportList.get(0) ;
            JSONArray hours =(JSONArray) temp.get(jn_WEEKDAY);
                
                for(Object hour : hours){
                        weekDaySupportHrs.add(hour.toString());
                       // System.out.println(jn_WEEKDAY + " " +hour);
                 }
            
            temp = (JSONObject) supportList.get(1) ;
            hours =(JSONArray) temp.get(jn_WEEKEND);
                
                for(Object hour : hours){
                        weekEndSupportHrs.add(hour.toString());
                       // System.out.println(jn_WEEKEND + " " +hour);
                 }
                
                
                
        incProp.setWeekDaySupportHrs(weekDaySupportHrs);
        incProp.setWeekEndSupportHrs(weekEndSupportHrs);
            
        JSONArray cstList = (JSONArray)parsedJSONObject.get(jn_CST);        
        
            temp = (JSONObject) cstList.get(0) ;
            long cstHourBegin =  ((Long) temp.get(jn_CST_BEGIN));
            incProp.setCstHourBegin((int)cstHourBegin);
              
            temp = (JSONObject) cstList.get(1) ;
            long cstHourEnd =  ((Long) temp.get(jn_CST_END));
            incProp.setCstHourEnd((int)cstHourEnd);
            
        // System.out.println(cstHourBegin + " " +cstHourEnd);
        
        return incProp;
    }
    
    
    String getIncidentDocument(IncidentDetails inc){
    
        JSONObject jsObjMaster = new JSONObject();
        jsObjMaster.put(jn_INC_NUMBER, inc.getIncNumber());
        jsObjMaster.put(jn_CALLER_ID, inc.getCallerId());
        jsObjMaster.put(jn_SHORT_DESC, inc.getShortDescription());
        jsObjMaster.put(jn_CATEGORY, inc.getCategory());
        
        jsObjMaster.put(jn_PRIORITY, inc.getPriority());
        jsObjMaster.put(jn_INCIDENT_STATE, inc.getIncidentState());
        jsObjMaster.put(jn_ASSIGNEMENT_GROUP, inc.getAssignmentGroup());
        jsObjMaster.put(jn_ASSIGNED_TO, inc.getAssignedTo());
        
        jsObjMaster.put(jn_DURATION, inc.getCalendarDuration());
        jsObjMaster.put(jn_CREATE_ON, inc.getSysCreatedOn());
        jsObjMaster.put(jn_CLOSE_NOTES, inc.getCloseNotes());
        jsObjMaster.put(jn_CLOSED_AT, inc.getClosedAt());
        
       
        jsObjMaster.put(jn_CLASSIFICATION, inc.getClassification());
        jsObjMaster.put(jn_BRODERCLASSIFICATION, inc.getBroaderClassification());
        jsObjMaster.put(jn_TIMELOST, inc.getTimeLost());
        jsObjMaster.put(jn_AGILEHOURS, inc.isAgileHours());
        
        jsObjMaster.put(jn_TEAM, inc.getTeam());
        jsObjMaster.put(jn_WEEK, inc.getWeek());
        jsObjMaster.put(jn_DAY, inc.getDay());
        jsObjMaster.put(jn_MONTH, inc.getMonth());
        
        jsObjMaster.put(jn_HOUR, inc.getHour());
        jsObjMaster.put(jn_CST, inc.isCstHour());
        jsObjMaster.put(jn_SUPPORT, inc.isSupportedHours());
        
        return jsObjMaster.toJSONString();
        
    }
    
}
