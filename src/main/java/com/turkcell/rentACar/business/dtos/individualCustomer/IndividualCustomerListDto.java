package com.turkcell.rentACar.business.dtos.individualCustomer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class IndividualCustomerListDto {

    private int customerId;
    private String nationalIdentity;
    private String firstName;
    private String lastName;
    private String Email;
}
