package com.turkcell.rentACar.api.controller;

import com.turkcell.rentACar.business.abstracts.CarService;
import com.turkcell.rentACar.business.dtos.CarGetDto;
import com.turkcell.rentACar.business.dtos.CarListDto;
import com.turkcell.rentACar.business.request.CreateCarRequest;
import com.turkcell.rentACar.business.request.DeleteCarRequest;
import com.turkcell.rentACar.business.request.UpdateCarRequest;
import com.turkcell.rentACar.core.exception.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cars")
public class CarsController {

    private CarService carService;

    @Autowired
    public CarsController(CarService carService) {
        this.carService = carService;
    }

    @GetMapping("/getall")
    public List<CarListDto> getAll() {
        return this.carService.getAll();
    }

    @PostMapping("/add")
    public String add(@RequestBody CreateCarRequest createCarRequest) {
        try {
            this.carService.add(createCarRequest);
        } catch (Exception e) {
            return e.getMessage();
        }
        return "Eklendi";
    }

    @GetMapping("getById")
    public CarGetDto getById(@RequestParam int carId) {
        return this.carService.getById(carId);
    }

    @PostMapping("/update")
    public void update(@RequestBody UpdateCarRequest updateCarRequest) throws BusinessException {
        this.carService.update(updateCarRequest);
    }

    @DeleteMapping("/delete")
    public void delete(@RequestBody DeleteCarRequest deleteCarRequest) {
        this.carService.delete(deleteCarRequest);
    }
}
