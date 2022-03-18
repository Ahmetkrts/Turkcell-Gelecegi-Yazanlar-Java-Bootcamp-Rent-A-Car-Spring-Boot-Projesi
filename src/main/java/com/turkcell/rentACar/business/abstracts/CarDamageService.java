package com.turkcell.rentACar.business.abstracts;

import com.turkcell.rentACar.business.dtos.carDamage.CarDamageGetDto;
import com.turkcell.rentACar.business.dtos.carDamage.CarDamageListDto;
import com.turkcell.rentACar.business.request.carDamage.CreateCarDamageRequest;
import com.turkcell.rentACar.business.request.carDamage.DeleteCarDamageRequest;
import com.turkcell.rentACar.business.request.carDamage.UpdateCarDamageRequest;
import com.turkcell.rentACar.core.exception.BusinessException;
import com.turkcell.rentACar.core.result.DataResult;
import com.turkcell.rentACar.core.result.Result;

import java.util.List;

public interface CarDamageService {

    Result add(CreateCarDamageRequest createCarDamageRequest) throws BusinessException;

    Result update(UpdateCarDamageRequest updateCarDamageRequest) throws BusinessException;

    Result delete(DeleteCarDamageRequest deleteCarDamageRequest) throws BusinessException;

    DataResult<List<CarDamageListDto>> getAll();

    DataResult<CarDamageGetDto> getById(int carDamageId) throws BusinessException;

    DataResult<List<CarDamageListDto>> getByCarId(int carId) throws BusinessException;


}
