package com.turkcell.rentACar.business.concrates;

import com.turkcell.rentACar.business.abstracts.CityService;
import com.turkcell.rentACar.business.dtos.city.CityGetDto;
import com.turkcell.rentACar.business.dtos.city.CityListDto;
import com.turkcell.rentACar.business.request.city.CreateCityRequest;
import com.turkcell.rentACar.business.request.city.DeleteCityRequest;
import com.turkcell.rentACar.business.request.city.UpdateCityRequest;
import com.turkcell.rentACar.core.exception.BusinessException;
import com.turkcell.rentACar.core.mapping.ModelMapperService;
import com.turkcell.rentACar.core.result.DataResult;
import com.turkcell.rentACar.core.result.Result;
import com.turkcell.rentACar.core.result.SuccessDataResult;
import com.turkcell.rentACar.core.result.SuccessResult;
import com.turkcell.rentACar.dataAccess.abstracts.CityDao;
import com.turkcell.rentACar.entities.concrates.City;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CityManager implements CityService {

    private CityDao cityDao;
    private ModelMapperService modelMapperService;

    @Autowired
    public CityManager(CityDao cityDao, ModelMapperService modelMapperService) {
        this.cityDao = cityDao;
        this.modelMapperService = modelMapperService;
    }

    @Override
    public Result add(CreateCityRequest createCityRequest) throws BusinessException {
        checkIfNameExists(createCityRequest.getName());

        City city = this.modelMapperService.forRequest().map(createCityRequest, City.class);
        this.cityDao.save(city);
        return new SuccessResult("eklendi");

    }

    @Override
    public DataResult<List<CityListDto>> getAll() {
        List<City> response = this.cityDao.findAll();
        List<CityListDto> result = response
                .stream()
                .map(city -> this.modelMapperService.forDto().map(city, CityListDto.class))
                .collect(Collectors.toList());
        return new SuccessDataResult<List<CityListDto>>(result, "Listelendi");

    }

    @Override
    public DataResult<CityGetDto> getById(int cityId) throws BusinessException {
        checkIfCityIdExists(cityId);

        City response = this.cityDao.getById(cityId);
        CityGetDto result = this.modelMapperService.forDto().map(response, CityGetDto.class);

        return new SuccessDataResult<CityGetDto>("Listelendi");
    }

    @Override
    public Result update(UpdateCityRequest updateCityRequest) throws BusinessException {
        checkIfNameExists(updateCityRequest.getName());
        checkIfCityIdExists(updateCityRequest.getCityId());

        City city = this.modelMapperService.forRequest().map(updateCityRequest, City.class);
        this.cityDao.save(city);
        return new SuccessResult("eklendi");

    }

    @Override
    public Result delete(DeleteCityRequest deleteCityRequest) throws BusinessException {
        checkIfCityIdExists(deleteCityRequest.getCityId());

        City city = this.modelMapperService.forRequest().map(deleteCityRequest, City.class);
        this.cityDao.deleteById(city.getId());
        return new SuccessResult("Silindi");
    }

    private void checkIfNameExists(String name) throws BusinessException {
        if (this.cityDao.existsByName(name)) {
            throw new BusinessException("İsimler Aynı Olamaz...");
        }
    }

    public void checkIfCityIdExists(int cityId) throws BusinessException {
        if (!this.cityDao.existsById(cityId)) {
            throw new BusinessException(cityId + " No'lu Şehir Bulunamadı");
        }
    }
}
