package com.samenea.payments.web.acceptance.steps;

import junit.framework.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Created with IntelliJ IDEA.
 * User: ngs
 * Date: 3/9/13
 * Time: 5:17 PM
 * To change this template use File | Settings | File Templates.
 */
public class ResolvingOrderSupport {
    WebDriver driver;


    public void getUnresolvedPage(){

        // TODO: we should find a good way to locate base address
        //driver = new HtmlUnitDriver();
        driver = new FirefoxDriver();

        driver.get("http://localhost:8087/payments-war/order/postponeds");
    }
    public void verifyNoOrderExists(){
        WebDriverWait wait = new WebDriverWait(driver, 100);
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.id("div_empty")));

        WebElement element = driver.findElement(By.id("div_empty"));
        Assert.assertNotNull(element);
        driver.quit();

    }
}
