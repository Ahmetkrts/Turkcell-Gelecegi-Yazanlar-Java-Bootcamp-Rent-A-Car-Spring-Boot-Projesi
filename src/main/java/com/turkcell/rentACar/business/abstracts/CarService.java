package com.turkcell.rentACar.business.abstracts;

import com.turkcell.rentACar.business.dtos.CarGetDto;
import com.turkcell.rentACar.business.dtos.CarListDto;
import com.turkcell.rentACar.business.request.*;
import com.turkcell.rentACar.core.exception.BusinessException;

import java.util.List;

public interface CarService {

    void add(CreateCarRequest createCarRequest) throws BusinessException;

    List<CarListDto> getAll();

    CarGetDto getById(int carId);

    void update(UpdateCarRequest updateCarRequest);

    void delete(DeleteCarRequest deleteCarRequest);
}
