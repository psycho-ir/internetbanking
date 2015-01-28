package com.samenea.payments.web.controller.deposit;

import com.samenea.banking.customer.ICustomer;
import com.samenea.banking.customer.ICustomerService;
import com.samenea.banking.deposit.ChargeException;
import com.samenea.banking.simia.model.Customer;
import com.samenea.commons.component.utils.log.LoggerFactory;
import com.samenea.payments.model.DefaultDepositOrderService;
import com.samenea.payments.order.CustomerInfo;
import com.samenea.payments.order.Order;
import com.samenea.payments.web.model.common.CustomTimeValidator;
import com.samenea.payments.web.model.deposit.ChargeDepositViewModel;
import com.samenea.payments.web.model.deposit.ConfirmDepositViewModel;
import com.samenea.payments.web.model.View;
import com.samenea.seapay.client.impl.CommunicationException;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;


import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

/**
 * @author: Soroosh Sarabadani
 * Date: 1/30/13
 * Time: 4:15 PM
 */

@ContextConfiguration(locations = {"classpath:context.xml","classpath:contexts/mock.xml"})
public class ConfirmControllerTest extends AbstractJUnit4SpringContextTests {
    private static final Logger logger = LoggerFactory.getLogger(ConfirmControllerTest.class);
    @Mock
    private ICustomerService customerService;
    @Mock
    DefaultDepositOrderService defaultDepositOrderService;
    @Mock
    CustomTimeValidator customTimeValidator;

    private List<ICustomer> customers = new ArrayList<ICustomer>();
    @Mock
    private ChargeDepositViewModel chargeDepositViewModel;
    @Mock
    private ConfirmDepositViewModel confirmDepositViewModel;
    @Mock
    BindingResult result;
    @Mock
    ModelMap modelMap;
    @InjectMocks
    private  ConfirmController confirmController;

    private CustomerInfo customerInfo;

    private String seapayUrl = "http://localhost:8181/web/banks/";

    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
        when(result.hasErrors()).thenReturn(true);
        Customer customer1 = new Customer("test", "test2", "2020", false);
        Customer customer2 = new Customer("test", "test2", "2020", false);
        customers.add(customer1);
        customers.add(customer2);
        when(customTimeValidator.isValid("10:22","22:55")) .thenReturn(true);
    }

    final String depositNumber = "100";
    final String amount = "1000000";

    @Test
    public void should_return_to_charge_deposit_page_when_depositNumber_is_null() {
        when(chargeDepositViewModel.getDepositNumber1()).thenReturn(null);
        final String resultPage = confirmController.showConfirmDepositInfo(modelMap, chargeDepositViewModel, result);
        Assert.assertEquals(View.Deposit.DEPOSIT_CHARGE, resultPage);
    }

    @Test
    public void should_return_to_charge_deposit_page_when_amount_is_null() {
        when(chargeDepositViewModel.getAmount()).thenReturn(null);
        final String resultPage = confirmController.showConfirmDepositInfo(modelMap, chargeDepositViewModel, result);
        Assert.assertEquals(View.Deposit.DEPOSIT_CHARGE, resultPage);
    }

    @Test
    public void should_return_to_charge_page_when_email_is_null() {
        when(chargeDepositViewModel.getEmail()).thenReturn(null);
        final String resultPage = confirmController.showConfirmDepositInfo(modelMap, chargeDepositViewModel, result);
        Assert.assertEquals(View.Deposit.DEPOSIT_CHARGE, resultPage);
    }

    @Test
    public void should_return_to_charge_page_when_description_is_null() {
        when(chargeDepositViewModel.getDescription()).thenReturn(null);
        final String resultPage = confirmController.showConfirmDepositInfo(modelMap, chargeDepositViewModel, result);
        Assert.assertEquals(View.Deposit.DEPOSIT_CHARGE, resultPage);
    }

    @Test
    public void should_return_to_charge_page_when_phoneNumber_is_null() {
        when(chargeDepositViewModel.getPhoneNumber()).thenReturn(null);
        final String resultPage = confirmController.showConfirmDepositInfo(modelMap, chargeDepositViewModel, result);
        Assert.assertEquals(View.Deposit.DEPOSIT_CHARGE, resultPage);
    }

    @Test
    @Ignore
    public void should_return_list_customer_when_depositNumber_is_valid() {
        when(result.hasErrors()).thenReturn(false);
        when(chargeDepositViewModel.getDepositNumber1()).thenReturn(depositNumber);
        when(chargeDepositViewModel.getAmount()).thenReturn(amount);
        when(customerService.findCustomersOfDeposit(depositNumber)).thenReturn(customers);
        final String resultPage = confirmController.showConfirmDepositInfo(modelMap, chargeDepositViewModel, result);
        Assert.assertEquals(View.Deposit.DEPOSIT_CONFIRM, resultPage);
    }

    @Test
    public void should_be_return_to_charge_page_when_modelMap_hasErrors() {
        final String resultPage = confirmController.showConfirmDepositInfo(modelMap, chargeDepositViewModel, result);
        Assert.assertEquals(View.Deposit.DEPOSIT_CHARGE, resultPage);
    }

    @Test
    @Ignore
    public void should_be_return_to_seapayUrl_when_confirmViewModel_has_no_error() throws ChargeException {
        when(result.hasErrors()).thenReturn(false);
        when(confirmDepositViewModel.getAmountDepositNumeric()).thenReturn("2000");
        when(confirmDepositViewModel.getNumberDeposit()).thenReturn("2020");
        customerInfo = new CustomerInfo(confirmDepositViewModel.getPhoneNumber(), confirmDepositViewModel.getEmail());
        Order order = new Order("2020", customerInfo);
        when(defaultDepositOrderService.createCheckedOutDepositChargeOrder("2020", 2000, customerInfo)).thenReturn(order);
        final String resultorderId = confirmController.redirect(confirmDepositViewModel, result) + order.getOrderId().toString();
        Assert.assertEquals("2020", resultorderId);
    }

    @Test
    public void should_be_return_to_charge_page_when_confirmViewModel_haserror() {
        when(result.hasErrors()).thenReturn(true);
        final String resultPage = confirmController.redirect(confirmDepositViewModel, result);
        Assert.assertEquals(View.Deposit.DEPOSIT_CHARGE, resultPage);

    }

    @Test(expected = CommunicationException.class)
    @Ignore
    public void should_be_return_to_exception_page_when_throw_CommunicationException() throws ChargeException {
        ConfirmDepositViewModel confirmDepositViewModel1 = new ConfirmDepositViewModel();
        confirmDepositViewModel1.setAmount(1000);
        confirmDepositViewModel1.setDescription("this is test");
        confirmDepositViewModel1.setPhoneNumber("091256454");
        confirmDepositViewModel1.setEmail("email@samenea.com");
        confirmDepositViewModel1.setNumberDeposit("2545552555");
        confirmDepositViewModel1.setAmountDepositNumeric("1000");
        customerInfo = new CustomerInfo("091256454", "email@samenea.com");
        when(defaultDepositOrderService.createCheckedOutDepositChargeOrder("2545552555", 1000, customerInfo)).thenThrow(CommunicationException.class);
        confirmController.redirect(confirmDepositViewModel1, result);
    }
}
