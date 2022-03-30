package com.turkcell.rentACar.business.request.payment;

import com.turkcell.rentACar.business.request.creditCard.CreateCreditCardForPaymentRequest;
import com.turkcell.rentACar.business.request.rentCar.CreateRentCarRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreatePaymentRequest {

    private CreateRentCarRequest rentCarRequest;

    private CreateCreditCardForPaymentRequest createCreditCardForPaymentRequest;

    private List<Integer> additionalList;
    private boolean saveCreditCard;


}
