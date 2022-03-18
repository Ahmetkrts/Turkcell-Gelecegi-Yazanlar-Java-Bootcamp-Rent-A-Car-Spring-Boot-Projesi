package com.turkcell.rentACar.business.abstracts;

import com.turkcell.rentACar.business.dtos.carMaintenance.CarMaintenanceGetDto;
import com.turkcell.rentACar.business.dtos.carMaintenance.CarMaintenanceListDto;
import com.turkcell.rentACar.business.request.carMaintenance.CreateCarMaintenanceRequest;
import com.turkcell.rentACar.business.request.carMaintenance.DeleteCarMaintenanceRequest;
import com.turkcell.rentACar.business.request.carMaintenance.UpdateCarMaintenanceRequest;
import com.turkcell.rentACar.core.exception.BusinessException;
import com.turkcell.rentACar.core.result.DataResult;
import com.turkcell.rentACar.core.result.Result;

import java.util.List;

public interface CarMaintenanceService {

    Result add(CreateCarMaintenanceRequest createCarMaintenanceRequest) throws BusinessException;

    Result update(UpdateCarMaintenanceRequest updateCarMaintenanceRequest) throws BusinessException;

    Result delete(DeleteCarMaintenanceRequest deleteCarMaintenanceRequest) throws BusinessException;

    DataResult<CarMaintenanceGetDto> getById(int carMaintenanceId) throws BusinessException;

    DataResult<List<CarMaintenanceListDto>> getCarMaintenanceByCarId(int carId) throws BusinessException;

    DataResult<List<CarMaintenanceListDto>> getAll();

    void checkIfCarMaintenanceReturnDateByCarId(int carId) throws BusinessException;

}
