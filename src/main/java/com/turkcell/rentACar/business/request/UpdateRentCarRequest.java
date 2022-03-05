package com.turkcell.rentACar.business.request;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateRentCarRequest {


    private int rentCarId;
    @NotNull
    private Date dateOfIssue;
    private Date dateOfReceipt;
    @NotNull
    private String rentFirstName;
    private String rentLastName;
    private int carId;


}
