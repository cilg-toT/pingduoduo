package com.zyinf.servlet;

import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.mysql.jdbc.AbandonedConnectionCleanupThread;

/**
 * SEVERE: The web application [/jiantv] registered the JDBC driver 
 * [com.mysql.jdbc.Driver] but failed to unregister it when the web 
 * application was stopped. To prevent a memory leak, the JDBC Driver 
 * has been forcibly unregistered.
 * 
 * SEVERE: The web application [/jiantv] appears to have started a thread named 
 * [Abandoned connection cleanup thread] but has failed to stop it. This 
 * is very likely to create a memory leak.
 * 
 * <web.xml>
  	<listener>   
    	<listener-class>com.jiantv.servlet.ContextFinalizer</listener-class>   
	</listener>    
 *
 */
public class ContextFinalizer implements ServletContextListener {

    public void contextInitialized(ServletContextEvent sce) {
    }

    public void contextDestroyed(ServletContextEvent sce) {
        Enumeration<Driver> drivers = DriverManager.getDrivers();
        Driver d = null;
        while (drivers.hasMoreElements()) {
            try {
                d = drivers.nextElement();
                DriverManager.deregisterDriver(d);
            } 
            catch (SQLException ex) {
            	ex.printStackTrace();
            }
        }
        try {
            AbandonedConnectionCleanupThread.shutdown();
        } 
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}