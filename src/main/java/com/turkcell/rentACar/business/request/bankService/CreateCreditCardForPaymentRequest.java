package com.turkcell.rentACar.business.request.bankService;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateCreditCardForPaymentRequest {

    @NotNull
    private String cardHolder;
    @Size(min = 16, max = 16)
    @NotNull
    private int cardNumber;
    @NotNull
    @Max(31)
    @Min(1)
    private int dayOfDate;
    @NotNull
    @Size(max = 4, min = 4)
    private int yearOfDate;
    @NotNull
    @Min(3)
    @Max(3)
    private int cvv;
    @NotNull
    private double totalAmount;
}
