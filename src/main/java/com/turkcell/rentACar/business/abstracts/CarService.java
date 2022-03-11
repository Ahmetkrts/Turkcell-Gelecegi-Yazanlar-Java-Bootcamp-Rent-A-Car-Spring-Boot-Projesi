package com.turkcell.rentACar.business.abstracts;

import com.turkcell.rentACar.business.dtos.CarGetDto;
import com.turkcell.rentACar.business.dtos.CarListDto;
import com.turkcell.rentACar.business.request.CreateCarRequest;
import com.turkcell.rentACar.business.request.DeleteCarRequest;
import com.turkcell.rentACar.business.request.UpdateCarRequest;
import com.turkcell.rentACar.core.exception.BusinessException;
import com.turkcell.rentACar.core.result.DataResult;
import com.turkcell.rentACar.core.result.Result;

import java.time.LocalDate;
import java.util.List;

public interface CarService {

    Result add(CreateCarRequest createCarRequest) throws BusinessException;

    DataResult<List<CarListDto>> getAll();

    DataResult<CarGetDto> getById(int carId) throws BusinessException;

    Result update(UpdateCarRequest updateCarRequest) throws BusinessException;

    Result delete(DeleteCarRequest deleteCarRequest) throws BusinessException;

    DataResult<List<CarListDto>> findByDailyPriceLessThanEqual(int dailyPrice);

    DataResult<List<CarListDto>> getAllPage(int pageNo, int pageSize);

    DataResult<List<CarListDto>> getAllSort(boolean sort);

    void checkIfCarExist(int carId) throws BusinessException;

    double totalCarDailyPriceCalculator(int carId, LocalDate dateOfIssue, LocalDate dateOfReceipt);
}
