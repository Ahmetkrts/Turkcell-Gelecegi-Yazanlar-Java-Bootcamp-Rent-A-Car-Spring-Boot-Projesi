package com.turkcell.rentACar.business.dtos.rentCar;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RentCarGetDto {


    private int rentCarId;
    private LocalDate dateOfIssue;
    private LocalDate dateOfReceipt;
    private LocalDate carReturnDate;
    private double startDistance;
    private double returnDistance;
    private String brandName;
    private String description;
    private String fromCityName;
    private String toCityName;

}
