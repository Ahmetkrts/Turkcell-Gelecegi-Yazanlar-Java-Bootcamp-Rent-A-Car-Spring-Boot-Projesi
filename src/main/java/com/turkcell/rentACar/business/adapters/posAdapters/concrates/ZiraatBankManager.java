package com.turkcell.rentACar.business.adapters.posAdapters.concrates;

import com.turkcell.rentACar.api.paymetRequestApi.ZiraatBankPaymentApi;
import com.turkcell.rentACar.business.adapters.posAdapters.abstracts.BankService;
import com.turkcell.rentACar.business.request.creditCard.CreateCreditCardForPaymentRequest;
import com.turkcell.rentACar.core.exception.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ZiraatBankManager implements BankService {

    private ZiraatBankPaymentApi ziraatBankPaymentApi;

    @Autowired
    public ZiraatBankManager(ZiraatBankPaymentApi ziraatBankPaymentApi) {
        this.ziraatBankPaymentApi = ziraatBankPaymentApi;
    }


    public boolean payment(CreateCreditCardForPaymentRequest createCreditCardForPaymentRequest, double totalFee) throws BusinessException {

        return ziraatBankPaymentApi.ziraatPayment(createCreditCardForPaymentRequest.getHolderName(), createCreditCardForPaymentRequest.getCardNo(), createCreditCardForPaymentRequest.getExpiryDate(), createCreditCardForPaymentRequest.getSecurityCode(), totalFee);
    }


}
