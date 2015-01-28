package com.samenea.payments.web.translation;

import com.samenea.payments.translation.TrackingTranslator;
import junit.framework.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

/**
 * Created with IntelliJ IDEA.
 * User: ngs
 * Date: 2/25/13
 * Time: 5:14 PM
 * To change this template use File | Settings | File Templates.
 */

@ContextConfiguration(locations = {"classpath:context.xml","classpath*:contexts/mock.xml"})
public class TrackingTranslatorTest extends AbstractJUnit4SpringContextTests {

    @Autowired
    TrackingTranslator trackingTranslator;

    @Test
    public void test_message_order_created(){
        String actual = trackingTranslator.translate(TrackingTranslator.ORDER_CREATED, "سفارش یک");

        Assert.assertEquals("سفارشی با این مشخصات ایجاد شد: سفارش یک", actual);
    }

    @Test
    public void test_message_order_displayed(){
        String actual = trackingTranslator.translate(TrackingTranslator.ORDER_DISPLAYED, "سفارش یک");

        Assert.assertEquals("سفارشی با این مشخصات نمایش داده شد : سفارش یک", actual);
    }

    @Test
    public void test_message_order_display_problem(){
        String actual = trackingTranslator.translate(TrackingTranslator.ORDER_DISPLAY_PROBLEM);

        Assert.assertEquals("خطا در نمایش سفارش! کاربر به صفحه خطا هدایت شد.", actual);
    }

    @Test
    public void test_message_transaction_successful_return(){
        String actual = trackingTranslator.translate(TrackingTranslator.TRANSACTION_SUCCESSFUL_RETURN, "تراکنش یک");

        Assert.assertEquals("بازگشت موفقیت آمیز از بانک با شماره تراکنش : تراکنش یک", actual);
    }

    @Test
    public void test_message_transaction_successful_commit(){
        String actual = trackingTranslator.translate(TrackingTranslator.TRANSACTION_SUCCESSFUL_COMMIT, "تراکنش یک");

        Assert.assertEquals("تراکنش با موفقیت ثبت شد. شماره تراکنش : تراکنش یک", actual);
    }

    @Test
    public void test_message_simia_document_submitted(){
        String actual = trackingTranslator.translate(TrackingTranslator.SIMIA_DOCUMENT_SUBMITTED, "شماره یک");

        Assert.assertEquals("سند مربوط به تراکنش شماره یک در سیستم سیمیا ثبت شد.", actual);
    }

    @Test
    public void test_message_simia_document_failed_transaction(){
        String actual = trackingTranslator.translate(TrackingTranslator.SIMIA_DOCUMENT_FAILED_TARANSACTION, "شماره یک");

        Assert.assertEquals("مشکل در ثبت سند سیمیا برای تراکنش شماره یک", actual);
    }

    @Test
    public void test_message_simia_document_failed_order(){
        String actual = trackingTranslator.translate(TrackingTranslator.SIMIA_DOCUMENT_FAILED_ORDER, "شماره یک");

        Assert.assertEquals("مشکل ثبت سند سیمیا برای سفارش با شماره شماره یک", actual);
    }

    @Test
    public void test_message_transaction_problem(){
        String actual = trackingTranslator.translate(TrackingTranslator.TRANSACTION_PROBLEM, "شماره یک");

        Assert.assertEquals("خطا در انجام تراکنش با شناسه شماره یک", actual);
    }

}
