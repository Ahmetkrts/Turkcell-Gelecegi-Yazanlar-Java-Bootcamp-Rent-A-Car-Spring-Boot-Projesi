package com.turkcell.rentACar.business.concrates;

import com.turkcell.rentACar.business.abstracts.CarMaintenanceService;
import com.turkcell.rentACar.business.abstracts.CarService;
import com.turkcell.rentACar.business.abstracts.RentCarService;
import com.turkcell.rentACar.business.dtos.CarMaintenanceGetDto;
import com.turkcell.rentACar.business.dtos.CarMaintenanceListDto;
import com.turkcell.rentACar.business.request.CreateCarMaintenanceRequest;
import com.turkcell.rentACar.business.request.DeleteCarMaintenanceRequest;
import com.turkcell.rentACar.business.request.UpdateCarMaintenanceRequest;
import com.turkcell.rentACar.core.exception.BusinessException;
import com.turkcell.rentACar.core.mapping.ModelMapperService;
import com.turkcell.rentACar.core.result.DataResult;
import com.turkcell.rentACar.core.result.Result;
import com.turkcell.rentACar.core.result.SuccessDataResult;
import com.turkcell.rentACar.core.result.SuccessResult;
import com.turkcell.rentACar.dataAccess.abstracts.CarMaintenanceDao;
import com.turkcell.rentACar.entities.concrates.CarMaintenance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CarMaintenanceManager implements CarMaintenanceService {

    private ModelMapperService modelMapperService;
    private CarMaintenanceDao carMaintenanceDao;
    private RentCarService rentCarService;
    private CarService carService;


    @Autowired
    public CarMaintenanceManager
            (ModelMapperService modelMapperService,
             CarMaintenanceDao carMaintenanceDao,
             @Lazy RentCarService rentCarService,
             @Lazy CarService carService) {

        this.modelMapperService = modelMapperService;
        this.carMaintenanceDao = carMaintenanceDao;
        this.rentCarService = rentCarService;
        this.carService = carService;
    }

    @Override
    public Result add(CreateCarMaintenanceRequest createCarMaintenanceRequest) throws BusinessException {
        this.carService.checkIfCarExist(createCarMaintenanceRequest.getCarId());
        this.rentCarService.checkIfRentCarReturnDate(createCarMaintenanceRequest.getCarId());

        checkIfCarMaintenanceReturnDate(createCarMaintenanceRequest.getCarId());
        CarMaintenance carMaintenance = this.modelMapperService.forRequest().map(createCarMaintenanceRequest,
                CarMaintenance.class);
        carMaintenance.setId(0);
        this.carMaintenanceDao.save(carMaintenance);
        return new SuccessResult("Bakım eklendi..");
    }

    @Override
    public Result update(UpdateCarMaintenanceRequest updateCarMaintenanceRequest) throws BusinessException {

        this.carService.checkIfCarExist(updateCarMaintenanceRequest.getCar_CarId());
        this.rentCarService.checkIfRentCarReturnDate(updateCarMaintenanceRequest.getCar_CarId());
        checkIfCarMaintenance(updateCarMaintenanceRequest.getId());


        CarMaintenance carMaintenance = this
                .modelMapperService.forRequest()
                .map(updateCarMaintenanceRequest, CarMaintenance.class);
        this.carMaintenanceDao.save(carMaintenance);
        return new SuccessResult("Bakım Güncellendi..");

    }

    @Override
    public Result delete(DeleteCarMaintenanceRequest deleteCarMaintenanceRequest) throws BusinessException {
        this.carMaintenanceDao.deleteById(deleteCarMaintenanceRequest.getId());
        return new SuccessResult("Bakım Silindi..");
    }

    @Override
    public DataResult<CarMaintenanceGetDto> getById(int carMaintenanceId) throws BusinessException {
        checkIfCarMaintenance(carMaintenanceId);
        CarMaintenance result = this.carMaintenanceDao.getById(carMaintenanceId);
        CarMaintenanceGetDto response = this.modelMapperService.forDto().map(result, CarMaintenanceGetDto.class);
        return new SuccessDataResult<CarMaintenanceGetDto>(response, "Listelendi");
    }

    @Override
    public DataResult<List<CarMaintenanceListDto>> getCarMaintenanceByCarId(int carId) throws BusinessException {
        this.carService.checkIfCarExist(carId);
        List<CarMaintenance> result = this.carMaintenanceDao.getCarMaintenanceByCarCarId(carId);
        List<CarMaintenanceListDto> response = result.stream()
                .map(carMaintenance -> this.modelMapperService.forDto().map(carMaintenance, CarMaintenanceListDto.class))
                .collect(Collectors.toList());
        return new SuccessDataResult<>(response, "Listelendi");
    }

    @Override
    public DataResult<List<CarMaintenanceListDto>> getAll() {
        List<CarMaintenance> result = this.carMaintenanceDao.findAll();
        List<CarMaintenanceListDto> response = result.stream()
                .map(carMaintenance -> this.modelMapperService.forDto().map(carMaintenance, CarMaintenanceListDto.class))
                .collect(Collectors.toList());

        return new SuccessDataResult<>(response, "Listelendi");

    }

    public void checkIfCarMaintenanceReturnDate(int carId) throws BusinessException {
        if (this.carMaintenanceDao.getCarMaintenanceByCarCarIdAndReturnDateIsNull(carId) != null) {
            throw new BusinessException("Araç Şuanda Bakımda..");
        }

    }

    private void checkIfCarMaintenance(int carMaintenanceId) throws BusinessException {
        if (!this.carMaintenanceDao.existsById(carMaintenanceId)) {
            throw new BusinessException("Araç Bakımı Bulunamadı..");
        }
    }


}
