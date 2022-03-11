package com.turkcell.rentACar.api.controller;

import com.turkcell.rentACar.business.abstracts.CityService;
import com.turkcell.rentACar.business.dtos.CityGetDto;
import com.turkcell.rentACar.business.dtos.CityListDto;
import com.turkcell.rentACar.business.request.CreateCityRequest;
import com.turkcell.rentACar.business.request.DeleteCityRequest;
import com.turkcell.rentACar.business.request.UpdateCityRequest;
import com.turkcell.rentACar.core.exception.BusinessException;
import com.turkcell.rentACar.core.result.DataResult;
import com.turkcell.rentACar.core.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/cities")
public class CityController {

    private CityService cityService;

    @Autowired
    public CityController(CityService cityService) {
        this.cityService = cityService;
    }

    @GetMapping("/getall")
    public DataResult<List<CityListDto>> getAll() {

        return this.cityService.getAll();
    }

    @PostMapping("/add")
    public Result add(@RequestBody @Valid CreateCityRequest createCityRequest) throws BusinessException {

        return this.cityService.add(createCityRequest);

    }

    @GetMapping("getById")
    public DataResult<CityGetDto> getById(@RequestParam int cityId) throws BusinessException {
        return this.cityService.getById(cityId);
    }

    @PutMapping("/update")
    public Result update(@RequestBody @Valid UpdateCityRequest updateCityRequest) throws BusinessException {
        return this.cityService.update(updateCityRequest);
    }

    @DeleteMapping("/delete")
    public Result delete(@RequestBody DeleteCityRequest deleteCityRequest) throws BusinessException {
        return this.cityService.delete(deleteCityRequest);
    }
}
