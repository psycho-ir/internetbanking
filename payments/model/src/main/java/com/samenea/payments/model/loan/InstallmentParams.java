package com.samenea.payments.model.loan;

import com.samenea.commons.component.utils.log.LoggerFactory;
import com.samenea.payments.model.Util;
import com.samenea.payments.order.ProductSpec;
import com.samenea.payments.order.UserInfo;
import org.slf4j.Logger;
import org.springframework.util.Assert;

import java.util.Map;

/**
 * @author: Jalal Ashrafi
 * Date: 2/13/13
 */
public class InstallmentParams {
    private static final Logger logger = LoggerFactory.getLogger(InstallmentParams.class);

    public static final String LOAN_NUMBER_KEY = "loanNumber";
    public static final String INSTALLMENT_NUMBER_KEY = "installmentNumber";
    public static final String AND = " and ";
    public static final String PARAM_TEMPLATE="%s == %s";
    public static final String CRITERIA_TEMPLATE = PARAM_TEMPLATE  + AND + PARAM_TEMPLATE + AND + PARAM_TEMPLATE;
    private final String loanNumber;
    private final String installmentNumber;
    private static final String AMOUNT_KEY = "amount";
    /**
     * @see com.samenea.payments.order.ProductSpec#getProductName()
     */
    public static final String INSTALLMENT_PAY_PRODUCT_NAME = "InstallmentPay";
    private Long amount;

    private InstallmentParams(ProductSpec spec) {
        Assert.notNull(spec,"Product spec should not be null.");
        if(!INSTALLMENT_PAY_PRODUCT_NAME.equals(spec.getProductName())){
            throw new IllegalArgumentException(String.format("product spec name should be %s but is %s",INSTALLMENT_PAY_PRODUCT_NAME, spec.getProductName()));
        }
        final Map<String,String> keyValueMap = Util.getKeyValueMap(spec.getCriteria());
        Assert.isTrue(keyValueMap.size() == 3 && keyValueMap.containsKey(LOAN_NUMBER_KEY) &&
                keyValueMap.containsKey(INSTALLMENT_NUMBER_KEY), String.format("criteria '%s' is not valid ", spec));
        this.loanNumber = keyValueMap.get(LOAN_NUMBER_KEY);
        this.installmentNumber = keyValueMap.get(INSTALLMENT_NUMBER_KEY);
        this.amount = Long.valueOf(keyValueMap.get(AMOUNT_KEY));
    }

    public InstallmentParams(String loanNumber, String installmentNumber, Long amount) {
        this.loanNumber = loanNumber;
        this.installmentNumber = installmentNumber;
        this.amount = amount;
    }

    public static InstallmentParams fromProductSpec(ProductSpec criteria) {
        return new InstallmentParams(criteria);
    }

    public String getLoanNumber() {
        return loanNumber;
    }

    public String getInstallmentNumber() {
        return installmentNumber;
    }

    public ProductSpec getProductSpec(){
        final String criteria = String.format(CRITERIA_TEMPLATE,
                LOAN_NUMBER_KEY, loanNumber, INSTALLMENT_NUMBER_KEY, installmentNumber, AMOUNT_KEY, amount);
        return new ProductSpec(INSTALLMENT_PAY_PRODUCT_NAME, criteria);
    }

    public static InstallmentParams fromParams(String loanId, String installmentId,long amount) {
        return new InstallmentParams(loanId,installmentId,amount);
    }

    public Long getAmount() {
        return amount;
    }

    public ProductSpec getProductSpec(UserInfo customerInfo) {
        final String criteria = String.format(CRITERIA_TEMPLATE,
                LOAN_NUMBER_KEY, loanNumber, INSTALLMENT_NUMBER_KEY, installmentNumber, AMOUNT_KEY, amount);
        return new ProductSpec(INSTALLMENT_PAY_PRODUCT_NAME, criteria, customerInfo);
    }
    public static String createParamEqualityCriteria(String paramName,String value){
        return String.format(PARAM_TEMPLATE,paramName,value);
    }
}
