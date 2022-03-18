package com.turkcell.rentACar.business.request.corporateCustomer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateCorporateCustomerRequest {

    @NotNull
    private String taxNumber;
    @NotNull
    private LocalDate customer_CreateDate;
    @NotNull
    private String companyName;

    @NotNull
    @Email
    private String Email;
    @NotNull
    private String password;


}
