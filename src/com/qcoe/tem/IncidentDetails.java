/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.qcoe.tem;

/**
 *
 * @author kbose
 */
public class IncidentDetails {
    
    											

    private String incNumber;
    private String callerId;
    private String shortDescription;
    private String category;
    private String priority;
    private String incidentState;
    private String assignmentGroup;
    private String assignedTo;
    private Long calendarDuration;
    private String sysCreatedOn;
    private String closeNotes;
    private String closedAt;

    private boolean supportedHours;//
    private String classification;
    private String broaderClassification;
    private double timeLost;//
    private boolean agileHours;
    private String team;//
    private String week;//
    private String day;//
    private String month;//
    private String hour;//
    private boolean cstHour;//
    
   
    
    
    
    public String getIncNumber() {
        return incNumber;
    }

    public void setIncNumber(String incNumber) {
        this.incNumber = incNumber;
    }

    public String getCallerId() {
        return callerId;
    }

    public void setCallerId(String callerId) {
        this.callerId = callerId;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getIncidentState() {
        return incidentState;
    }

    public void setIncidentState(String incidentState) {
        this.incidentState = incidentState;
    }

    public String getAssignmentGroup() {
        return assignmentGroup;
    }

    public void setAssignmentGroup(String assignmentGroup) {
        this.assignmentGroup = assignmentGroup;
    }

    public String getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(String assignedTo) {
        this.assignedTo = assignedTo;
    }

    public Long getCalendarDuration() {
        return calendarDuration;
    }

    public void setCalendarDuration(Long calendarDuration) {
        this.calendarDuration = calendarDuration;
    }

    public String getSysCreatedOn() {
        
        return sysCreatedOn;
    }

    public void setSysCreatedOn(String sysCreatedOn) {
        this.sysCreatedOn = sysCreatedOn;
    }

    public String getCloseNotes() {
        return closeNotes;
    }

    public void setCloseNotes(String closeNotes) {
        this.closeNotes = closeNotes;
    }

    public String getClosedAt() {
        return closedAt;
    }

    public void setClosedAt(String closedAt) {
        this.closedAt = closedAt;
    }

    public boolean isSupportedHours() {
        return supportedHours;
    }

    public void setSupportedHours(boolean supportedHours) {
        this.supportedHours = supportedHours;
    }

    public String getClassification() {
        return classification;
    }

    public void setClassification(String classification) {
        this.classification = classification;
    }

    public String getBroaderClassification() {
        return broaderClassification;
    }

    public void setBroaderClassification(String broaderClassification) {
        this.broaderClassification = broaderClassification;
    }

    public double getTimeLost() {
        return timeLost;
    }

    public void setTimeLost(double timeLost) {
        this.timeLost = timeLost;
    }

    public boolean isAgileHours() {
        return agileHours;
    }

    public void setAgileHours(boolean agileHours) {
        this.agileHours = agileHours;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public boolean isCstHour() {
        return cstHour;
    }

    public void setCstHour(boolean cstHour) {
        this.cstHour = cstHour;
    }

    
    
    
    
    
}
