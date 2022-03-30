package com.turkcell.rentACar.business.request.payment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdatePaymentRequest {

    private int paymentId;

    @NotNull
    @Min(0)
    private double totalPaymentAmount;
    @NotNull
    private int invoiceId;


}
