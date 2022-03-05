package com.turkcell.rentACar.business.dtos;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RentCarListDto {


    private int rentCarId;
    private Date dateOfIssue;
    private Date dateOfReceipt;
    private String rentFirstName;
    private String rentLastName;
    private String brandName;


}
