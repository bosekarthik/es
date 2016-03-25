/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.qcoe.config;

/**
 *
 * @author kbose
 */
public class TEMConstants {
    
    //Output JSON
    public static String jn_ASSIGNED_TO ="assigned_to";
    public static String jn_ASSIGNEMENT_GROUP ="assignment_group";
    public static String jn_DURATION ="calendar_duration";
    public static String jn_CALLER_ID ="caller_id";
    public static String jn_CATEGORY ="category";
    public static String jn_CLOSE_NOTES ="close_notes";
    public static String jn_CLOSED_AT ="closed_at";
    public static String jn_INCIDENT_STATE ="incident_state";
    public static String jn_INC_NUMBER ="number";
    public static String jn_PRIORITY ="priority";
    public static String jn_SHORT_DESC ="short_description";
    public static String jn_CREATE_ON ="sys_created_on";
    
    
    public static String jn_CLASSIFICATION="classification";
    public static String jn_BRODERCLASSIFICATION="broaderclassification";
    
    public static String jn_TIMELOST="timelost";
    public static String jn_AGILEHOURS="agilehour";
    
    public static String jn_WEEK="week";
    public static String jn_DAY="day";
    public static String jn_MONTH="month";
    public static String jn_HOUR="hour";
    public static String jn_CST="cst";
    
    
    //common
    public static String jn_SUPPORT ="supported";
    public static String jn_TEAM="team";
    //Input JSON
    

    public static String jn_WEEKDAY ="weekdays";
    public static String jn_WEEKEND ="weekends";
    
    public static String jn_CST_BEGIN ="begin";
    public static String jn_CST_END ="end";
    
    
    public static String INDEXNAME= "tem";
    public static String INCIDENT_DOCUMENT_TYPE= "incidents";
    public static int INCIDENT_START_LINE =1;
    public static String INCIDENT_FILE_IDENTIFIER = "\"number\",\"caller_id\",\"short_description\"";
    public static String TIMEFORMAT = "yyyy-MM-dd HH:mm:ss";
    public static String[] DAYS_OF_WEEK = {"07-SUNDAY","01-MONDAY","02-TUESDAY","03-WEDNESDAY","04-THURSDAY","05-FRIDAY","06-SATURDAY"}; 
    public static String[] MONTHS = {"01-JANUARY","02-FEBRUARY","03-MARCH","04-APRIL","05-MAY","06-JUNE","07-JULY","08-AUGUST","09-SEPTEMBER","10-OCTOBER","11-NOVEMBER","12-DECEMBER"}; 

   
   
   
}
