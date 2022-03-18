package com.turkcell.rentACar.business.request.carDamage;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeleteCarDamageRequest {

    private int carDamageId;
}
