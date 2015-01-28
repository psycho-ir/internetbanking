package com.samenea.payments.order.delivery;

import com.samenea.commons.component.utils.Environment;
import com.samenea.commons.component.utils.log.LoggerFactory;
import com.samenea.payments.order.ProductSpec;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.util.Assert;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Transient;
import java.util.Date;

/**
 * This is a delivery record
 * @author: Jalal Ashrafi
 * Date: 4/20/13
 */
@Configurable(dependencyCheck = true,preConstruction = true)
@Entity
public class Delivery extends com.samenea.commons.component.model.Entity<Long> {
    private static final Logger logger = LoggerFactory.getLogger(Delivery.class);
    @Embedded()
    private final ProductSpec productSpec;
    @Column(nullable = false)
    private final String orderId;
    @Column(nullable = false,unique = true)
    private final String serialNumber;
    @Column(nullable = false)
    private final Date creationTime;
    @Transient
    @Autowired
    private Environment environment;


    private Delivery() {
        serialNumber = null;
        orderId = null;
        productSpec = null;
        creationTime = null;
    }

    public Delivery(String orderId, ProductSpec productSpec, String serialNumber) {
        Assert.hasText(orderId,"Order Id should not be null or empty");
        Assert.hasText(serialNumber,"serial number should not be null or empty");
        Assert.notNull(productSpec,"productSpec should not be null");
        this.orderId = orderId;
        this.productSpec = productSpec;
        this.serialNumber = serialNumber;
        creationTime = environment.getCurrentDate();
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public ProductSpec getProductSpec() {
        return productSpec;
    }

    public String getOrderId() {
        return orderId;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Delivery delivery = (Delivery) o;

        if (!serialNumber.equals(delivery.serialNumber)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return serialNumber.hashCode();
    }

    @Override
    public String toString() {
        return "Delivery{" +
                "productSpec=" + productSpec.toString() +
                ", orderId='" + orderId + '\'' +
                ", serialNumber='" + serialNumber + '\'' +
                ", creationTime=" + creationTime +
                '}';
    }
}
