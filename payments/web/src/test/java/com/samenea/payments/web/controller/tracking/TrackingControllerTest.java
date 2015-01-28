package com.samenea.payments.web.controller.tracking;

import com.ibm.icu.util.Calendar;
import com.samenea.commons.component.model.exceptions.NotFoundException;
import com.samenea.payments.order.CustomerInfo;
import com.samenea.payments.order.Order;
import com.samenea.payments.web.model.View;
import com.samenea.payments.web.model.tracking.TrackingSearchViewModel;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.ModelMap;
import com.samenea.payments.order.OrderService;
import org.springframework.validation.BindingResult;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Date: 3/27/13
 * Time: 3:28 PM
 *
 * @Author:payam
 */
public class TrackingControllerTest {
    final  String EMAIL="test@test.com";
    final  String TRACKINGID="ORD_123";
    @Mock
    BindingResult result;
    @Mock
    CustomerInfo customerInfo;
    @InjectMocks
    private TrackingController trackingController;
    @Mock
    OrderService orderService;
    @Mock
    Order order;
    @Mock
    ModelMap modelMap;
    TrackingSearchViewModel trackSearchViewModel;
    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
        Calendar calendar = Calendar.getInstance();
        calendar.set(2012, 10, 12, 10, 10, 0);
        when(order.getCreateDate()).thenReturn(calendar.getTime());
        calendar.set(2012, 10, 12, 14, 12, 0);
        when(order.getLastUpdateDate()).thenReturn(calendar.getTime());
        when(customerInfo.getEmail()).thenReturn(EMAIL);
        when(order.getCustomerInfo()).thenReturn(customerInfo);
        trackSearchViewModel=new TrackingSearchViewModel();
        trackSearchViewModel.setTrackID(TRACKINGID);
    }
    @Test
    public void should_be_return_to_tracking_page(){
        final  String resultPage= trackingController.index(modelMap);
        Assert.assertEquals(View.Tracking.TRACKING, resultPage);

    }
    @Test
    public void should_set_modelMap_attributes_for_using_in_view() {
        trackingController.index(modelMap);
        verify(modelMap).addAttribute(eq("trackingSearchViewModel"), any(TrackingSearchViewModel.class));
        verify(modelMap).addAttribute(eq("hasError"), anyString());

    }
    @Test
    public void should_be_return_tracking_page_when_orderId_does_not_exist (){
        doThrow(new NotFoundException("Mock NotFoundException")).when(orderService).
                findByOrderId(TRACKINGID);
        final  String resultPage= trackingController.find(modelMap, trackSearchViewModel,result);
        Assert.assertEquals(View.Tracking.TRACKING, resultPage);

    }
    @Test
    public void should_set_modelMap_attributes_when_orderId_does_not_exist (){
        doThrow(new NotFoundException("Mock NotFoundException")).when(orderService).
                findByOrderId(TRACKINGID);
        trackingController.find(modelMap, trackSearchViewModel,result);
        verify(modelMap).addAttribute(eq("hasError"), anyString());
    }
    @Test
    public void should_set_modelMap_attributes_when_orderId_does_not_match_with_email (){
        trackSearchViewModel.setEmail("test2@gmail.com");
        when(orderService.findByOrderId(TRACKINGID)).thenReturn(order);
        trackingController.find(modelMap, trackSearchViewModel,result);
        verify(modelMap).addAttribute(eq("hasError"), anyString());
    }
    @Test
    public void should_be_return_order_page_when_orderId_exist_and_match_with_email (){
        trackSearchViewModel.setEmail(EMAIL);
        when(order.getStatus()).thenReturn(Order.Status.POSTPONED);
        when(orderService.findByOrderId(TRACKINGID)).thenReturn(order);
        final  String resultPage= trackingController.find(modelMap, trackSearchViewModel,result);
        Assert.assertEquals(View.Tracking.ORDER, resultPage);
    }
}
