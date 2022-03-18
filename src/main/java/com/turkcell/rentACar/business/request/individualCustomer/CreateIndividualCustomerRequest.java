package com.turkcell.rentACar.business.request.individualCustomer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateIndividualCustomerRequest {

    @NotNull
    private String nationalIdentity;
    @NotNull
    private LocalDate customer_CreateDate;
    @NotNull
    private String firstName;
    @NotNull
    private String lastName;
    @NotNull
    @Email
    private String Email;
    @NotNull
    private String password;
}
