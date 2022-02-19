package com.turkcell.rentACar.business.concrates;

import com.turkcell.rentACar.business.abstracts.BrandService;
import com.turkcell.rentACar.business.dtos.BrandGetDto;
import com.turkcell.rentACar.business.dtos.BrandListDto;
import com.turkcell.rentACar.business.request.CreateBrandRequest;
import com.turkcell.rentACar.business.request.DeleteBrandRequest;
import com.turkcell.rentACar.business.request.UpdateBrandRequest;
import com.turkcell.rentACar.core.exception.BusinessException;
import com.turkcell.rentACar.core.mapping.ModelMapperManager;
import com.turkcell.rentACar.core.mapping.ModelMapperService;
import com.turkcell.rentACar.core.result.*;
import com.turkcell.rentACar.dataAccess.abstracts.BrandDao;
import com.turkcell.rentACar.entities.concrates.Brand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BrandManager implements BrandService {

    private BrandDao brandDao;
    private ModelMapperService modelMapperService;

    @Autowired
    public BrandManager(BrandDao brandDao, ModelMapperManager modelMapperService) {
        this.modelMapperService = modelMapperService;
        this.brandDao = brandDao;
    }

    @Override
    public Result add(CreateBrandRequest createBrandRequest) throws BusinessException {
        Brand brand = this.modelMapperService.forRequest().map(createBrandRequest, Brand.class);
        if (checkIfName(brand.getName())) {
            this.brandDao.save(brand);
            return new SuccessResult("Eklendi");
        }
        return new ErrorResult("İsimler Aynı olamaz");
    }

    @Override
    public DataResult<List<BrandListDto>> getAll() {
        List<Brand> response = this.brandDao.findAll();
        List<BrandListDto> result = response.stream()
                .map(brand -> this.modelMapperService
                        .forDto().map(brand, BrandListDto.class))
                .collect(Collectors.toList());
        return new SuccessDataResult<List<BrandListDto>>(result, "Listelendi");
    }

    @Override
    public DataResult<BrandGetDto> getById(int brandId) {
        Brand response = this.brandDao.getById(brandId);
        BrandGetDto result = this.modelMapperService.forDto().map(response, BrandGetDto.class);
        return new SuccessDataResult<BrandGetDto>(result, "Listelendi");
    }

    @Override
    public Result update(UpdateBrandRequest updateBrandRequest) throws BusinessException {
        Brand brand = this.modelMapperService.forRequest().map(updateBrandRequest, Brand.class);
        if (checkIfName(brand.getName())) {
            this.brandDao.save(brand);
            return new SuccessResult("GÜncellendi");
        }
        return new ErrorResult("İsimler Aynı olamaz");
    }

    @Override
    public Result delete(DeleteBrandRequest deleteBrandRequest) {
        Brand brand = this.modelMapperService.forRequest().map(deleteBrandRequest, Brand.class);
        this.brandDao.delete(brand);
        return new SuccessResult("Veri Silindi");
    }

    private boolean checkIfName(String name) throws BusinessException {
        var result = this.brandDao.findAll();
        for (Brand brand1 : result) {
            if (brand1.getName().equals(name)) {
                throw new BusinessException("İsimler Aynı Olamaz");

            }
        }
        return true;

    }
}
