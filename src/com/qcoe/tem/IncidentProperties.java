/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.qcoe.tem;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author kbose
 */
public class IncidentProperties {
    
    private String failureFileName;
    private HashMap<String,String> teamLookUp;
    private ArrayList<String> weekDaySupportHrs;
    private ArrayList<String> weekEndSupportHrs;
    private int cstHourBegin;
    private int cstHourEnd;
    
    public int getCstHourBegin() {
        return cstHourBegin;
    }

    public void setCstHourBegin(int cstHourBegin) {
        this.cstHourBegin = cstHourBegin;
    }

    public int getCstHourEnd() {
        return cstHourEnd;
    }

    public void setCstHourEnd(int cstHourEnd) {
        this.cstHourEnd = cstHourEnd;
    }

    public HashMap<String, String> getTeamLookUp() {
        return teamLookUp;
    }

    public void setTeamLookUp(HashMap<String, String> teamLookUp) {
        this.teamLookUp = teamLookUp;
    }

    public ArrayList<String> getWeekDaySupportHrs() {
        return weekDaySupportHrs;
    }

    public void setWeekDaySupportHrs(ArrayList<String> weekDaySupportHrs) {
        this.weekDaySupportHrs = weekDaySupportHrs;
    }

    public ArrayList<String> getWeekEndSupportHrs() {
        return weekEndSupportHrs;
    }

    public void setWeekEndSupportHrs(ArrayList<String> weekEndSupportHrs) {
        this.weekEndSupportHrs = weekEndSupportHrs;
    }

    
    
    
    public String getFailureFileName() {
        return failureFileName;
    }

    public void setFailureFileName(String failureFileName) {
        this.failureFileName = failureFileName;
    }
    
    
    
    
    
}
