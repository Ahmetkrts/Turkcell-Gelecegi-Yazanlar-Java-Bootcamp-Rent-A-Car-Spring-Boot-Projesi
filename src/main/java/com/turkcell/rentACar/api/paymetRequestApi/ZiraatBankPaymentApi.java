package com.turkcell.rentACar.api.paymetRequestApi;

import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class ZiraatBankPaymentApi {

    public boolean ziraatPayment(String holderName, String cardNo, LocalDate expiryDate, String securityCode, double paymentAmount) {
        return true;
    }

}
