package com.turkcell.rentACar.api.controller;

import com.turkcell.rentACar.business.abstracts.CarDamageService;
import com.turkcell.rentACar.business.dtos.carDamage.CarDamageGetDto;
import com.turkcell.rentACar.business.dtos.carDamage.CarDamageListDto;
import com.turkcell.rentACar.business.request.carDamage.CreateCarDamageRequest;
import com.turkcell.rentACar.business.request.carDamage.DeleteCarDamageRequest;
import com.turkcell.rentACar.business.request.carDamage.UpdateCarDamageRequest;
import com.turkcell.rentACar.core.exception.BusinessException;
import com.turkcell.rentACar.core.result.DataResult;
import com.turkcell.rentACar.core.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/CarDamages")
public  class CarDamageController {
    private CarDamageService carDamageService;

    @Autowired
    public CarDamageController(CarDamageService carDamageService) {
        this.carDamageService = carDamageService;
    }

    @PostMapping("/add")
    Result add(@RequestBody @Valid CreateCarDamageRequest createCarDamageRequest) throws BusinessException {
        return this.carDamageService.add(createCarDamageRequest);
    }

    @PutMapping("/update")
    Result update(@RequestBody @Valid UpdateCarDamageRequest updateCarDamageRequest) throws BusinessException {
        return this.carDamageService.update(updateCarDamageRequest);
    }

    @DeleteMapping("/delete")
    Result delete(@RequestBody DeleteCarDamageRequest deleteCarDamageRequest) throws BusinessException {
        return this.carDamageService.delete(deleteCarDamageRequest);
    }

    @GetMapping("/getAll")
    DataResult<List<CarDamageListDto>> getAll() {
        return this.carDamageService.getAll();
    }

    @GetMapping("/getById")
    DataResult<CarDamageGetDto> getById(@RequestParam int carDamageId) throws BusinessException {
        return this.carDamageService.getById(carDamageId);
    }

    @GetMapping("/getByCarId")
    DataResult<List<CarDamageListDto>> getByCarId(@RequestParam int carId) throws BusinessException {
        return this.carDamageService.getByCarId(carId);
    }
}
