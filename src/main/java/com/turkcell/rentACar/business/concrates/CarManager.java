package com.turkcell.rentACar.business.concrates;

import com.turkcell.rentACar.business.abstracts.BrandService;
import com.turkcell.rentACar.business.abstracts.CarService;
import com.turkcell.rentACar.business.abstracts.ColorService;
import com.turkcell.rentACar.business.dtos.car.CarGetDto;
import com.turkcell.rentACar.business.dtos.car.CarListDto;
import com.turkcell.rentACar.business.request.car.CreateCarRequest;
import com.turkcell.rentACar.business.request.car.DeleteCarRequest;
import com.turkcell.rentACar.business.request.car.UpdateCarRequest;
import com.turkcell.rentACar.core.exception.BusinessException;
import com.turkcell.rentACar.core.mapping.ModelMapperService;
import com.turkcell.rentACar.core.result.DataResult;
import com.turkcell.rentACar.core.result.Result;
import com.turkcell.rentACar.core.result.SuccessDataResult;
import com.turkcell.rentACar.core.result.SuccessResult;
import com.turkcell.rentACar.dataAccess.abstracts.CarDao;
import com.turkcell.rentACar.entities.concrates.Car;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
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
    private BrandService brandService;
    private ColorService colorService;

    @Autowired
    public CarManager(CarDao carDao, ModelMapperService modelMapperService,
                      @Lazy ColorService colorService,
                      @Lazy BrandService brandService) {
        this.carDao = carDao;
        this.modelMapperService = modelMapperService;
        this.colorService = colorService;
        this.brandService = brandService;
    }

    @Override
    public Result add(CreateCarRequest createCarRequest) throws BusinessException {

        this.brandService.checkIfBrandIdExists(createCarRequest.getBrandId());
        this.colorService.checkIfColorIdExists(createCarRequest.getColorId());

        Car car = this.modelMapperService.forRequest().map(createCarRequest, Car.class);
        car.setCarId(0);
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
    public DataResult<CarGetDto> getById(int carId) throws BusinessException {
        checkIfCarExist(carId);

        Car car = this.carDao.getById(carId);
        CarGetDto response = this.modelMapperService.forRequest().map(car, CarGetDto.class);
        return new SuccessDataResult<CarGetDto>(response, "Id ye Göre Getirildi.");
    }

    @Override
    public Result update(UpdateCarRequest updateCarRequest) throws BusinessException {
        checkIfCarExist(updateCarRequest.getCarId());
        this.brandService.checkIfBrandIdExists(updateCarRequest.getBrandId());
        this.colorService.checkIfColorIdExists(updateCarRequest.getColorId());

        Car car = this.modelMapperService.forRequest().map(updateCarRequest, Car.class);
        this.carDao.save(car);

        return new SuccessResult("Araba Güncellendi");
    }

    @Override
    public Result delete(DeleteCarRequest deleteCarRequest) throws BusinessException {
        checkIfCarExist(deleteCarRequest.getCarId());

        Car car = this.modelMapperService.forRequest().map(deleteCarRequest, Car.class);
        this.carDao.deleteById(car.getCarId());
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

        Sort.Direction sortDirection = chooseSortDirection(sort);

        Sort sorted = Sort.by(sortDirection, "dailyPrice");
        List<Car> result = this.carDao.findAll(sorted);
        List<CarListDto> response = result
                .stream()
                .map(car -> this.modelMapperService.forDto().map(car, CarListDto.class))
                .collect(Collectors.toList());
        return new SuccessDataResult<List<CarListDto>>(response, "Listelendi");
    }

    public void checkIfCarExist(int carId) throws BusinessException {
        if (!this.carDao.existsByCarId(carId)) {
            throw new BusinessException(carId + " No'lu Araç Bulunamadı..");
        }
    }

    private Sort.Direction chooseSortDirection(boolean sort) {
        if (sort) {
            return Sort.Direction.ASC;
        } else {
            return Sort.Direction.DESC;
        }
    }

    @Override
    public void isCarReturnedFromRent(int carId, double returnDistance) throws BusinessException {
        checkIfCarExist(carId);
        Car car = this.carDao.getById(carId);
        if (car.getDistance() < returnDistance) {
            car.setDistance(returnDistance);
            this.carDao.save(car);
        } else {
            throw new BusinessException("Girilen Dönüş Kilometresi değeri Aracın şuanki Kilometresinden Düşük ");
        }
    }

   /* @Override
    public double totalCarDailyPriceCalculator(int carId, LocalDate dateOfIssue, LocalDate dateOfReceipt) {
        int daysBetweenTwoDates = (int) ChronoUnit.DAYS.between(dateOfIssue, dateOfReceipt);
        return this.carDao.getById(carId).getDailyPrice() * daysBetweenTwoDates;
    }*/


}
