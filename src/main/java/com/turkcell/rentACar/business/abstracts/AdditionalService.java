package com.turkcell.rentACar.business.abstracts;

import com.turkcell.rentACar.business.dtos.AdditionalGetDto;
import com.turkcell.rentACar.business.dtos.AdditionalListDto;
import com.turkcell.rentACar.business.request.CreateAdditionalRequest;
import com.turkcell.rentACar.business.request.DeleteAdditionalRequest;
import com.turkcell.rentACar.business.request.UpdateAdditionalRequest;
import com.turkcell.rentACar.core.exception.BusinessException;
import com.turkcell.rentACar.core.result.DataResult;
import com.turkcell.rentACar.core.result.Result;

import java.util.List;

public interface AdditionalService {


    Result add(CreateAdditionalRequest createAdditionalRequest) throws BusinessException;

    Result update(UpdateAdditionalRequest updateAdditionalRequest) throws BusinessException;

    Result delete(DeleteAdditionalRequest deleteAdditionalRequest) throws BusinessException;

    DataResult<List<AdditionalListDto>> getAll();

    DataResult<AdditionalGetDto> getById(int additionalId) throws BusinessException;

    void checkIfAdditionIdExists(int addditionalId) throws BusinessException;

    double totalAdditionalFeeCalculator(List<Integer> additionalList);
}
