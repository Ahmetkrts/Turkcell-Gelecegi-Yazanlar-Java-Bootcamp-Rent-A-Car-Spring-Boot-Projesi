package com.turkcell.rentACar.business.request.additional;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeleteAdditionalRequest {

    private int additionalId;
}
