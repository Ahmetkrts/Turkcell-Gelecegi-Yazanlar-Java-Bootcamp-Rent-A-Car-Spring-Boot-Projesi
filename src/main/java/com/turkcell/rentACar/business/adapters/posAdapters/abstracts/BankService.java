package com.turkcell.rentACar.business.adapters.posAdapters.abstracts;

import com.turkcell.rentACar.business.request.creditCard.CreateCreditCardForPaymentRequest;
import com.turkcell.rentACar.core.exception.BusinessException;

public interface BankService {

    //result mimarisi
    boolean payment(CreateCreditCardForPaymentRequest createCreditCardForPaymentRequest, double totalFee) throws BusinessException;

}
