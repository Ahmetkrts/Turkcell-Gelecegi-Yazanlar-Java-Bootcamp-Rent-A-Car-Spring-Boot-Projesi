package com.turkcell.rentACar.business.request.corporateCustomer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCorporateCustomerRequest {

    private int customerId;
    @NotNull
    private String taxNumber;
    @NotNull
    private String companyName;
    @NotNull
    @Email
    private String Email;
    @NotNull
    private String password;


}
