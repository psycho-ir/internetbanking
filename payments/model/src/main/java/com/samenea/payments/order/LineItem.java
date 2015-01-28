package com.samenea.payments.order;

import com.samenea.commons.component.model.ValueObject;
import org.springframework.util.Assert;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;

/**
 * @author: Jalal Ashrafi
 * Date: 1/27/13
 */
@Embeddable
public class LineItem extends ValueObject {

    @Embedded()
    @Access(AccessType.FIELD)
    private final ProductSpec productSpec;

    private final int price;

    private LineItem() {
        this.productSpec = null;
        this.price = 0;
    }

    public LineItem(ProductSpec spec, int price) {
        Assert.notNull(spec,"null product spec is not allowed");
        this.productSpec = spec;
        this.price = price;
    }

    public int getPrice() {
        return price;
    }

    public ProductSpec getProductSpec() {
        return productSpec;
    }

    @Override
    public String toString() {
        return "LineItem{" +
                "productSpec=" + productSpec.toString() +
                ", price=" + price +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LineItem lineItem = (LineItem) o;

        if (price != lineItem.price) return false;
        if (!productSpec.equals(lineItem.productSpec)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = productSpec.hashCode();
        result = 31 * result + price;
        return result;
    }
}
