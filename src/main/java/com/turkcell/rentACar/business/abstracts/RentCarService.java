package com.turkcell.rentACar.business.abstracts;

import com.turkcell.rentACar.business.dtos.rentCar.RentCarGetDto;
import com.turkcell.rentACar.business.dtos.rentCar.RentCarListDto;
import com.turkcell.rentACar.business.request.rentCar.CreateRentCarRequest;
import com.turkcell.rentACar.business.request.rentCar.DeleteRentCarRequest;
import com.turkcell.rentACar.business.request.rentCar.UpdateRentCarRequest;
import com.turkcell.rentACar.business.request.rentCar.UpdateReturnCarRequest;
import com.turkcell.rentACar.core.exception.BusinessException;
import com.turkcell.rentACar.core.result.DataResult;
import com.turkcell.rentACar.core.result.Result;
import com.turkcell.rentACar.entities.concrates.RentCar;

import java.util.List;


public interface RentCarService {

    DataResult<Integer> addForIndividual(CreateRentCarRequest createRentCarRequest) throws BusinessException;

    Result addForCorporate(CreateRentCarRequest createRentCarRequest) throws BusinessException;


    Result update(UpdateRentCarRequest updateRentCarRequest) throws BusinessException;

    Result delete(DeleteRentCarRequest deleteRentCarRequest) throws BusinessException;

    DataResult<List<RentCarListDto>> getAll();

    DataResult<List<RentCarListDto>> getRentCarsByCarId(int carId) throws BusinessException;

    DataResult<RentCarGetDto> getById(int rentCarId) throws BusinessException;

    Result updateForReturnCar(UpdateReturnCarRequest updateReturnCarRequest) throws BusinessException;

    void checkIfRentCarReturnDateByCarId(int carId) throws BusinessException;

    void checkIfRentCarExists(int rentCarId) throws BusinessException;

    double totalRentCarDailyPriceAndDifferntCityCalculator(RentCar rentCar) throws BusinessException;

}
