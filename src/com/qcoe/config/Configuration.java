/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.qcoe.config;

import com.qcoe.es.ElasticSearchProperties;
import com.qcoe.tem.IncidentProperties;
import com.qcoe.jdbc.JDBCProperties;
import com.qcoe.jdbc.QueryBuilder;
import java.io.File;

/**
 *
 * @author kbose
 */
public class Configuration {
    
    public static final String SYSTEMPATH = new File("").getAbsolutePath();
    public static final boolean WORK_ONLINE=SystemProperties.getInstance().isWorkOnline();
    public static final String LOGFILE=SystemProperties.getInstance().getLogFile();
    public static final JDBCProperties jdbcProperties = SystemProperties.getInstance().getJdbcProperties();
    public static final ElasticSearchProperties esProperties = SystemProperties.getInstance().getElasticSearchProperties();
    public static final IncidentProperties incProperties = SystemProperties.getInstance().getIncidentProperties();
    public static final QueryBuilder qBuilder = SystemProperties.getInstance().getqBuilder();


}
