package com.samenea.payments.web.acceptance.steps;

import com.samenea.commons.component.utils.log.LoggerFactory;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import org.apache.commons.dbcp.BasicDataSource;
import org.dbunit.DatabaseUnitException;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.ext.oracle.Oracle10DataTypeFactory;
import org.dbunit.operation.DatabaseOperation;
import org.dbunit.util.fileloader.FullXmlDataFileLoader;
import org.slf4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Created with IntelliJ IDEA.
 * User: ngs
 * Date: 3/9/13
 * Time: 5:03 PM
 * To change this template use File | Settings | File Templates.
 */


public class ResolvingOrderStepDefinitions{
    ResolvingOrderSupport resolvingOrderSupport = new ResolvingOrderSupport();
    private Logger logger = LoggerFactory.getLogger(ResolvingOrderStepDefinitions.class);

    private String url;
    private String username;
    private String password;

    public ResolvingOrderStepDefinitions() {
        Properties p = new Properties();
        try {
            p.load(this.getClass().getResourceAsStream("/jdbc.properties"));
        } catch (IOException e) {
           logger.info("can not find jdbc.properites for acceptance test!!!");
        }

        url = (String)p.get("jdbc.url");
        username = (String)p.get("jdbc.username");
        password = (String)p.get("jdbc.password");
    }


    private void handleSetUpOperation() throws Exception{
        final IDatabaseConnection conn = getConnection();
        final IDataSet data = getDataSet();

        try{
             DatabaseOperation.CLEAN_INSERT.execute(conn, data);
        }finally{
            conn.close();
        }
    }

    private IDataSet getDataSet() throws IOException,
            DataSetException {

        return new FullXmlDataFileLoader().load("/sample-data.xml");
    }

    private IDatabaseConnection getConnection() throws ClassNotFoundException, SQLException, DatabaseUnitException {
        IDatabaseConnection idc = new DatabaseConnection(DriverManager.
                getConnection(url, username, password));

        return idc;
    }

    @Given("there is no unresolved orders")
    public void setupNoUnresolvedOrder() throws Exception {
        handleSetUpOperation();

    }

    @When("admin gets unresolved orders page")
    public void getUnresolvedPage(){
        resolvingOrderSupport.getUnresolvedPage();

    }

    @Then("the page doesn't show any order to resolve")
    public void verifyNoOrderExistsOnPage(){
        resolvingOrderSupport.verifyNoOrderExists();
    }


}
