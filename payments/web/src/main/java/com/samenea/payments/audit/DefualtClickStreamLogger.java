package com.samenea.payments.audit;

import com.opensymphony.clickstream.Clickstream;
import com.opensymphony.clickstream.ClickstreamRequest;
import com.opensymphony.clickstream.logger.ClickstreamLogger;
import com.samenea.commons.component.utils.log.LoggerFactory;
import org.apache.commons.dbcp.BasicDataSource;
import org.slf4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

/**
 * Created with IntelliJ IDEA.
 * User: ngs
 * Date: 4/9/13
 * Time: 10:54 AM
 * To change this template use File | Settings | File Templates.
 */

@Configurable
@Component
public class DefualtClickStreamLogger implements ClickstreamLogger, ApplicationContextAware {
    private Logger logger = LoggerFactory.getLogger(DefualtClickStreamLogger.class);
    private ApplicationContext applicationContext;
    private BasicDataSource dataSource;


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public DefualtClickStreamLogger() {


    }

    /* (non-Javadoc)
     * @see com.opensymphony.clickstream.logger.ClickstreamLogger#log(com.opensymphony.clickstream.Clickstream)
     */
    @Override
    public void log(Clickstream cs) {
        if(dataSource == null){
            dataSource = (BasicDataSource) this.applicationContext.getBean("dataSource");
        }
        try {
            Connection conn = dataSource.getConnection();

            PreparedStatement pst = conn
                    .prepareStatement("INSERT INTO clickstream(sessionid,streamhost,referrer,lastreqtime,starttime) VALUES(?,?,?,?,?)");
            pst.setString(1, cs.getSession().getId());
            pst.setString(2, cs.getHostname());
            pst.setString(3, cs.getInitialReferrer());
            pst.setTimestamp(4, new java.sql.Timestamp(cs.getLastRequest().getTime()));
            pst.setTimestamp(5, new java.sql.Timestamp(cs.getStart().getTime()));

            pst.executeUpdate();
            pst.close();

            PreparedStatement pst2 = conn
                    .prepareStatement("INSERT INTO clickstream_requests(sessionid, protocol,qrystring, streamuser, uri, server, port, reqtime) VALUES(?,?,?,?,?,?,?,?)");


            for (Iterator iterator = cs.getStream().iterator(); iterator
                    .hasNext();) {
                ClickstreamRequest request = (ClickstreamRequest) iterator
                        .next();
                pst2.setString(1, cs.getSession().getId());
                pst2.setString(2, request.getProtocol());
                pst2.setString(3, request.getQueryString());
                pst2.setString(4, request.getRemoteUser());
                pst2.setString(5, request.getRequestURI());
                pst2.setString(6, request.getServerName());
                pst2.setInt(7, request.getServerPort());
                pst2.setTimestamp(8, new java.sql.Timestamp(request.getTimestamp().getTime()));

                pst2.executeUpdate();
                pst2.clearParameters();

            }
            pst2.close();
            conn.close();
        } catch (SQLException e) {
           logger.debug("problem in saving click stream audit log to db! cs host : "+ cs.getHostname());
        }
    }


}
