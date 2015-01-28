package com.samenea.payments.model.banking;

import com.samenea.banking.deposit.ChargeException;
import com.samenea.banking.deposit.IDepositService;
import com.samenea.commons.component.model.BasicRepository;
import com.samenea.commons.component.utils.log.LoggerFactory;
import com.samenea.payments.model.DeliveryException;
import com.samenea.payments.model.DepositParams;
import com.samenea.payments.order.delivery.Delivery;
import com.samenea.payments.order.delivery.DeliveryService;
import com.samenea.payments.order.ProductSpec;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

/**
 * @author: Jalal Ashrafi
 * Date: 1/27/13
 */
@Configurable(dependencyCheck = true)
@Component
public class DefaultDepositService implements DepositChargeService,DeliveryService {
    @Autowired
    BasicRepository<Delivery,Long> productRepository;
    @Autowired
    IDepositService depositService;
    @Value("${banking.default.debitNumber}")
    private String defaultDebitNumber;
    @Value("${banking.depositDescriptionTemplate}")
    private String descritptionTemplate;
    @Value("${banking.depositUserId}")
    private String userId;
    @Value("${banking.debitBranchCode}")
    private String debitBranchCode;
    private static final Logger logger = LoggerFactory.getLogger(DefaultDepositService.class);

    @Transactional(rollbackFor = Exception.class)
    public void chargeDeposit(String depositNumber, int amount,String debitNumber) throws ChargeException {
        depositService.chargeDeposit(amount,debitNumber, depositNumber,"No desc",userId,debitBranchCode);
    }

    @Override
    public void probeChargeDeposit(Integer amount, String depositNumber) throws ChargeException {
        //we have not transactionId here thus just a fake transactionId is added for checking description length for example
        final String fakeDescription = createDescription("fakeTransactionId");
        logger.info("************ before check charging feasibility! ");
        depositService.checkChargingFeasibility(amount,defaultDebitNumber,depositNumber, fakeDescription,userId,debitBranchCode);
    }


    @Transactional( )
    @Override
    public Delivery deliver(ProductSpec productSpec, String orderId, String transactionId,String debitNumber) {
        Assert.hasText(orderId,"OrderId should not be null or empty");
        final DepositParams depositParams = DepositParams.fromCriteriaString(productSpec.getCriteria());
        try {
            final String bankingTransactionId = depositService.chargeDeposit(depositParams.getAmount(), debitNumber, depositParams.getDepositNumber(), createDescription(transactionId), userId, debitBranchCode);
            final Delivery delivery = new Delivery(orderId, productSpec, bankingTransactionId);
            return productRepository.store(delivery);
        } catch (ChargeException e) {
            throw new DeliveryException(e);
        }
    }

    private String createDescription(String transactionId) {
        return String.format(descritptionTemplate, transactionId);
    }


}
