package com.samenea.payments.model;

import com.samenea.payments.order.ProductSpec;
import com.samenea.payments.order.UserInfo;
import org.springframework.util.Assert;

import java.util.Map;

/**
 * @author: Jalal Ashrafi
 * Date: 1/28/13
 */
public class DepositParams {

    public static final String CREDIT_NUMBER_KEY = "depositNumber";
    public static final String AMOUNT_KEY = "amount";
    private final String debitNumber;
    private final int amount;
    public static final String PARAM_TEMPLATE="%s == %s";

    /**
     * @see com.samenea.payments.order.ProductSpec#getProductName()
     */
    public static final String DEPOSIT_CHARGE_PRODUCT_NAME = "DepositCharge";

    private DepositParams(String criteria) {
        final Map<String, String> keyValueMap = Util.getKeyValueMap(criteria);
        Assert.isTrue(keyValueMap.size() == 2 && keyValueMap.containsKey(DepositParams.CREDIT_NUMBER_KEY) &&
                keyValueMap.containsKey(DepositParams.AMOUNT_KEY), String.format("criteria '%s' is not valid ", criteria));
        this.debitNumber = keyValueMap.get(CREDIT_NUMBER_KEY);
        this.amount = Integer.parseInt(keyValueMap.get(AMOUNT_KEY));
    }

    public DepositParams(String depositNumber, int amount) {
        this.debitNumber = depositNumber;
        this.amount = amount;
    }

    public String getDepositNumber() {
        return debitNumber;
    }

    public int getAmount() {
        return amount;
    }

    public ProductSpec getProductSpec() {
        final String criteriaString = String.format(PARAM_TEMPLATE + " and " + PARAM_TEMPLATE,
                DepositParams.CREDIT_NUMBER_KEY, debitNumber, DepositParams.AMOUNT_KEY, amount);
        return new ProductSpec(DEPOSIT_CHARGE_PRODUCT_NAME, criteriaString);
    }

    public static DepositParams fromCriteriaString(String criteria) {
        return new DepositParams(criteria);
    }

    public static DepositParams fromProductSpec(ProductSpec productSpec) {
        return new DepositParams(productSpec.getCriteria());
    }

    public static DepositParams fromParams(String depositNumber, int amount) {
        return new DepositParams(depositNumber, amount);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DepositParams that = (DepositParams) o;

        if (amount != that.amount) return false;
        if (!debitNumber.equals(that.debitNumber)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = debitNumber.hashCode();
        result = 31 * result + amount;
        return result;
    }

    @Override
    public String toString() {
        return "DepositParams{" +
                "debitNumber='" + debitNumber + '\'' +
                ", amount=" + amount +
                '}';
    }

    public ProductSpec getProductSpec(UserInfo customerInfo) {
        final String criteriaString = String.format(PARAM_TEMPLATE + " and " + PARAM_TEMPLATE,
                DepositParams.CREDIT_NUMBER_KEY, debitNumber, DepositParams.AMOUNT_KEY, amount);
        return new ProductSpec(DEPOSIT_CHARGE_PRODUCT_NAME, criteriaString, customerInfo);
    }

    public static String createEqualCriteria(String paramName, String value) {
        return String.format(PARAM_TEMPLATE,paramName,value);
    }
}
