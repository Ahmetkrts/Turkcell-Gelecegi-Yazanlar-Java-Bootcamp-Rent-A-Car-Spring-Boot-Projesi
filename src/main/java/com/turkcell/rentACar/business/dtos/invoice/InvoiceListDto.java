package com.turkcell.rentACar.business.dtos.invoice;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceListDto {

    private int invoiceId;
    private LocalDate createDate;
    private double totalPrice;
    private int customer_CustomerId;
    private int rentCar_RentCarId;
    private LocalDate rentCar_RentCarDateOfIssue;
    private LocalDate rentCar_RentCarDateOfReceipt;


}
