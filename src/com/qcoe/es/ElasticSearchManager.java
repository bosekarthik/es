/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.qcoe.es;

import com.qcoe.config.Configuration;
import static com.qcoe.config.Constants.FAILURE_MESSAGE;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;

/**
 *
 * @author kbose
 */
public class ElasticSearchManager {
    
    private final ElasticSearchProperties esProperties;
    private Client client;
    private ArrayList<String> failures;
    
    public ElasticSearchManager(ElasticSearchProperties esProperties)  {
       
        this.esProperties =esProperties;
        
    }

        
    
    public void insertDocuments(String indexName,String docType, HashMap<String,String> documents) throws UnknownHostException{
   
        try{
            
            openClient();
        
            if(client ==null )
            return;

            failures = new ArrayList<>();

        
            for(String key : documents.keySet()){
                try{
                    this.client.prepareIndex(indexName, docType,key).setSource(documents.get(key)).get();


                }catch(Exception e){
                    System.out.println("Exception "+key);
                    failures.add(FAILURE_MESSAGE+" "+key +"; " + e.getMessage());
                    Logger.getLogger(Configuration.LOGFILE).log(Level.INFO, e.getMessage(), e);


                }
            }
        }finally{
         closeClient();        
        }
            
    }

    
    public ArrayList<String> getDocumentIDs(String indexName,String docType) throws UnknownHostException{
      try{
            
            openClient();
            
        SearchRequestBuilder request = client.prepareSearch(indexName);
        request.setTypes(docType);
        request.setSearchType(SearchType.DFS_QUERY_THEN_FETCH);
        request.setQuery(QueryBuilders.termQuery("number", "INC2595066"));                 // Query
      //  .setPostFilter(QueryBuilders.rangeQuery("age").from(12).to(18))     // Filter
      //  .setFrom(0).setSize(60).setExplain(true)
       SearchResponse response = request.execute().actionGet();
       
        System.out.println(request);
        System.out.println("------------------------------------");
        System.out.println(response);
        
        for (SearchHit sr : response.getHits().getHits()){
        
            System.out.println(sr);
        }
        }finally{
         closeClient();        
        }
        return null;
    }
    
      
    public Map<String,Map> getDocuments(String indexName,String docType,ArrayList<String> docIDs) throws UnknownHostException{
    
        HashMap<String,Map> documents = new HashMap<>();
        
        try{
            
            openClient();

            for(String docID : docIDs){
            
                GetResponse response = client.prepareGet(indexName, docType, docID).setOperationThreaded(false).get();
                Map<String,Object> document =response.getSource();
                documents.put(docID, document);
                
            }
        
        
        }finally{
         closeClient();        
        }

        return documents;
           
        
    }
    
    
    public ArrayList<String> getFailures() {
        return failures;
    }
    
    
    
    
    private void openClient() throws UnknownHostException{
    
        Settings settings = Settings.settingsBuilder().put("cluster.name", esProperties.getClusterName()).build();
        String[] ips = esProperties.getHostIPs();
        InetSocketTransportAddress[] transportAddress = new InetSocketTransportAddress[ips.length];
        int i=0;
        
        for(String ip :ips){
            transportAddress[i]= new  InetSocketTransportAddress(InetAddress.getByName(ip), esProperties.getPortNo());
            i++;        
        }
        
        
        this.client = TransportClient.builder().settings(settings).build().addTransportAddresses( transportAddress   );
       
        
    }
    
    private void closeClient(){
       
        if(this.client!=null){
            this.client.close();
        }
    }
    
}
