package com.turkcell.rentACar.api.controller;

import com.turkcell.rentACar.business.abstracts.CarMaintenanceService;
import com.turkcell.rentACar.business.dtos.carMaintenance.CarMaintenanceGetDto;
import com.turkcell.rentACar.business.dtos.carMaintenance.CarMaintenanceListDto;
import com.turkcell.rentACar.business.request.carMaintenance.CreateCarMaintenanceRequest;
import com.turkcell.rentACar.business.request.carMaintenance.DeleteCarMaintenanceRequest;
import com.turkcell.rentACar.business.request.carMaintenance.UpdateCarMaintenanceRequest;
import com.turkcell.rentACar.core.exception.BusinessException;
import com.turkcell.rentACar.core.result.DataResult;
import com.turkcell.rentACar.core.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/carMaintenances")
public class CarMaintenanceController {

    private CarMaintenanceService carMaintenanceService;

    @Autowired
    public CarMaintenanceController(CarMaintenanceService carMaintenanceService) {
        this.carMaintenanceService = carMaintenanceService;
    }

    @PostMapping("/add")
    Result add(@RequestBody @Valid CreateCarMaintenanceRequest createCarMaintenanceRequest) throws BusinessException {
        return this.carMaintenanceService.add(createCarMaintenanceRequest);
    }

    @PutMapping("/update")
    Result update(@RequestBody @Valid UpdateCarMaintenanceRequest updateCarMaintenanceRequest) throws BusinessException {
        return this.carMaintenanceService.update(updateCarMaintenanceRequest);
    }

    @DeleteMapping("/delete")
    Result delete(@RequestBody DeleteCarMaintenanceRequest deleteCarMaintenanceRequest) throws BusinessException {
        return this.carMaintenanceService.delete(deleteCarMaintenanceRequest);
    }

    @GetMapping("/getById")
    DataResult<CarMaintenanceGetDto> getById(@RequestParam int carMaintenanceId) throws BusinessException {
        return this.carMaintenanceService.getById(carMaintenanceId);
    }

    @GetMapping("/getCarMaintenanceByCarId")
    DataResult<List<CarMaintenanceListDto>> getCarMaintenanceByCarId(@RequestParam int carId) throws BusinessException {
        return this.carMaintenanceService.getCarMaintenanceByCarId(carId);
    }

    @GetMapping("/getAll")
    DataResult<List<CarMaintenanceListDto>> getAll() {
        return this.carMaintenanceService.getAll();
    }
}
