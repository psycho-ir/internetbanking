package com.samenea.payments.web.acceptance;

import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

/**
 * Created with IntelliJ IDEA.
 * User: ngs
 * Date: 3/9/13
 * Time: 4:48 PM
 * To change this template use File | Settings | File Templates.
 */


@RunWith(Cucumber.class)
@Cucumber.Options(features = {"src/test/resources/com/samenea/payments/web/acceptance/"}, format = {"pretty", "html:target/cucumber-html-report"})
public class RunIT {
}
