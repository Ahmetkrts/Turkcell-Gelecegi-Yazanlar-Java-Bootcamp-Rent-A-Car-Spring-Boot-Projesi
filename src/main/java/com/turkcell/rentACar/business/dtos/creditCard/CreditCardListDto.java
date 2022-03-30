package com.turkcell.rentACar.business.dtos.creditCard;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreditCardListDto {

    private int creditCardId;
    private String creditCardTitle;

    private String holderName;

    private String cardNo;

    private LocalDate expiryDate;

    private String securityCode;

    private int customerId;
}
