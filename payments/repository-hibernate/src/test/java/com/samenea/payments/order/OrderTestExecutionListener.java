package com.samenea.payments.order;

import com.samenea.commons.component.utils.test.AbstractAcceptanceTestExecutionListener;

public class OrderTestExecutionListener extends AbstractAcceptanceTestExecutionListener {

	@Override
	protected String getDatasourceName() {
		return "dataSource";
	}

	@Override
	protected String getXmlDataFileLoaderName() {
		return "fullXmlDataFileLoader";
	}

}
