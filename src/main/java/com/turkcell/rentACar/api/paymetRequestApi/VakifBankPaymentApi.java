package com.turkcell.rentACar.api.paymetRequestApi;

import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class VakifBankPaymentApi {

    public boolean VakifBankPayment(double paymentAmount, String nameOnCard, int cardNumber, int dateOfMont, int dateOfYear, int securityCode) {
        return new Random().nextBoolean();
    }
}
