package com.turkcell.rentACar.business.request.city;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCityRequest {
    private int cityId;
    @NotNull
    private String name;
}
