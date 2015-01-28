package com.samenea.payments.order;


import org.springframework.util.Assert;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * @author: Jalal Ashrafi
 * Date: 1/27/13
 */
@Embeddable
public class Receipt {
    @Column(length = 50)
    private final String receiptId;

    @Column(length = 50)
    private final String paymentSystem;

    private Receipt() {
        paymentSystem = "";
        receiptId = "";
    }

    public Receipt(String receiptId, String paymentSystem) {
        Assert.hasText(receiptId, "receiptId should not be null or empty");
        Assert.hasText(paymentSystem,"paymentSystem Name should not be null or empty");
        this.receiptId = receiptId;
        this.paymentSystem = paymentSystem;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Receipt receipt = (Receipt) o;

        if (!paymentSystem.equals(receipt.paymentSystem)) return false;
        if (!receiptId.equals(receipt.receiptId)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = receiptId.hashCode();
        result = 31 * result + paymentSystem.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Receipt{" +
                "receiptId='" + receiptId + '\'' +
                ", paymentSystem='" + paymentSystem + '\'' +
                '}';
    }

    public String getReceiptId() {
        return receiptId;
    }
    public String getPaymentSystem() {
        return paymentSystem;
    }
}
