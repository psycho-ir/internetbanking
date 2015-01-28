package com.samenea.payments.model.banking;

/**
 * @author: Jalal Ashrafi
 * Date: 2/13/13
 */
public interface LoanService {
    void payInstallment(String loanNumber, String installmentNumber, long amount, String debitNumber);

    void probePayInstallment(String loanNumber, String installmentNumber, long amount);
}
