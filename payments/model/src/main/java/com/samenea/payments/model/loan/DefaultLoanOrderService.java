package com.samenea.payments.model.loan;

import com.samenea.banking.customer.ICustomer;
import com.samenea.banking.loan.IInstallment;
import com.samenea.banking.loan.ILoan;
import com.samenea.banking.loan.ILoanService;
import com.samenea.commons.component.utils.log.LoggerFactory;
import com.samenea.payments.model.LimitExceededException;
import com.samenea.payments.model.banking.LoanService;
import com.samenea.payments.order.*;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.samenea.payments.order.Order.Status.CHECKED_OUT;
import static com.samenea.payments.order.Order.Status.DELIVERED;
import static com.samenea.payments.order.Order.Status.POSTPONED;

/**
 * @author: Jalal Ashrafi
 * Date: 2/12/13
 */

@Configurable
@Component
public class DefaultLoanOrderService implements LoanOrderService {
    private static final Logger logger = LoggerFactory.getLogger(DefaultLoanOrderService.class);
    private Logger exceptionLogger = LoggerFactory.getLogger(DefaultLoanOrderService.class, LoggerFactory.LoggerType.EXCEPTION);
    @Autowired
    LoanService loanService;
    @Autowired
    ILoanService bankingLoanService;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    OrderResolver orderResolverService;
    @Autowired
    @Value("${loan.maximumNumberOfInstallmentPayInEachDay}")
    private Integer maximumNumberOfInstallmentPayInEachDay;

    @Override
    public ProductSpec createInstallmentPaySpec(String loanNumber) {

        final ILoan loan = bankingLoanService.findLoan(loanNumber);
        if (numberOfInstallmentPaysToday(loanNumber) >= maximumNumberOfInstallmentPayInEachDay) {
            throw new LimitExceededException("Maximum number of installment pay per day is: " + maximumNumberOfInstallmentPayInEachDay, maximumNumberOfInstallmentPayInEachDay);
        }
        final IInstallment payableInstallment = loan.getPayableInstallment();
        loanService.probePayInstallment(loanNumber, payableInstallment.getInstallmentNumber(), payableInstallment.getPayableAmount());
        final ICustomer borrower = bankingLoanService.findCustomer(loanNumber);
        final InstallmentParams installmentParams = InstallmentParams.fromParams(loanNumber, payableInstallment.getInstallmentNumber(), payableInstallment.getPayableAmount());
        resolvePendingLoanPaymentIfAny(loanNumber, payableInstallment, installmentParams);
        return installmentParams.getProductSpec(new UserInfo(borrower.getName(),borrower.getLastName()));
    }

    private void resolvePendingLoanPaymentIfAny(String loanNumber, IInstallment payableInstallment, InstallmentParams installmentParams) {
        final String loanNumberCondition = installmentParams.createParamEqualityCriteria(InstallmentParams.LOAN_NUMBER_KEY, loanNumber);
        final List<Order> ordersForThisLoan = orderRepository.findOrderContainingSimilarSpec(installmentParams.getProductSpec().getProductName(), loanNumberCondition);
        for (Order order : ordersForThisLoan) {
            if(isResolveNeeded(order)){
                logger.debug("There is an unresolved order for this loan and installment " +
                        "thus we should resolve it first. loan : (loan number: {}- installment number: {}). order is: {}",
                        loanNumber,payableInstallment.getInstallmentNumber(),order.toString());
                orderResolverService.resolve(order);
                //          will cause exception if prev order results to paying installment
                loanService.probePayInstallment(loanNumber, payableInstallment.getInstallmentNumber(), payableInstallment.getPayableAmount());
            }
        }
    }

    private boolean isResolveNeeded(Order order) {
        return order.getStatus() == Order.Status.POSTPONED || (order.getStatus() == Order.Status.CHECKED_OUT && !StringUtils.isBlank(order.getAssignedTransactionId()));
    }

    private Integer numberOfInstallmentPaysToday(String loanNumber) {
        final Calendar instance = Calendar.getInstance();
        instance.set(Calendar.HOUR_OF_DAY,0);
        instance.set(Calendar.MINUTE,0);
        instance.set(Calendar.SECOND,0);
        instance.set(Calendar.MILLISECOND,0);
        Date from = instance.getTime();
        instance.add(Calendar.DAY_OF_MONTH,1);
        Date to = instance.getTime();
        final List<Order> ordersContainingProduct = orderRepository.findOrdersContainingProduct(InstallmentParams.INSTALLMENT_PAY_PRODUCT_NAME, InstallmentParams.createParamEqualityCriteria(InstallmentParams.LOAN_NUMBER_KEY, loanNumber), from, to);
        Integer todayTotalDeposits = 0;

        for (Order order : ordersContainingProduct) {
            if (order.getStatus() == DELIVERED || order.getStatus() == CHECKED_OUT || order.getStatus() == POSTPONED) {
                for (LineItem lineItem : order.getLineItems()) {
                    if (lineItem.getProductSpec().getProductName().equals(InstallmentParams.INSTALLMENT_PAY_PRODUCT_NAME)) {
                        todayTotalDeposits++;
                    }
                }

            }
        }
        return todayTotalDeposits;
    }


}
