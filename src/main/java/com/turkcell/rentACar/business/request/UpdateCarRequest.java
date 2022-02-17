package com.turkcell.rentACar.business.request;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCarRequest {
    private int carId;
    private int dailyPrice;
    private int modelYear;
    private String description;
    private int brandId;
    private int colorId;
}
