package com.samenea.payments.order;

import com.samenea.commons.component.model.exceptions.NotFoundException;
import com.samenea.commons.component.utils.log.LoggerFactory;
import com.samenea.commons.model.repository.BasicRepositoryHibernate;
import org.hibernate.Query;
import org.slf4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.util.Date;
import java.util.List;

/**
 * @author: Jalal Ashrafi
 * Date: 1/28/13
 */
@Repository
public class OrderRepositoryHibernate extends BasicRepositoryHibernate<Order, Long> implements OrderRepository {
    private static final Logger logger = LoggerFactory.getLogger(OrderRepositoryHibernate.class);

    public OrderRepositoryHibernate() {
        super(Order.class);
    }

    @Override
    public Order findByOrderId(String orderId) {
        Assert.hasText(orderId, "orderId should not be null or empty");
        final Query query = getSession().createQuery("from com.samenea.payments.order.Order where orderId = :orderId");
        query.setParameter("orderId", orderId);
        final Order result = (Order) query.uniqueResult();
        if (result == null) {
            throw new NotFoundException("There is no order with orderId: " + orderId);
        }
        return result;

    }

    @Override
    public List<Order> findByStatus(Order.Status status) {
        Assert.notNull(status, "status should not be null.");
        final Query query = getSession().createQuery("from com.samenea.payments.order.Order where status =:status");
        query.setParameter("status", status);
        final List<Order> result = query.list();

        return result;
    }

    @Override
    @SuppressWarnings(value = "unchecked")
    public List<Order> findOrderContainingSimilarSpec(String productName, String criteria) {
        final Query query = getSession().createQuery("SELECT distinct xorder from com.samenea.payments.order.Order xorder join xorder.lineItems item where" +
                " item.productSpec.criteria like :criteria and item.productSpec.productName = :productName");
        query.setParameter("criteria", "%" + criteria + "%");
        query.setParameter("productName", productName );
        return query.list();
    }

    @Override
    @SuppressWarnings(value = "unchecked")
    public List<Order> findOrdersContainingProduct(String productName, String criteria, Date from, Date to) {
        Assert.notNull(from,"from can not be null");
        Assert.notNull(to, "to can not be null");
        final Query query = getSession().createQuery("SELECT distinct xorder from com.samenea.payments.order.Order xorder join xorder.lineItems item where" +
                " item.productSpec.criteria like :criteria and item.productSpec.productName = :productName and xorder.createDate > :from and xorder.createDate < :to");
        query.setParameter("criteria", "%" + criteria + "%");
        query.setParameter("productName", productName );
        query.setParameter("from", from );
        query.setParameter("to", to );
        return query.list();
    }

}
