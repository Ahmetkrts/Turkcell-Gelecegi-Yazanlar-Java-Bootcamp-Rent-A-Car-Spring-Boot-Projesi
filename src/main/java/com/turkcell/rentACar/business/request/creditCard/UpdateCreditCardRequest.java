package com.turkcell.rentACar.business.request.creditCard;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCreditCardRequest {


    private int creditCardId;
    @NotNull
    private String creditCardTitle;
    @NotNull
    private String holderName;
    @NotNull
    @Size(min = 16, max = 16)
    private String cardNo;
    @NotNull
    private LocalDate expiryDate;
    @NotNull
    @Size(min = 3, max = 3)
    private String securityCode;
    @NotNull
    private int customer_UserId;
}
