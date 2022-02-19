package com.turkcell.rentACar.business.abstracts;

import com.turkcell.rentACar.business.dtos.BrandGetDto;
import com.turkcell.rentACar.business.dtos.BrandListDto;
import com.turkcell.rentACar.business.request.CreateBrandRequest;
import com.turkcell.rentACar.business.request.DeleteBrandRequest;
import com.turkcell.rentACar.business.request.UpdateBrandRequest;
import com.turkcell.rentACar.core.exception.BusinessException;
import com.turkcell.rentACar.core.result.DataResult;
import com.turkcell.rentACar.core.result.Result;

import java.util.List;


public interface BrandService {

    Result add(CreateBrandRequest createBrandRequest) throws BusinessException;

    DataResult<List<BrandListDto>> getAll();

    DataResult<BrandGetDto> getById(int brandId);

    Result update(UpdateBrandRequest updateBrandRequest) throws BusinessException;

    Result delete(DeleteBrandRequest deleteBrandRequest);


}
