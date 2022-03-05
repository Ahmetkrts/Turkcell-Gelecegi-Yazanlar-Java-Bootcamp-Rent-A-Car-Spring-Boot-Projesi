package com.turkcell.rentACar.business.concrates;

import com.turkcell.rentACar.business.abstracts.CarMaintenanceService;
import com.turkcell.rentACar.business.abstracts.RentCarService;
import com.turkcell.rentACar.business.dtos.RentCarGetDto;
import com.turkcell.rentACar.business.dtos.RentCarListDto;
import com.turkcell.rentACar.business.request.CreateRentCarRequest;
import com.turkcell.rentACar.business.request.DeleteRentCarRequest;
import com.turkcell.rentACar.business.request.UpdateRentCarRequest;
import com.turkcell.rentACar.core.exception.BusinessException;
import com.turkcell.rentACar.core.mapping.ModelMapperService;
import com.turkcell.rentACar.core.result.DataResult;
import com.turkcell.rentACar.core.result.Result;
import com.turkcell.rentACar.core.result.SuccessDataResult;
import com.turkcell.rentACar.core.result.SuccessResult;
import com.turkcell.rentACar.dataAccess.abstracts.RentCarDao;
import com.turkcell.rentACar.entities.concrates.RentCar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RentCarManager implements RentCarService {
    private RentCarDao rentCarDao;
    private ModelMapperService modelMapperService;
    private CarMaintenanceService carMaintenanceService;

    @Autowired
    public RentCarManager(RentCarDao rentCarDao,
                          ModelMapperService modelMapperService,
                          @Lazy CarMaintenanceService carMaintenanceService) {
        this.rentCarDao = rentCarDao;
        this.modelMapperService = modelMapperService;
        this.carMaintenanceService = carMaintenanceService;
    }

    @Override
    public Result add(CreateRentCarRequest createRentCarRequest) throws BusinessException {
        this.carMaintenanceService.checkIfCarMaintenanceReturnDate(createRentCarRequest.getCarId());
        checkIfRentCarReturnDate(createRentCarRequest.getCarId());
        RentCar rentCar = this.modelMapperService.forRequest().map(createRentCarRequest, RentCar.class);
        rentCar.setRentCarId(0);
        this.rentCarDao.save(rentCar);
        return new SuccessResult("Added");
    }

    @Override
    public Result update(UpdateRentCarRequest updateRentCarRequest) throws BusinessException {
        checkIfRentCar(updateRentCarRequest.getRentCarId());
        this.carMaintenanceService.checkIfCarMaintenanceReturnDate(updateRentCarRequest.getCarId());
        RentCar rentCar = this.modelMapperService.forRequest().map(updateRentCarRequest, RentCar.class);
        this.rentCarDao.save(rentCar);
        return new SuccessResult("updated");
    }

    @Override
    public Result delete(DeleteRentCarRequest deleteRentCarRequest) {
        RentCar rentCar = this.modelMapperService.forRequest().map(deleteRentCarRequest, RentCar.class);
        this.rentCarDao.delete(rentCar);
        return new SuccessResult("deleted");
    }

    @Override
    public DataResult<List<RentCarListDto>> getAll() {
        List<RentCar> result = this.rentCarDao.findAll();
        List<RentCarListDto> response = result.stream()
                .map(rentCar -> this.modelMapperService.forDto().map(rentCar, RentCarListDto.class))
                .collect(Collectors.toList());
        return new SuccessDataResult<List<RentCarListDto>>(response, "Listelendi");
    }

    @Override
    public DataResult<List<RentCarListDto>> getRentCarsByCarId(int carId) {
        List<RentCar> result = this.rentCarDao.getRentCarByCar_CarId(carId);
        List<RentCarListDto> response = result.stream()
                .map(rentCar -> this.modelMapperService.forDto().map(rentCar, RentCarListDto.class))
                .collect(Collectors.toList());
        return new SuccessDataResult<List<RentCarListDto>>(response, "Listelendi");
    }

    @Override
    public DataResult<RentCarGetDto> getById(int rentCarId) {
        RentCar result = this.rentCarDao.getById(rentCarId);
        RentCarGetDto response = this.modelMapperService.forDto().map(result, RentCarGetDto.class);
        return new SuccessDataResult<RentCarGetDto>(response, "Listelendi..");
    }


    public void checkIfRentCarReturnDate(int carId) throws BusinessException {
        if (this.rentCarDao.getRentCarByCar_CarIdAndDateOfReceiptIsNull(carId) != null) {
            throw new BusinessException("Araç Kiralandı..");
        }
    }

    private void checkIfRentCar(int rentCarId) throws BusinessException {
        if (!this.rentCarDao.existsByRentCarId(rentCarId)) {
            throw new BusinessException("Araç Kiralama Kayıtı Bulunamadı");
        }
    }


}