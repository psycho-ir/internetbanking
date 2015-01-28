package com.samenea.payments.order;

import org.junit.Test;

/**
 * @author: Jalal Ashrafi
 * Date: 1/27/13
 */
public class ProductSpecTest {
    @Test(expected = IllegalArgumentException.class)
    public void constructor_should_not_accept_null_product_name(){
        new ProductSpec(null,"prop1 == value1");
    }
    @Test(expected = IllegalArgumentException.class)
    public void constructor_should_not_accept_empty_product_name(){
        new ProductSpec("  ","prop1 == value1");
    }
}
