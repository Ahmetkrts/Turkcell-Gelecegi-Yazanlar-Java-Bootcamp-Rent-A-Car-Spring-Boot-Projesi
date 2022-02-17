package com.turkcell.rentACar.business.abstracts;

import com.turkcell.rentACar.business.dtos.BrandGetDto;
import com.turkcell.rentACar.business.dtos.BrandListDto;
import com.turkcell.rentACar.business.request.CreateBrandRequest;
import com.turkcell.rentACar.business.request.DeleteBrandRequest;
import com.turkcell.rentACar.business.request.UpdateBrandRequest;
import com.turkcell.rentACar.core.exception.BusinessException;
import com.turkcell.rentACar.entities.concrates.Brand;

import java.util.List;


public interface BrandService {

    void add(CreateBrandRequest createBrandRequest) throws BusinessException;

    List<BrandListDto> getAll();

    BrandGetDto getById(int brandId);

    void update(UpdateBrandRequest updateBrandRequest) throws BusinessException;

    void delete(DeleteBrandRequest deleteBrandRequest);



}
