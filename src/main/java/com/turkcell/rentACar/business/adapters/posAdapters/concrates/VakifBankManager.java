package com.turkcell.rentACar.business.adapters.posAdapters.concrates;

import com.turkcell.rentACar.api.paymetRequestApi.VakifBankPaymentApi;
import com.turkcell.rentACar.business.request.bankService.CreateCreditCardForPaymentRequest;
import com.turkcell.rentACar.core.exception.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VakifBankManager {

    VakifBankPaymentApi vakifBankPaymentApi;

    @Autowired
    public VakifBankManager(VakifBankPaymentApi vakifBankPaymentApi) {
        this.vakifBankPaymentApi = vakifBankPaymentApi;
    }


    public boolean payment(CreateCreditCardForPaymentRequest createCreditCardForPaymentRequest, double totalFee) throws BusinessException {
        return vakifBankPaymentApi.VakifBankPayment(createCreditCardForPaymentRequest.getTotalAmount(), createCreditCardForPaymentRequest.getCardHolder(),
                createCreditCardForPaymentRequest.getCardNumber(), createCreditCardForPaymentRequest.getDayOfDate(), createCreditCardForPaymentRequest.getYearOfDate(), createCreditCardForPaymentRequest.getCvv());
    }
}
