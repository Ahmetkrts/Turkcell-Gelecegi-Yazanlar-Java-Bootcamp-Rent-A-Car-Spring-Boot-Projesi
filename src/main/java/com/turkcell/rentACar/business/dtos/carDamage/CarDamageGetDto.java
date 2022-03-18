package com.turkcell.rentACar.business.dtos.carDamage;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarDamageGetDto {

    private int carDamageId;
    private String damageInfo;
    private String description;
}
