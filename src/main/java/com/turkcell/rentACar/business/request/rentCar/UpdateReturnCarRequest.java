package com.turkcell.rentACar.business.request.rentCar;

import com.turkcell.rentACar.business.request.creditCard.CreateCreditCardForPaymentRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateReturnCarRequest {

    @NotNull
    private int carId;
    @NotNull
    private LocalDate carReturnDate;
    @NotNull
    private double returnDistance;
    private CreateCreditCardForPaymentRequest createCreditCardForPaymentRequest;

}
