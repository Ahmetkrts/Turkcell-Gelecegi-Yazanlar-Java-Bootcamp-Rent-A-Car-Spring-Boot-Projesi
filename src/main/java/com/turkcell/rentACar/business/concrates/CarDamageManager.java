package com.turkcell.rentACar.business.concrates;

import com.turkcell.rentACar.business.abstracts.CarDamageService;
import com.turkcell.rentACar.business.abstracts.CarService;
import com.turkcell.rentACar.business.constants.messages.BusinessMessages;
import com.turkcell.rentACar.business.dtos.carDamage.CarDamageGetDto;
import com.turkcell.rentACar.business.dtos.carDamage.CarDamageListDto;
import com.turkcell.rentACar.business.request.carDamage.CreateCarDamageRequest;
import com.turkcell.rentACar.business.request.carDamage.DeleteCarDamageRequest;
import com.turkcell.rentACar.business.request.carDamage.UpdateCarDamageRequest;
import com.turkcell.rentACar.core.exception.BusinessException;
import com.turkcell.rentACar.core.mapping.ModelMapperService;
import com.turkcell.rentACar.core.result.DataResult;
import com.turkcell.rentACar.core.result.Result;
import com.turkcell.rentACar.core.result.SuccessDataResult;
import com.turkcell.rentACar.core.result.SuccessResult;
import com.turkcell.rentACar.dataAccess.abstracts.CarDamageDao;
import com.turkcell.rentACar.entities.concrates.CarDamage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CarDamageManager implements CarDamageService {


    private CarDamageDao carDamageDao;
    private ModelMapperService modelMapperService;
    private CarService carService;

    @Autowired
    public CarDamageManager(CarDamageDao carDamageDao, ModelMapperService modelMapperService,
                            @Lazy CarService carService) {
        this.carDamageDao = carDamageDao;
        this.modelMapperService = modelMapperService;
        this.carService = carService;
    }

    @Override
    public Result add(CreateCarDamageRequest createCarDamageRequest) throws BusinessException {
        this.carService.checkIfCarExist(createCarDamageRequest.getCarId());
        CarDamage cardamage = this.modelMapperService.forRequest().map(createCarDamageRequest, CarDamage.class);

        cardamage.setCarDamageId(0);
        this.carDamageDao.save(cardamage);

        return new SuccessResult(BusinessMessages.ADDED);
    }

    @Override
    public Result update(UpdateCarDamageRequest updateCarDamageRequest) throws BusinessException {
        checkIfCarDamageIdExist(updateCarDamageRequest.getCarDamageId());
        this.carService.checkIfCarExist(updateCarDamageRequest.getCarId());

        CarDamage cardamage = this.modelMapperService.forRequest().map(updateCarDamageRequest, CarDamage.class);
        this.carDamageDao.save(cardamage);

        return new SuccessResult(BusinessMessages.UPDATED);
    }

    @Override
    public Result delete(DeleteCarDamageRequest deleteCarDamageRequest) throws BusinessException {
        checkIfCarDamageIdExist(deleteCarDamageRequest.getCarDamageId());

        CarDamage cardamage = this.modelMapperService.forRequest().map(deleteCarDamageRequest, CarDamage.class);
        this.carDamageDao.deleteById(cardamage.getCarDamageId());

        return new SuccessResult(BusinessMessages.DELETED);
    }

    @Override
    public DataResult<List<CarDamageListDto>> getAll() {

        List<CarDamage> response = this.carDamageDao.findAll();

        List<CarDamageListDto> result = response.stream()
                .map(carDamage -> this.modelMapperService.forDto().map(carDamage, CarDamageListDto.class))
                .collect(Collectors.toList());

        return new SuccessDataResult<>(result, BusinessMessages.LISTED);
    }

    @Override
    public DataResult<CarDamageGetDto> getById(int carDamageId) throws BusinessException {
        checkIfCarDamageIdExist(carDamageId);

        CarDamage response = this.carDamageDao.getById(carDamageId);
        CarDamageGetDto result = this.modelMapperService.forDto().map(response, CarDamageGetDto.class);

        return new SuccessDataResult<>(result, carDamageId + BusinessMessages.CAR_DAMAGE_LISTED);
    }

    @Override
    public DataResult<List<CarDamageListDto>> getByCarId(int carId) throws BusinessException {
        this.carService.checkIfCarExist(carId);

        List<CarDamage> response = this.carDamageDao.getByCar_CarId(carId);

        List<CarDamageListDto> result = response.stream()
                .map(carDamage -> this.modelMapperService.forDto().map(carDamage, CarDamageListDto.class))
                .collect(Collectors.toList());

        return new SuccessDataResult<>(result, carId + BusinessMessages.CAR_DAMAGE_BY_CAR_ID_LISTED);
    }

    private void checkIfCarDamageIdExist(int carId) throws BusinessException {
        if (!this.carDamageDao.existsById(carId)) {
            throw new BusinessException(carId + BusinessMessages.CAR_DAMAGE_NOT_FOUND);
        }
    }
}
