package com.samenea.payments.order;

import org.junit.Test;

/**
 * @author: Jalal Ashrafi
 * Date: 1/27/13
 */
public class LineItemTest {
    @Test(expected = IllegalArgumentException.class)
    public void constructor_should_not_allow_null(){
        new LineItem(null,100);
    }
}
