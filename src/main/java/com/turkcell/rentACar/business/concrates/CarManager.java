package com.turkcell.rentACar.business.concrates;

import com.turkcell.rentACar.business.abstracts.CarService;
import com.turkcell.rentACar.business.dtos.CarGetDto;
import com.turkcell.rentACar.business.dtos.CarListDto;
import com.turkcell.rentACar.business.request.CreateCarRequest;
import com.turkcell.rentACar.business.request.DeleteCarRequest;
import com.turkcell.rentACar.business.request.UpdateCarRequest;
import com.turkcell.rentACar.core.exception.BusinessException;
import com.turkcell.rentACar.core.mapping.ModelMapperService;
import com.turkcell.rentACar.core.result.DataResult;
import com.turkcell.rentACar.core.result.Result;
import com.turkcell.rentACar.core.result.SuccessDataResult;
import com.turkcell.rentACar.core.result.SuccessResult;
import com.turkcell.rentACar.dataAccess.abstracts.CarDao;
import com.turkcell.rentACar.entities.concrates.Car;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CarManager implements CarService {

    private CarDao carDao;
    private ModelMapperService modelMapperService;

    @Autowired
    public CarManager(CarDao carDao, ModelMapperService modelMapperService) {
        this.carDao = carDao;
        this.modelMapperService = modelMapperService;
    }

    @Override
    public Result add(CreateCarRequest createCarRequest) throws BusinessException {
        Car car = this.modelMapperService.forRequest().map(createCarRequest, Car.class);
        this.carDao.save(car);
        return new SuccessResult("Car Added");

    }

    @Override
    public DataResult<List<CarListDto>> getAll() {
        List<Car> result = this.carDao.findAll();
        List<CarListDto> response = result
                .stream()
                .map(car -> this.modelMapperService.forDto().map(car, CarListDto.class))
                .collect(Collectors.toList());
        return new SuccessDataResult<List<CarListDto>>(response, "Listelendi");

    }

    @Override
    public DataResult<CarGetDto> getById(int carId) {
        Car car = this.carDao.getById(carId);
        CarGetDto response = this.modelMapperService.forRequest().map(car, CarGetDto.class);
        return new SuccessDataResult<CarGetDto>(response, "Id ye Göre Getirildi.");
    }

    @Override
    public Result update(UpdateCarRequest updateCarRequest) {
        Car car = this.modelMapperService.forRequest().map(updateCarRequest, Car.class);
        this.carDao.save(car);

        return new SuccessResult("Araba Güncellendi");
    }

    @Override
    public Result delete(DeleteCarRequest deleteCarRequest) {
        Car car = this.modelMapperService.forRequest().map(deleteCarRequest, Car.class);
        this.carDao.delete(car);
        return new SuccessResult("Araba Silindi");
    }

    @Override
    public DataResult<List<CarListDto>> findByDailyPriceLessThanEqual(int dailyPrice) {
        List<Car> result = this.carDao.findByDailyPriceLessThanEqual(dailyPrice);
        List<CarListDto> response = result
                .stream()
                .map(car -> this.modelMapperService.forDto().map(car, CarListDto.class))
                .collect(Collectors.toList());
        return new SuccessDataResult<List<CarListDto>>(response, "Listelendi");
    }

    @Override
    public DataResult<List<CarListDto>> getAllPage(int pageNo, int pageSize) {

        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        List<Car> result = this.carDao.findAll(pageable).getContent();
        List<CarListDto> response = result
                .stream()
                .map(car -> this.modelMapperService.forDto().map(car, CarListDto.class))
                .collect(Collectors.toList());
        return new SuccessDataResult<List<CarListDto>>(response, "Listelendi");
    }

    @Override
    public DataResult<List<CarListDto>> getAllSort(boolean sort) {
        Sort.Direction sortDirection;
        if (sort) {
            sortDirection = Sort.Direction.ASC;
        } else {
            sortDirection = Sort.Direction.DESC;
        }
        Sort sorted = Sort.by(sortDirection, "dailyPrice");
        List<Car> result = this.carDao.findAll(sorted);
        List<CarListDto> response = result
                .stream()
                .map(car -> this.modelMapperService.forDto().map(car, CarListDto.class))
                .collect(Collectors.toList());
        return new SuccessDataResult<List<CarListDto>>(response, "Listelendi");
    }
}
