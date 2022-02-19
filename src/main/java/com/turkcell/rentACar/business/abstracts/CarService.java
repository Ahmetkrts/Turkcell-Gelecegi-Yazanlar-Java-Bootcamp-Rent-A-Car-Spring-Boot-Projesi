package com.turkcell.rentACar.business.abstracts;

import com.turkcell.rentACar.business.dtos.CarGetDto;
import com.turkcell.rentACar.business.dtos.CarListDto;
import com.turkcell.rentACar.business.request.CreateCarRequest;
import com.turkcell.rentACar.business.request.DeleteCarRequest;
import com.turkcell.rentACar.business.request.UpdateCarRequest;
import com.turkcell.rentACar.core.exception.BusinessException;
import com.turkcell.rentACar.core.result.DataResult;
import com.turkcell.rentACar.core.result.Result;

import java.util.List;

public interface CarService {

    Result add(CreateCarRequest createCarRequest) throws BusinessException;

    DataResult<List<CarListDto>> getAll();

    DataResult<CarGetDto> getById(int carId);

    Result update(UpdateCarRequest updateCarRequest);

    Result delete(DeleteCarRequest deleteCarRequest);

    DataResult<List<CarListDto>> findByDailyPriceLessThanEqual(int dailyPrice);

    DataResult<List<CarListDto>> getAllPage(int pageNo, int pageSize);

    DataResult<List<CarListDto>> getAllSort(boolean sort);
}
