package com.turkcell.rentACar.business.abstracts;

import com.turkcell.rentACar.business.dtos.RentCarGetDto;
import com.turkcell.rentACar.business.dtos.RentCarListDto;
import com.turkcell.rentACar.business.request.CreateRentCarRequest;
import com.turkcell.rentACar.business.request.DeleteRentCarRequest;
import com.turkcell.rentACar.business.request.UpdateRentCarRequest;
import com.turkcell.rentACar.core.exception.BusinessException;
import com.turkcell.rentACar.core.result.DataResult;
import com.turkcell.rentACar.core.result.Result;

import java.util.List;


public interface RentCarService {

    Result add(CreateRentCarRequest createRentCarRequest) throws BusinessException;

    Result update(UpdateRentCarRequest updateRentCarRequest) throws BusinessException;

    Result delete(DeleteRentCarRequest deleteRentCarRequest);

    DataResult<List<RentCarListDto>> getAll();

    DataResult<List<RentCarListDto>> getRentCarsByCarId(int carId);

    DataResult<RentCarGetDto> getById(int rentCarId);

    void checkIfRentCarReturnDate(int carId) throws BusinessException;

}
