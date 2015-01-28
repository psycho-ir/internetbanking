package com.samenea.payments.web.acceptance.steps;

import com.samenea.commons.component.utils.test.AbstractAcceptanceTestExecutionListener;
import org.springframework.test.context.TestExecutionListener;

/**
 * Created with IntelliJ IDEA.
 * User: ngs
 * Date: 4/7/13
 * Time: 1:30 PM
 * To change this template use File | Settings | File Templates.
 */
public class AcceptanceTestExecutionListener  extends AbstractAcceptanceTestExecutionListener {

    @Override
    protected String getDatasourceName() {
        return "dataSource";
    }

    @Override
    protected String getXmlDataFileLoaderName() {
        return "fullXmlDataFileLoader";
    }
}
