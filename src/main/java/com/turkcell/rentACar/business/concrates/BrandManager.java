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
import com.turkcell.rentACar.core.result.DataResult;
import com.turkcell.rentACar.core.result.Result;
import com.turkcell.rentACar.core.result.SuccessDataResult;
import com.turkcell.rentACar.core.result.SuccessResult;
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
        checkIfName(createBrandRequest.getName());

        Brand brand = this.modelMapperService.forRequest().map(createBrandRequest, Brand.class);
        this.brandDao.save(brand);
        return new SuccessResult("Eklendi");

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
    public DataResult<BrandGetDto> getById(int brandId) throws BusinessException {
        checkIfId(brandId);
        Brand response = this.brandDao.getById(brandId);
        BrandGetDto result = this.modelMapperService.forDto().map(response, BrandGetDto.class);
        return new SuccessDataResult<BrandGetDto>(result, "Listelendi");
    }

    @Override
    public Result update(UpdateBrandRequest updateBrandRequest) throws BusinessException {
        checkIfName(updateBrandRequest.getName());
        checkIfId(updateBrandRequest.getBrandId());
        Brand brand = this.modelMapperService.forRequest().map(updateBrandRequest, Brand.class);
        this.brandDao.save(brand);
        return new SuccessResult("GÜncellendi");
    }

    @Override
    public Result delete(DeleteBrandRequest deleteBrandRequest) throws BusinessException {
        checkIfId(deleteBrandRequest.getBrandId());
        Brand brand = this.modelMapperService.forRequest().map(deleteBrandRequest, Brand.class);
        this.brandDao.delete(brand);
        return new SuccessResult("Veri Silindi");
    }

    private void checkIfName(String name) throws BusinessException {
        if (this.brandDao.existsByName(name)) {
            throw new BusinessException("İsimler Aynı Olamaz...");
        }
    }

    private void checkIfId(int brandId) throws BusinessException {
        if (!this.brandDao.existsById(brandId)) {
            throw new BusinessException(brandId + " No'lu Marka Bulunamadı");
        }
    }
}
