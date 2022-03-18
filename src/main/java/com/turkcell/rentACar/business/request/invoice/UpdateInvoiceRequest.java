package com.turkcell.rentACar.business.request.invoice;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateInvoiceRequest {

    private int invoiceId;
    @NotNull
    private LocalDate createDate;
    @NotNull
    @Min(0)
    private double totalPrice;
    @NotNull
    private int customer_UserId;
    @NotNull
    private int rentCarId;


}
