/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.qcoe.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 *
 * @author kbose
 */
public class SpoolFile{

    private final String fileName;
    private final boolean append;
    private PrintWriter out;
    
    public SpoolFile(String fileName, boolean append){
        this.fileName = fileName;
        this.append = append;
    
    
    }
        
    public void openFile() throws IOException{
       
            out = new PrintWriter(new BufferedWriter(new FileWriter(new File(fileName),append)));
        
    }
    
    public void writeFile(String currentLine){
                out.println(currentLine);  
        
    }
    
    public void spoolFile(ArrayList<String> lines){
    
        if(lines==null)
            return;
        
        for(String line : lines )
            writeFile(line);
        
    }
    
    public void spoolFile(ArrayList<String> lines, String append){
    
        for(String line : lines ){
            String nLine = append +line;
            writeFile(nLine);
        }
    }
    
    public void closeFile(){
    
        out.flush();
        out.close();

    
    }
    
    
    
}