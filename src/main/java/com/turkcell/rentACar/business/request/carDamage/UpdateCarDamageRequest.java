package com.turkcell.rentACar.business.request.carDamage;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCarDamageRequest {

    private int carDamageId;
    private String damageInfo;
    private int carId;
}
