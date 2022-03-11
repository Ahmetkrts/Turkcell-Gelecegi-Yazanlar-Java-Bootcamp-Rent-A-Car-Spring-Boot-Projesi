package com.turkcell.rentACar.business.abstracts;

import com.turkcell.rentACar.business.dtos.CityGetDto;
import com.turkcell.rentACar.business.dtos.CityListDto;
import com.turkcell.rentACar.business.request.CreateCityRequest;
import com.turkcell.rentACar.business.request.DeleteCityRequest;
import com.turkcell.rentACar.business.request.UpdateCityRequest;
import com.turkcell.rentACar.core.exception.BusinessException;
import com.turkcell.rentACar.core.result.DataResult;
import com.turkcell.rentACar.core.result.Result;

import java.util.List;

public interface CityService {

    Result add(CreateCityRequest createCityRequest) throws BusinessException;

    DataResult<List<CityListDto>> getAll();

    DataResult<CityGetDto> getById(int cityId) throws BusinessException;

    Result update(UpdateCityRequest updateCityRequest) throws BusinessException;

    Result delete(DeleteCityRequest deleteCityRequest) throws BusinessException;

}
