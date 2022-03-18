package com.turkcell.rentACar.api.controller;

import com.turkcell.rentACar.business.abstracts.RentCarService;
import com.turkcell.rentACar.business.dtos.rentCar.RentCarGetDto;
import com.turkcell.rentACar.business.dtos.rentCar.RentCarListDto;
import com.turkcell.rentACar.business.request.rentCar.CreateRentCarForCorporateRequest;
import com.turkcell.rentACar.business.request.rentCar.CreateRentCarForIndividualRequest;
import com.turkcell.rentACar.business.request.rentCar.DeleteRentCarRequest;
import com.turkcell.rentACar.business.request.rentCar.UpdateRentCarRequest;
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

    //ismi rental olucak rentalcar olucak

    @Autowired
    public RentCarController(RentCarService rentCarService) {
        this.rentCarService = rentCarService;
    }

    @PostMapping("/addForIndividual")
    Result addForIndividual(@RequestBody @Valid CreateRentCarForIndividualRequest createRentCarForIndividualRequest) throws BusinessException {
        return this.rentCarService.addForIndividual(createRentCarForIndividualRequest);
    }

    @PostMapping("/addForCorporate")
    Result addForCorporate(@RequestBody @Valid CreateRentCarForCorporateRequest createRentCarForCorporateRequest) throws BusinessException {
        return this.rentCarService.addForCorporate(createRentCarForCorporateRequest);
    }

    @PutMapping("/update")
    Result update(@RequestBody @Valid UpdateRentCarRequest updateRentCarRequest) throws BusinessException {
        return this.rentCarService.update(updateRentCarRequest);
    }

    @DeleteMapping("/delete")
    Result delete(@RequestBody DeleteRentCarRequest deleteRentCarRequest) throws BusinessException {
        return this.rentCarService.delete(deleteRentCarRequest);
    }

    @GetMapping("/getAll")
    DataResult<List<RentCarListDto>> getAll() {
        return this.rentCarService.getAll();
    }

    @GetMapping("/getRentCarsByCarId")
    DataResult<List<RentCarListDto>> getRentCarsByCarId(@RequestParam int carId) throws BusinessException {
        return this.rentCarService.getRentCarsByCarId(carId);
    }

    @GetMapping("/getById")
    DataResult<RentCarGetDto> getById(@RequestParam int rentCarId) throws BusinessException {
        return this.rentCarService.getById(rentCarId);
    }
}
