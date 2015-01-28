package com.samenea.payments.order;

import com.samenea.commons.component.model.ValueObject;
import org.springframework.util.Assert;

import javax.persistence.*;

/**
 * @author: Jalal Ashrafi
 * Date: 1/27/13
 */
@Embeddable
public class ProductSpec extends ValueObject {
    public static final String EQUAL = "==";
    public static final String AND = " and ";

    @Column(length = 50,nullable = false)
    private final String productName;
    @Column(length = 500,nullable = false)
    private final String criteria;

    //This product is purchased for
    @Embedded()
    @Access(AccessType.FIELD)
    @AttributeOverrides({
            @AttributeOverride(name = "firstName", column = @Column(name = "ownerFirstName")),
            @AttributeOverride(name = "lastName", column = @Column(name = "ownerLastName"))
    })
    private final UserInfo deliveryInfo;

    private ProductSpec() {
        productName = "";
        criteria = "";
        deliveryInfo = null;
    }

    public ProductSpec(String productName, String criteria) {
        Assert.hasText(productName,"Product Name should not be null or empty");
        this.productName = productName;
        this.criteria = criteria;
        deliveryInfo = null;
    }

    public ProductSpec(String productName, String criteriaString, UserInfo customerInfo) {
        Assert.hasText(productName,"Product Name should not be null or empty");
        this.productName = productName;
        this.criteria = criteriaString;
        this.deliveryInfo = customerInfo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProductSpec that = (ProductSpec) o;

        if (!criteria.equals(that.criteria)) return false;
        if (!productName.equals(that.productName)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = productName.hashCode();
        result = 31 * result + criteria.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "ProductSpec{" +
                "productionName='" + productName + '\'' +
                ", criteria='" + criteria + '\'' +
                '}';
    }

    public UserInfo getDeliveryInfo() {
        return deliveryInfo;
    }

    public String getCriteria() {
        return criteria;
    }

    public String getProductName() {
        return productName;
    }
}
