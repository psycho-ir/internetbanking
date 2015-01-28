package com.samenea.payments.model.banking;

import com.samenea.banking.deposit.ChargeException;

/**
 * @author: Jalal Ashrafi
 * Date: 1/27/13
 */
public interface DepositChargeService {
    /**
     * @param depositNumber
     * @param amount
     * @throws ChargeException
     */
    void chargeDeposit(String depositNumber, int amount, String debitNumber) throws ChargeException;

    /**
     * This method can be used to examine if chargeDeposit is possible
     * It is a simulation of chargeDeposit except that its effect will be rolled back
     *
     * @param amount        amount to charge
     * @param depositNumber deposit number of deposit account to be charged
     * @throws ChargeException if charge can not be done
     *                         In the case of any other exception also account can not be charged
     */
    void probeChargeDeposit(Integer amount, String depositNumber) throws ChargeException;
}
