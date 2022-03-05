package com.turkcell.rentACar.api.controller;

import com.turkcell.rentACar.business.abstracts.RentCarService;
import com.turkcell.rentACar.business.dtos.RentCarGetDto;
import com.turkcell.rentACar.business.dtos.RentCarListDto;
import com.turkcell.rentACar.business.request.CreateRentCarRequest;
import com.turkcell.rentACar.business.request.DeleteRentCarRequest;
import com.turkcell.rentACar.business.request.UpdateRentCarRequest;
import com.turkcell.rentACar.core.exception.BusinessException;
import com.turkcell.rentACar.core.result.DataResult;
import com.turkcell.rentACar.core.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/RentCars")
public class RentCarController {
    private RentCarService rentCarService;

    @Autowired
    public RentCarController(RentCarService rentCarService) {
        this.rentCarService = rentCarService;
    }

    @PostMapping("/add")
    Result add(@RequestBody @Valid CreateRentCarRequest createRentCarRequest) throws BusinessException {
        return this.rentCarService.add(createRentCarRequest);
    }

    @PutMapping("/update")
    Result update(@RequestBody @Valid UpdateRentCarRequest updateRentCarRequest) throws BusinessException {
        return this.rentCarService.update(updateRentCarRequest);
    }

    @DeleteMapping("/delete")
    Result delete(@RequestBody DeleteRentCarRequest deleteRentCarRequest) {
        return this.rentCarService.delete(deleteRentCarRequest);
    }

    @GetMapping("/getAll")
    DataResult<List<RentCarListDto>> getAll() {
        return this.rentCarService.getAll();
    }

    @GetMapping("/getRentCarsByCarId")
    DataResult<List<RentCarListDto>> getRentCarsByCarId(@RequestParam int carId) {
        return this.rentCarService.getRentCarsByCarId(carId);
    }

    @GetMapping("/getById")
    DataResult<RentCarGetDto> getById(@RequestParam int rentCarId) {
        return this.rentCarService.getById(rentCarId);
    }
}
