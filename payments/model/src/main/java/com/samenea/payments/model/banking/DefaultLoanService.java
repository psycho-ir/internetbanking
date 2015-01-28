package com.samenea.payments.model.banking;

import com.samenea.banking.deposit.ChargeException;
import com.samenea.banking.loan.ILoanService;
import com.samenea.commons.component.model.BasicRepository;
import com.samenea.payments.model.DeliveryException;
import com.samenea.payments.model.loan.InstallmentParams;
import com.samenea.payments.order.delivery.Delivery;
import com.samenea.payments.order.delivery.DeliveryService;
import com.samenea.payments.order.ProductSpec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author: Jalal Ashrafi
 * Date: 1/27/13
 */
@Configurable(dependencyCheck = true)
@Component
public class DefaultLoanService implements LoanService, DeliveryService {
    @Autowired
    ILoanService loanService;

    @Value("${banking.default.debitNumber}")
    private String defaultDebitNumber;
    @Value("${banking.depositUserId}")
    private String userId;
    @Autowired
    BasicRepository<Delivery, Long> productRepository;

    @Override
    public void probePayInstallment(String loanNumber, String installmentNumber, long amount) throws ChargeException {
        loanService.checkPaymentFeasibility(userId, defaultDebitNumber, loanNumber, installmentNumber, amount);
    }

    @Override
    public void payInstallment(String loanNumber, String installmentNumber, long amount,String debitNumber) {
        loanService.payInstallment(userId, debitNumber, loanNumber, installmentNumber, amount);
    }

    @Override
    public Delivery deliver(ProductSpec productSpec, String orderId, String transactionId,String debitNumber) {
//        Assert.hasText(orderId,"OrderId should not be null or empty");
//        Assert.hasText(transactionId, "transactionId should not be null or empty");
        final InstallmentParams installmentParams = InstallmentParams.fromProductSpec(productSpec);
        try {
            final String bankingTransactionId = loanService.payInstallment(userId, debitNumber,
                    installmentParams.getLoanNumber(), installmentParams.getInstallmentNumber(),
                    installmentParams.getAmount());
            return productRepository.store(new Delivery(orderId, productSpec, bankingTransactionId));
        } catch (Exception e) {
            throw new DeliveryException("Can not pay installment due to exception", e);
        }
    }
}
