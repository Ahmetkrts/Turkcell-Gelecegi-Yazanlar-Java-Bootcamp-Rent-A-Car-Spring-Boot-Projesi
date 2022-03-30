package com.turkcell.rentACar.business.request.payment;

import com.turkcell.rentACar.business.request.creditCard.CreateCreditCardForPaymentRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateExtraPaymentRequest {

    private int rentCarId;
    private int customerId;
    private int carId;
    private LocalDate dateOfReceipt;
    private LocalDate carReturnDate;
    private CreateCreditCardForPaymentRequest createCreditCardForPaymentRequest;


}
