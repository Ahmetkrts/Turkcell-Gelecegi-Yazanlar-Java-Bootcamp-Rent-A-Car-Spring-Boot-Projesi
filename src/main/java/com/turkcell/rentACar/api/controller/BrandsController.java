package com.turkcell.rentACar.api.controller;

import com.turkcell.rentACar.business.abstracts.BrandService;
import com.turkcell.rentACar.business.dtos.BrandGetDto;
import com.turkcell.rentACar.business.dtos.BrandListDto;
import com.turkcell.rentACar.business.request.CreateBrandRequest;
import com.turkcell.rentACar.business.request.DeleteBrandRequest;
import com.turkcell.rentACar.business.request.UpdateBrandRequest;
import com.turkcell.rentACar.core.exception.BusinessException;
import com.turkcell.rentACar.entities.concrates.Brand;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/brands")
public class BrandsController {
    private BrandService brandService;


    public BrandsController(BrandService brandService) {
        this.brandService = brandService;
    }

    @GetMapping("/getall")
    public List<BrandListDto> getAll() {

        return this.brandService.getAll();
    }

    @PostMapping("/add")
    public String add(@RequestBody CreateBrandRequest createBrandRequest) {
        try {
            this.brandService.add(createBrandRequest);
        } catch (Exception e) {
            return e.getMessage();
        }
        return "Eklendi";
    }

    @GetMapping("/getById")
    public BrandGetDto getById(@RequestParam int brandId) {
        return this.brandService.getById(brandId);
    }

    @PostMapping("/update")
    public void update(@RequestBody UpdateBrandRequest updateBrandRequest) throws BusinessException {
        this.brandService.update(updateBrandRequest);
    }

    @DeleteMapping("/delete")
    public void delete(@RequestBody DeleteBrandRequest deleteBrandRequest) {
        this.brandService.delete(deleteBrandRequest);
    }


}