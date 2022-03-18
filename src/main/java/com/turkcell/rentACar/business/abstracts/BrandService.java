package com.turkcell.rentACar.business.abstracts;

import com.turkcell.rentACar.business.dtos.brand.BrandGetDto;
import com.turkcell.rentACar.business.dtos.brand.BrandListDto;
import com.turkcell.rentACar.business.request.brand.CreateBrandRequest;
import com.turkcell.rentACar.business.request.brand.DeleteBrandRequest;
import com.turkcell.rentACar.business.request.brand.UpdateBrandRequest;
import com.turkcell.rentACar.core.exception.BusinessException;
import com.turkcell.rentACar.core.result.DataResult;
import com.turkcell.rentACar.core.result.Result;

import java.util.List;


public interface BrandService {

    Result add(CreateBrandRequest createBrandRequest) throws BusinessException;

    DataResult<List<BrandListDto>> getAll();

    DataResult<BrandGetDto> getById(int brandId) throws BusinessException;

    Result update(UpdateBrandRequest updateBrandRequest) throws BusinessException;

    Result delete(DeleteBrandRequest deleteBrandRequest) throws BusinessException;

    void checkIfBrandIdExists(int brandId) throws BusinessException;


}
