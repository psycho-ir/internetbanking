package com.samenea.payments.order.delivery;

import com.samenea.commons.component.utils.log.LoggerFactory;
import com.samenea.payments.model.DepositParams;
import com.samenea.payments.model.loan.InstallmentParams;
import com.samenea.payments.order.ProductSpec;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * a dispatcher which based on {@link com.samenea.payments.order.ProductSpec#getProductName} calls appropriate service
 *
 * @author: Jalal Ashrafi
 * Date: 2/16/13
 * @see com.samenea.payments.model.banking.DefaultLoanService
 * @see com.samenea.payments.model.banking.DefaultDepositService
 */
@Component
@Configurable
public class DeliveryDispatcher implements DeliveryService {
    private static final Logger logger = LoggerFactory.getLogger(DeliveryDispatcher.class);
    @Qualifier(value = "defaultDepositService")
    @Autowired
    DeliveryService depositService;

    @Qualifier(value = "defaultLoanService")
    @Autowired
    DeliveryService loanService;

    @Override
    public Delivery deliver(ProductSpec productSpec, String orderId, String transactionId, String debitNumber) {
        //todo a better approach should be used if new services are added
        if (productSpec.getProductName().equals(DepositParams.DEPOSIT_CHARGE_PRODUCT_NAME)) {
            depositService.deliver(productSpec, orderId, transactionId, debitNumber);
        } else if (productSpec.getProductName().equals(InstallmentParams.INSTALLMENT_PAY_PRODUCT_NAME)) {
            loanService.deliver(productSpec, orderId, transactionId, debitNumber);
        } else {
            throw new IllegalStateException(String.format("This product: '%s', is not supported. may be there is a bug!", productSpec.getProductName()));
        }
        return null;
    }
}
