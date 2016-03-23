/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.qcoe.tem;

import com.qcoe.config.Configuration;
import static com.qcoe.config.Configuration.SYSTEMPATH;
import static com.qcoe.config.Constants.SUCCESS_MESSAGE;
import com.qcoe.config.TEMConstants;
import static com.qcoe.config.TEMConstants.DAYS_OF_WEEK;
import static com.qcoe.config.TEMConstants.INCIDENT_DOCUMENT_TYPE;
import static com.qcoe.config.TEMConstants.INDEXNAME;
import static com.qcoe.config.TEMConstants.MONTHS;
import static com.qcoe.config.TEMConstants.TIMEFORMAT;
import com.qcoe.es.ElasticSearchManager;
import com.qcoe.es.ElasticSearchProperties;
import com.qcoe.util.SpoolFile;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author kbose
 */
public class IncidentProcessor {
    
    
    private File[] listOfFiles;
    private ArrayList<String> failures;
   

    public IncidentProcessor(){}

    public void setListOfFiles(File[] listOfFiles) {
        this.listOfFiles = listOfFiles;
    }

  
    
    
    public IncidentProcessor(File[] listOfFiles) {
        this.listOfFiles = listOfFiles;
       
    }
    
        
    public void doWork() throws IOException{
    
    
        for(File fileName : listOfFiles){
            
            ArrayList<String> data = this.getFile(fileName);
            HashMap<String,String> incidentCollection = getIncidentCollection(data);
            publishDocument2ES(incidentCollection);
            writeStatus();
        }//for
    
    }
    
    
    private void writeStatus() throws IOException{
    
        String failureFileName = SYSTEMPATH +"/"+ Configuration.incProperties.getFailureFileName();
    
        SpoolFile sFile = new SpoolFile(failureFileName,false);
        sFile.openFile();
       
        if(failures==null || failures.isEmpty()){
            failures = new ArrayList<>();
            failures.add(SUCCESS_MESSAGE);
        }
        sFile.spoolFile(failures);
        sFile.closeFile();
    } 
    
    
    private void publishDocument2ES( HashMap<String,String> incidentCollection ) throws UnknownHostException{
    
        ElasticSearchProperties esProperties = Configuration.esProperties;
        ElasticSearchManager esManager = new ElasticSearchManager(esProperties);
        esManager.insertDocuments(INDEXNAME, INCIDENT_DOCUMENT_TYPE, incidentCollection);
        failures = esManager.getFailures();

    }
    
    private HashMap<String,String>  getIncidentCollection(ArrayList<String> data){
    
        HashMap<String,String> map = new HashMap();
        TEMJSONHelper jsonObj = new TEMJSONHelper();
        
        String token = "\",\"";
        
        for(String line : data){
        
            try{
            String[] details = line.split(token);
        
            String incNumber= details[0].substring(1, details[0].length());
            String callerId= details[1];
            String shortDescription= details[2];
            String category= details[3];
            String priority= details[4];
            String incidentState= details[5];
            String assignmentGroup= details[6];
            String assignedTo= details[7];
            Long calendarDuration= Long.parseLong(details[8]);
            String sysCreatedOn= details[9].trim().length() ==0 ? null :details[9];
            String closeNotes= details[10];
            String closedAt=details[11].trim().length() ==0 ? null :details[11].substring(0, details[11].length()-1) ;
            closedAt= closedAt==null || closedAt.length()==0 ?null :closedAt;
            
            IncidentDetails inc = new IncidentDetails();
            inc.setAssignedTo(assignedTo);
            inc.setAssignmentGroup(assignmentGroup);
            inc.setPriority(priority);
            inc.setCalendarDuration(calendarDuration);
            inc.setCallerId(callerId);
            inc.setCategory(category);
            inc.setCloseNotes(closeNotes);
            inc.setClosedAt(closedAt);
            inc.setIncNumber(incNumber);
            inc.setIncidentState(incidentState);
            inc.setPriority(category);
            inc.setShortDescription(shortDescription);
            inc.setSysCreatedOn(sysCreatedOn);
            setSaravananRules(inc);
            String jSon = jsonObj.getIncidentDocument(inc);
           // System.out.println(jSon);
            map.put(incNumber, jSon);
            }catch(Exception ex) {
              Logger.getLogger(Configuration.LOGFILE).log(Level.SEVERE, line, ex); 
            }
        }
        
        return map;
    }
    
    
    private void setSaravananRules(IncidentDetails inc) throws ParseException{
        
        SimpleDateFormat formatter = new SimpleDateFormat(TIMEFORMAT);
        String createdDate = inc.getSysCreatedOn();
        String completedDate = inc.getClosedAt();
        
        
        Date sDate = formatter.parse(createdDate);
        Date eDate = completedDate ==null ?null : formatter.parse(completedDate);
        
        Calendar openDate = Calendar.getInstance();openDate.setTime(sDate);
        Calendar closeDate =null;
        
        if(eDate !=null ){
            closeDate = Calendar.getInstance();closeDate.setTime(eDate);
         }
        
        String team = Configuration.incProperties.getTeamLookUp().get(inc.getCallerId());
        
        int weekI = openDate.get(Calendar.WEEK_OF_YEAR);
        
        String week =  weekI  <10 ? "0"+weekI : ""+weekI;
     
        String day = DAYS_OF_WEEK[openDate.get(Calendar.DAY_OF_WEEK)-1];
        String month = MONTHS[openDate.get(Calendar.MONTH)];
        int hour =  openDate.get(Calendar.HOUR_OF_DAY);
        String hourS =  hour  <10 ? "0"+hour : ""+hour;
        int cstBegin = Configuration.incProperties.getCstHourBegin();
        int cstEnd = Configuration.incProperties.getCstHourEnd();
        boolean supportedHours=false;
       
        if(day.equalsIgnoreCase(DAYS_OF_WEEK[0]) || day.equalsIgnoreCase(DAYS_OF_WEEK[6])){ //weekEnd
            supportedHours=Configuration.incProperties.getWeekEndSupportHrs().contains(hour+"") ;
        }else{
            supportedHours=Configuration.incProperties.getWeekDaySupportHrs().contains(hour+"") ;
        }
        int noOfMins = closeDate ==null ? -1 :
                (int) TimeUnit.MILLISECONDS.toMinutes(
                 Math.abs(closeDate.getTimeInMillis() - openDate.getTimeInMillis())) ;
        
        int hoursLost = noOfMins/60;
        int minLost = noOfMins%60;
        
        double timeLost =noOfMins ==-1 ? -1 : Double.parseDouble(hoursLost+"."+minLost);
        
        
        
        if(hour >= cstBegin && hour <= cstEnd){
            inc.setCstHour(true);
        }else{
            inc.setCstHour(false);
        }   
        
        
        inc.setWeek(week);
        inc.setDay(day);
        inc.setMonth(month);
        inc.setHour(hourS);
        inc.setTimeLost(timeLost);
        inc.setTeam(team);
        inc.setSupportedHours(supportedHours);
    
    
    
        
        
    }
    
    private ArrayList<String> getFile(File fileName) throws IOException{
    
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        String currentLine;
        StringBuilder dataLine = new StringBuilder();
        ArrayList<String> data = new ArrayList<>();
     
        int line2Skip = TEMConstants.INCIDENT_START_LINE;
        
        
        int lines =0;
        
        while((currentLine = br.readLine()) != null) {
         
            lines++;
          
            if (lines== line2Skip)  {
                if (currentLine.startsWith(TEMConstants.INCIDENT_FILE_IDENTIFIER))
                  continue;
                else
                  break;
            }//if
            
            if (currentLine.matches("\"INC[0-9][0-9][0-9][0-9][0-9][0-9][0-9]\",.*")){

               if( dataLine.length() != 0 ){
                   data.add(dataLine.toString());
                   dataLine = new StringBuilder();
               }
                   
            }
            
           dataLine.append(currentLine);
           
         
         
        }//while
        
         if( dataLine.length() != 0 ){
             data.add(dataLine.toString());
         }
        
        return data;
        
    }//method
    
    
}
