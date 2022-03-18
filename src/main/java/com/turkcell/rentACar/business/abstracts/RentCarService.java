package com.turkcell.rentACar.business.abstracts;

import com.turkcell.rentACar.business.dtos.rentCar.RentCarGetDto;
import com.turkcell.rentACar.business.dtos.rentCar.RentCarListDto;
import com.turkcell.rentACar.business.request.rentCar.CreateRentCarForCorporateRequest;
import com.turkcell.rentACar.business.request.rentCar.CreateRentCarForIndividualRequest;
import com.turkcell.rentACar.business.request.rentCar.DeleteRentCarRequest;
import com.turkcell.rentACar.business.request.rentCar.UpdateRentCarRequest;
import com.turkcell.rentACar.core.exception.BusinessException;
import com.turkcell.rentACar.core.result.DataResult;
import com.turkcell.rentACar.core.result.Result;

import java.util.List;


public interface RentCarService {

    Result addForIndividual(CreateRentCarForIndividualRequest createRentCarForIndividualRequest) throws BusinessException;

    Result addForCorporate(CreateRentCarForCorporateRequest createRentCarForCorporateRequest) throws BusinessException;

    Result update(UpdateRentCarRequest updateRentCarRequest) throws BusinessException;

    Result delete(DeleteRentCarRequest deleteRentCarRequest) throws BusinessException;

    DataResult<List<RentCarListDto>> getAll();

    DataResult<List<RentCarListDto>> getRentCarsByCarId(int carId) throws BusinessException;

    DataResult<RentCarGetDto> getById(int rentCarId) throws BusinessException;

    void checkIfRentCarReturnDateByCarId(int carId) throws BusinessException;

    void checkIfRentCarExists(int rentCarId) throws BusinessException;

    /*double invoiceTotalFeeCalculatorByRentId(int rentCarId) throws BusinessException;*/

}
