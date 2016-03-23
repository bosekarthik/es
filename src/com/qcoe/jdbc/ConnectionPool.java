/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.qcoe.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;


/**
 *
 * @author kbose
 */
public class ConnectionPool {
    
    private ArrayList<Connection> omsConnectionPool=null;
    private ArrayList<Connection> givConnectionPool=null;
    private JDBCProperties prop=null;
    private static ConnectionPool dbPool=null;
    
    public static ConnectionPool getInstance (JDBCProperties prop) throws SQLException, ClassNotFoundException {
    
           if(dbPool==null){
             dbPool=new ConnectionPool(prop);
        }

        return dbPool;
        
    }
    
    static ConnectionPool getInstance(){
    
        return dbPool;
    } 
    
    private ConnectionPool(JDBCProperties prop) throws SQLException, ClassNotFoundException {
            this.prop=prop;
            this.omsConnectionPool = new ArrayList<>();
            this.givConnectionPool=new ArrayList<>();
            createConnection();
    }
    
     private void createConnection() throws SQLException, ClassNotFoundException{

       Connection con = null;
       Class.forName("oracle.jdbc.driver.OracleDriver");
     
       
        for(int i=0;i<prop.getOmsPoolSize();i++){
               con =DriverManager.getConnection(prop.getOmsURL(),prop.getOmsUserName(), prop.getOmsPassWord());
               omsConnectionPool.add(con);
        }//for
        
        for(int i=0;i<prop.getGivPoolSize();i++){
               con =DriverManager.getConnection(prop.getGivURL(),prop.getGivUserName(),prop.getGivPassWord());
               givConnectionPool.add(con);
        }
     
        System.out.println("OMS Connection Pool Size = "+omsConnectionPool.size() );
        System.out.println("GIV Connection Pool Size = "+givConnectionPool.size() );
      
     }
    
    
     
    protected Connection getOMSConnectionFromPool() throws InterruptedException {

        Connection con = null;
       //  System.out.println("connectionPool size" +connectionPool.size());
        
          while(true){

                synchronized (this) {
             
                    if(omsConnectionPool.isEmpty()){

                       System.out.println("Waiting for Database Connection ....."+Thread.currentThread().getName());
                       
                   }else{


                           con = (Connection) omsConnectionPool.get(0);
                           omsConnectionPool.remove(0);
                          // System.out.println (Thread.currentThread().getName()+" getOMSConnectionFromPool "+ omsConnectionPool.size());
                         break;
                    }
                
                }//sync
                
                 Thread.sleep(1000);
                 
            }//while
        
         
        return con;
    }
    
    protected Connection getGIVConnectionFromPool() throws InterruptedException {

        Connection con = null;
       //  System.out.println("connectionPool size" +connectionPool.size());
        
          while(true){

                if(givConnectionPool.isEmpty()){
                    
               //     System.out.println("Waiting for Database Connection .....");
                   
                    Thread.sleep(250);
                   
                    continue;
                }
                
                synchronized (this) {
                    con = (Connection) givConnectionPool.get(0);
                    givConnectionPool.remove(0);

                }//sync
                break;
            }//while
        
        return con;
    }
    
    
    
    
     protected void relaseOMSConnectionToPool(Connection con){
        omsConnectionPool.add(con);
      //  System.out.println (Thread.currentThread().getName()+" relaseOMSConnectionToPool "+ omsConnectionPool.size());
    }
     
     protected void relaseGIVConnectionToPool(Connection con){
        givConnectionPool.add(con);
    }
     
     
   public void closeALL() throws SQLException{
      
           
            while(omsConnectionPool.size()>0){
                Connection c = (Connection)omsConnectionPool.remove(0);
                c.close();
                 
            }
       
            while(givConnectionPool.size()>0){
                Connection c = (Connection)givConnectionPool.remove(0);
                c.close();
                 
            }
            
          System.out.println("Connections Closed. OMS Pool Size = "+omsConnectionPool.size());  
          System.out.println("Connections Closed. GIV Pool Size = "+givConnectionPool.size());  
            
   }  
}
