package com.turkcell.rentACar.business.dtos.corporateCustomer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CorporateCustomerListDto {

    private int customerId;
    private String taxNumber;
    private String companyName;
    private String Email;

}
