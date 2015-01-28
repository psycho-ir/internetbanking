package com.samenea.payments.order;

import com.samenea.commons.component.model.exceptions.NotFoundException;
import com.samenea.commons.component.utils.log.LoggerFactory;
import com.samenea.payments.model.DepositParams;
import org.hibernate.SessionFactory;
import org.hibernate.exception.ConstraintViolationException;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import test.annotation.DataSets;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

/**
 * @author: Jalal Ashrafi
 * Date: 1/28/13
 */
@TestExecutionListeners(OrderTestExecutionListener.class)
@DataSets(setUpDataSet = "/test-data/default.xml")
@ContextConfiguration(locations = {"classpath:context.xml", "classpath:contexts/mock.xml"})
public class OrderRepositoryTest extends AbstractTransactionalJUnit4SpringContextTests {
    private static final Logger logger = LoggerFactory.getLogger(OrderRepositoryTest.class);
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    SessionFactory sessionFactory;
    private CustomerInfo customerInfo;

    @Before
    public void setUp() throws Exception {
        customerInfo = new CustomerInfo("91211212121", "a@gmail.com");
    }

    //todo exception translation does not work!
    @Test(expected = ConstraintViolationException.class)
    public void findByOrderId_should_not_allow_duplicate_order() {
        orderRepository.store(new Order("duplicateOrderId", customerInfo));
        orderRepository.store(new Order("duplicateOrderId", customerInfo));
        sessionFactory.getCurrentSession().flush();
    }

    //region findByOrderId tests
    @Test(expected = NotFoundException.class)
    public void findByOrderId_should_throw_NotFound_exception_if_there_is_no_order_with_this_orderId() throws Exception {
        orderRepository.findByOrderId("orderId");
    }

    @Test()
    public void findByOrderId_should_find_available_order() throws Exception {
        final Order found = orderRepository.findByOrderId("ORD-100");
        assertNotNull(found);
    }

    //endregion

    //region findOrdersByStatus tests
    @Test
    public void findByStatus_should_return_all_POSTPONED_orders() {
        final List<Order> orders = orderRepository.findByStatus(Order.Status.POSTPONED);
        assertEquals(0, orders.size());
    }

    @Test
    public void findByStatus_should_return_all_MODIFIABLE_orders() {
        final List<Order> orders = orderRepository.findByStatus(Order.Status.MODIFIABLE);
        assertEquals(1, orders.size());
    }
    //endregion
    @Test
    public void findOrderContainingProductSpec_should_return_order_with_the_spec(){
        assertEquals(1, orderRepository.findOrderContainingSimilarSpec("DepositCharge", DepositParams.createEqualCriteria(DepositParams.CREDIT_NUMBER_KEY,"1212")).size());
        assertEquals(1, orderRepository.findOrderContainingSimilarSpec("DepositCharge", DepositParams.createEqualCriteria(DepositParams.AMOUNT_KEY,"10")).size());
        assertEquals(0, orderRepository.findOrderContainingSimilarSpec("DepositCharge", "alaki").size());
    }
    @Test
    public void findSuccessfulOrdersContainingProduct_should_return_correctOrders() {
        final Calendar instance = Calendar.getInstance();
        instance.set(1970,Calendar.APRIL,1);
        final Date from = instance.getTime();
        instance.set(2020,Calendar.APRIL,1);
        final Date to = instance.getTime();
        List<Order> orders = orderRepository.findOrdersContainingProduct("DepositCharge", DepositParams.createEqualCriteria(DepositParams.CREDIT_NUMBER_KEY, "1122"), from, to);
        assertNotNull(orders);
        assertEquals(1, orders.size());
        assertEquals("c@d.com",orders.get(0).getCustomerInfo().getEmail());
    }
    @Test
    public void findSuccessfulOrdersContainingProduct_should_not_return_orders_which_createdate_is_not_in_range() {
        final Date from = new Date();
        final Date to = new Date();
        List<Order> orders = orderRepository.findOrdersContainingProduct("DepositCharge", DepositParams.createEqualCriteria(DepositParams.CREDIT_NUMBER_KEY,"1122"), from, to);
        assertNotNull(orders);
        assertEquals(0, orders.size());
    }

}
