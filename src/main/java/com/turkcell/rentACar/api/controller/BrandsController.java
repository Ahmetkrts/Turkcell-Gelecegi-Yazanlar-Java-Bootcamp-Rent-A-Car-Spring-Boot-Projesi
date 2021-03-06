package com.turkcell.rentACar.api.controller;

import com.turkcell.rentACar.business.abstracts.BrandService;
import com.turkcell.rentACar.business.dtos.brand.BrandGetDto;
import com.turkcell.rentACar.business.dtos.brand.BrandListDto;
import com.turkcell.rentACar.business.request.brand.CreateBrandRequest;
import com.turkcell.rentACar.business.request.brand.DeleteBrandRequest;
import com.turkcell.rentACar.business.request.brand.UpdateBrandRequest;
import com.turkcell.rentACar.core.exception.BusinessException;
import com.turkcell.rentACar.core.result.DataResult;
import com.turkcell.rentACar.core.result.Result;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/brands")
public class BrandsController {
    private BrandService brandService;


    public BrandsController(BrandService brandService) {
        this.brandService = brandService;
    }

    @GetMapping("/getall")
    public DataResult<List<BrandListDto>> getAll() {

        return this.brandService.getAll();
    }

    @PostMapping("/add")
    public Result add(@RequestBody @Valid CreateBrandRequest createBrandRequest) throws BusinessException {

        return this.brandService.add(createBrandRequest);

    }

    @GetMapping("/getById")
    public DataResult<BrandGetDto> getById(@RequestParam int brandId) throws BusinessException {
        return this.brandService.getById(brandId);
    }

    @PutMapping("/update")
    public Result update(@RequestBody @Valid UpdateBrandRequest updateBrandRequest) throws BusinessException {
        return this.brandService.update(updateBrandRequest);
    }

    @DeleteMapping("/delete")
    public Result delete(@RequestBody DeleteBrandRequest deleteBrandRequest) throws BusinessException {
        return this.brandService.delete(deleteBrandRequest);
    }


}
