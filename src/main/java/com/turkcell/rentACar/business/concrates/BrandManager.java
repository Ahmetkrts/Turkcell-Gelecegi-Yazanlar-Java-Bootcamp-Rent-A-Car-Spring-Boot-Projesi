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
    public void add(CreateBrandRequest createBrandRequest) throws BusinessException {
        Brand brand = this.modelMapperService.forRequest().map(createBrandRequest, Brand.class);
        if (checkIfName(brand.getName())) {
            this.brandDao.save(brand);
        }
    }

    @Override
    public List<BrandListDto> getAll() {
        List<Brand> result = this.brandDao.findAll();
        return result.stream()
                .map(brand -> this.modelMapperService
                        .forDto().map(brand, BrandListDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public BrandGetDto getById(int brandId) {
        Brand result = this.brandDao.getById(brandId);
        return this.modelMapperService.forDto().map(result, BrandGetDto.class);
    }

    @Override
    public void update(UpdateBrandRequest updateBrandRequest) throws BusinessException {
        Brand brand = this.modelMapperService.forRequest().map(updateBrandRequest, Brand.class);
        if (checkIfName(brand.getName())) {
            this.brandDao.save(brand);
        }
    }

    @Override
    public void delete(DeleteBrandRequest deleteBrandRequest) {
        Brand brand = this.modelMapperService.forRequest().map(deleteBrandRequest, Brand.class);
        this.brandDao.delete(brand);
    }

    private boolean checkIfName(String name) throws BusinessException {
        var result = this.getAll();
        for (BrandListDto brand1 : result) {
            if (brand1.getName().equals(name)) {
                throw new BusinessException("İsimler Aynı Olamaz");

            }
        }
        return true;

    }
}
