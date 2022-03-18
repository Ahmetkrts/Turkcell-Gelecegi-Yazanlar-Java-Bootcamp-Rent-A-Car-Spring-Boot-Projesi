package com.turkcell.rentACar.business.concrates;

import com.turkcell.rentACar.business.abstracts.CarMaintenanceService;
import com.turkcell.rentACar.business.abstracts.CarService;
import com.turkcell.rentACar.business.abstracts.RentCarService;
import com.turkcell.rentACar.business.dtos.carMaintenance.CarMaintenanceGetDto;
import com.turkcell.rentACar.business.dtos.carMaintenance.CarMaintenanceListDto;
import com.turkcell.rentACar.business.request.carMaintenance.CreateCarMaintenanceRequest;
import com.turkcell.rentACar.business.request.carMaintenance.DeleteCarMaintenanceRequest;
import com.turkcell.rentACar.business.request.carMaintenance.UpdateCarMaintenanceRequest;
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
        this.rentCarService.checkIfRentCarReturnDateByCarId(createCarMaintenanceRequest.getCarId());
        checkIfCarMaintenanceReturnDateByCarId(createCarMaintenanceRequest.getCarId());

        CarMaintenance carMaintenance = this.modelMapperService.forRequest().map(createCarMaintenanceRequest,
                CarMaintenance.class);
        carMaintenance.setCarMaintanenceId(0);
        this.carMaintenanceDao.save(carMaintenance);

        return new SuccessResult("Bakım eklendi..");
    }

    @Override
    public Result update(UpdateCarMaintenanceRequest updateCarMaintenanceRequest) throws BusinessException {
        this.carService.checkIfCarExist(updateCarMaintenanceRequest.getCar_CarId());
        this.rentCarService.checkIfRentCarReturnDateByCarId(updateCarMaintenanceRequest.getCar_CarId());
        checkIfCarMaintenanceIdExists(updateCarMaintenanceRequest.getCarMaintanenceId());


        CarMaintenance carMaintenance = this
                .modelMapperService.forRequest()
                .map(updateCarMaintenanceRequest, CarMaintenance.class);
        this.carMaintenanceDao.save(carMaintenance);

        return new SuccessResult("Bakım Güncellendi..");

    }

    @Override
    public Result delete(DeleteCarMaintenanceRequest deleteCarMaintenanceRequest) throws BusinessException {
        checkIfCarMaintenanceIdExists(deleteCarMaintenanceRequest.getIdcarMaintanenceId());
        this.carMaintenanceDao.deleteById(deleteCarMaintenanceRequest.getIdcarMaintanenceId());

        return new SuccessResult("Bakım Silindi..");
    }

    @Override
    public DataResult<CarMaintenanceGetDto> getById(int carMaintenanceId) throws BusinessException {
        checkIfCarMaintenanceIdExists(carMaintenanceId);

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

    public void checkIfCarMaintenanceReturnDateByCarId(int carId) throws BusinessException {
        if (this.carMaintenanceDao.getCarMaintenanceByCarCarIdAndReturnDateIsNull(carId) != null) {
            throw new BusinessException("Araç Şuanda Bakımda..");
        }

    }

    private void checkIfCarMaintenanceIdExists(int carMaintenanceId) throws BusinessException {
        if (!this.carMaintenanceDao.existsById(carMaintenanceId)) {
            throw new BusinessException(carMaintenanceId + " No'lu Araç Bakımı Bulunamadı..");
        }
    }


}
