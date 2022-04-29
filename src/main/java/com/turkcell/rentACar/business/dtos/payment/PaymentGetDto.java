package com.turkcell.rentACar.business.dtos.payment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentGetDto {

    private int paymentId;
    private double totalPaymentAmount;


}
