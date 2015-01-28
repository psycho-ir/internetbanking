package com.samenea.payments.model.loan;

import com.samenea.commons.component.utils.log.LoggerFactory;
import com.samenea.payments.order.Order;
import com.samenea.payments.order.ProductSpec;
import org.slf4j.Logger;

/**
 * @author: Jalal Ashrafi
 * Date: 2/12/13
 */
public interface LoanOrderService {
    /**
     *
     * @param loanId
     * @return
     * @throws com.samenea.payments.model.LimitExceededException if number of installment payed for this loan is already at limit per day
     */
    ProductSpec createInstallmentPaySpec(String loanId);
}
