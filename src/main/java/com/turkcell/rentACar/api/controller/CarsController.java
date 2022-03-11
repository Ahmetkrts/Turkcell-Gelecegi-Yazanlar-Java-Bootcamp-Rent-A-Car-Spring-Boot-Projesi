package com.turkcell.rentACar.api.controller;

import com.turkcell.rentACar.business.abstracts.CarService;
import com.turkcell.rentACar.business.dtos.CarGetDto;
import com.turkcell.rentACar.business.dtos.CarListDto;
import com.turkcell.rentACar.business.request.CreateCarRequest;
import com.turkcell.rentACar.business.request.DeleteCarRequest;
import com.turkcell.rentACar.business.request.UpdateCarRequest;
import com.turkcell.rentACar.core.exception.BusinessException;
import com.turkcell.rentACar.core.result.DataResult;
import com.turkcell.rentACar.core.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
    public DataResult<List<CarListDto>> getAll() {
        return this.carService.getAll();
    }

    @PostMapping("/add")
    public Result add(@RequestBody @Valid CreateCarRequest createCarRequest) throws BusinessException {

        return this.carService.add(createCarRequest);

    }

    @GetMapping("getById")
    public DataResult<CarGetDto> getById(@RequestParam int carId) throws BusinessException {

        return this.carService.getById(carId);
    }

    @PutMapping("/update")
    public Result update(@RequestBody @Valid UpdateCarRequest updateCarRequest) throws BusinessException {
        return this.carService.update(updateCarRequest);
    }

    @DeleteMapping("/delete")
    public Result delete(@RequestBody DeleteCarRequest deleteCarRequest) throws BusinessException {
        return this.carService.delete(deleteCarRequest);
    }

    @GetMapping("findByDailyPriceLessThanEqual")
    public DataResult<List<CarListDto>> findByDailyPriceLessThanEqual(int dailyPrice) {
        return this.carService.findByDailyPriceLessThanEqual(dailyPrice);
    }

    @GetMapping("getAllPage")
    public DataResult<List<CarListDto>> getAllPage(int pageNo, int pageSize) {
        return this.carService.getAllPage(pageNo, pageSize);
    }

    @GetMapping("getAllSort")
    public DataResult<List<CarListDto>> getAllSort(boolean sort) {
        return this.carService.getAllSort(sort);
    }
}
