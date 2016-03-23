/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.qcoe.jdbc;

import com.qcoe.util.SpoolFile;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;



/**
 *
 * @author kbose
 */
public class QueryHandler {
    
    private final QueryProperties qp;
    private final ConnectionPool cp;
   
    private  Connection conn;
    
    public QueryHandler(QueryProperties qp){
        this.qp = qp;
        cp = ConnectionPool.getInstance();
        
    }
    
    public void writeData() throws IOException, SQLException, InterruptedException{
    
        String sql = qp.getQuery();
        
        boolean isOMS = qp.isOMS();
        String fileName = qp.getOutputFile();
        
        SpoolFile csv = new SpoolFile(fileName,false);  
        csv.openFile();
        
        boolean connectionSet = setConnection(isOMS);
            
        if(!connectionSet)
        return ;
                        
        try (Statement statement = conn.createStatement(); ResultSet rs = statement.executeQuery(sql)) {
        
            int noFields = rs.getMetaData().getColumnCount();
            while(rs.next()){
                
                StringBuilder sb = new StringBuilder();
                
                for(int i=1 ;i<noFields;i++){
                    sb.append(rs.getString(i)==null ? "":rs.getString(i).trim());
                    sb.append(",");
                }//for
                
                sb.append(rs.getString(noFields)==null ?"":rs.getString(noFields).trim());
               // System.out.println(sb);
                csv.writeFile(sb.toString());
            }//while rs
        }finally{
             csv.closeFile(); 
            releaseConnection(isOMS);
        }
        
    
    }
   
    
    public ArrayList<String> collectData() throws SQLException, InterruptedException {
                 
        ArrayList<String> result = new ArrayList();
        String sql = qp.getQuery();
        
        boolean isOMS = qp.isOMS();
        boolean connectionSet = setConnection(isOMS);
            
        if(!connectionSet)
        return result;

            
        try (Statement statement = conn.createStatement(); ResultSet rs = statement.executeQuery(sql)) {
            
            int noFields = rs.getMetaData().getColumnCount();
            while(rs.next()){
                
                StringBuilder sb = new StringBuilder();
                
                for(int i=1 ;i<noFields;i++){
                    sb.append(rs.getString(i)==null ? "":rs.getString(i).trim());
                    sb.append(",");
                }//for
                
                sb.append(rs.getString(noFields)==null ?"":rs.getString(noFields).trim());
                
                result.add(sb.toString());
            }//while rs
        }finally {
            releaseConnection(isOMS);
        }
        
        return result;
    
    }
    
    public void execute() throws SQLException, InterruptedException{
    
        String sql = qp.getQuery();
        boolean isOMS = qp.isOMS();
        
    
     
          setConnection(isOMS);
          Statement statement;
          statement = conn.createStatement();
          statement.executeUpdate(sql);
          statement.close();
          
        releaseConnection(isOMS);
        
        
    }
    
    
    private boolean setConnection(boolean isOMS) throws InterruptedException{
    
     if(cp==null)
         return false;
        
     if(isOMS){
             conn = cp.getOMSConnectionFromPool();
        }else{
            conn = cp.getGIVConnectionFromPool();
        }
     
        return true;
     
    }
    
    private boolean releaseConnection(boolean isOMS){
    
        if(cp==null)
         return false;
     
            if(isOMS)
                cp.relaseOMSConnectionToPool(conn);
            else
                cp.relaseGIVConnectionToPool(conn);
       
       return true;
       
    }
    
}
