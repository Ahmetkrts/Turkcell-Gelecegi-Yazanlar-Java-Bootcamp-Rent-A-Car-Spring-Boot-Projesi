package com.turkcell.rentACar.business.concrates;

import com.turkcell.rentACar.business.abstracts.*;
import com.turkcell.rentACar.business.dtos.rentCar.RentCarGetDto;
import com.turkcell.rentACar.business.dtos.rentCar.RentCarListDto;
import com.turkcell.rentACar.business.request.rentCar.CreateRentCarForCorporateRequest;
import com.turkcell.rentACar.business.request.rentCar.CreateRentCarForIndividualRequest;
import com.turkcell.rentACar.business.request.rentCar.DeleteRentCarRequest;
import com.turkcell.rentACar.business.request.rentCar.UpdateRentCarRequest;
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
    private CarService carService;
    private CityService cityService;
    private IndividualCustomerService individualCustomerService;
    private CorporateCustomerService corporateCustomerService;


    @Autowired
    public RentCarManager(RentCarDao rentCarDao,
                          ModelMapperService modelMapperService,
                          @Lazy CarMaintenanceService carMaintenanceService,
                          @Lazy CarService carService,
                          @Lazy CityService cityService,
                          @Lazy CorporateCustomerService corporateCustomerService,
                          @Lazy IndividualCustomerService individualCustomerService) {
        this.rentCarDao = rentCarDao;
        this.modelMapperService = modelMapperService;
        this.carMaintenanceService = carMaintenanceService;
        this.carService = carService;
        this.cityService = cityService;
        this.corporateCustomerService = corporateCustomerService;
        this.individualCustomerService = individualCustomerService;
    }

    //controller de ayrı bir end point oluşturup rent carı ekledikten sonra orderadditional'ı daha sonra ekle
    @Override
    public Result addForIndividual(CreateRentCarForIndividualRequest createRentCarForIndividualRequest) throws BusinessException {
        this.carMaintenanceService.checkIfCarMaintenanceReturnDateByCarId(createRentCarForIndividualRequest.getCarId());
        checkIfRentCarReturnDateByCarId(createRentCarForIndividualRequest.getCarId());
        this.individualCustomerService.checkIfIndividualCustomerIdExists(createRentCarForIndividualRequest.getCustomer_UserId());
        this.carService.checkIfCarExist(createRentCarForIndividualRequest.getCarId());
        this.cityService.checkIfCityIdExists(createRentCarForIndividualRequest.getFromCityId());
        this.cityService.checkIfCityIdExists(createRentCarForIndividualRequest.getToCityId());

        RentCar rentCar = this.modelMapperService.forRequest().map(createRentCarForIndividualRequest, RentCar.class);

        isCarReturnedFromRent(rentCar.getCar().getCarId(), rentCar.getReturnDistance());
        rentCar.setStartDistance(this.carService.getById(rentCar.getCar().getCarId()).getData().getDistance());

        rentCar.setRentCarId(0);
        this.rentCarDao.save(rentCar);

        return new SuccessResult("Added");
    }

    @Override
    public Result addForCorporate(CreateRentCarForCorporateRequest createRentCarForCorporateRequest) throws BusinessException {
        this.carMaintenanceService.checkIfCarMaintenanceReturnDateByCarId(createRentCarForCorporateRequest.getCarId());
        checkIfRentCarReturnDateByCarId(createRentCarForCorporateRequest.getCarId());
        this.corporateCustomerService.checkIfCorporateCustomerIdExists(createRentCarForCorporateRequest.getCustomer_UserId());
        this.carService.checkIfCarExist(createRentCarForCorporateRequest.getCarId());
        this.cityService.checkIfCityIdExists(createRentCarForCorporateRequest.getFromCityId());
        this.cityService.checkIfCityIdExists(createRentCarForCorporateRequest.getToCityId());
        RentCar rentCar = this.modelMapperService.forRequest().map(createRentCarForCorporateRequest, RentCar.class);

        isCarReturnedFromRent(rentCar.getCar().getCarId(), rentCar.getReturnDistance());
        rentCar.setStartDistance(this.carService.getById(rentCar.getCar().getCarId()).getData().getDistance());

        rentCar.setRentCarId(0);
        this.rentCarDao.save(rentCar);

        return new SuccessResult("Added");
    }

    @Override
    public Result update(UpdateRentCarRequest updateRentCarRequest) throws BusinessException {
        checkIfRentCarExists(updateRentCarRequest.getRentCarId());
        checkIfRentCarReturnDateByCarId(updateRentCarRequest.getCarId());

        this.carService.checkIfCarExist(updateRentCarRequest.getCarId());
        this.carMaintenanceService.checkIfCarMaintenanceReturnDateByCarId(updateRentCarRequest.getCarId());
        this.cityService.checkIfCityIdExists(updateRentCarRequest.getFromCityId());
        this.cityService.checkIfCityIdExists(updateRentCarRequest.getToCityId());


        RentCar rentCar = this.modelMapperService.forRequest().map(updateRentCarRequest, RentCar.class);

        isCarReturnedFromRent(rentCar.getCar().getCarId(), rentCar.getReturnDistance());

        this.rentCarDao.save(rentCar);

        return new SuccessResult("updated");
    }

    @Override
    public Result delete(DeleteRentCarRequest deleteRentCarRequest) throws BusinessException {
        checkIfRentCarExists(deleteRentCarRequest.getRentCarId());

        RentCar rentCar = this.modelMapperService.forRequest().map(deleteRentCarRequest, RentCar.class);
        this.rentCarDao.deleteById(rentCar.getRentCarId());
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
    public DataResult<List<RentCarListDto>> getRentCarsByCarId(int carId) throws BusinessException {
        this.carService.checkIfCarExist(carId);

        List<RentCar> result = this.rentCarDao.getRentCarByCar_CarId(carId);
        List<RentCarListDto> response = result.stream()
                .map(rentCar -> this.modelMapperService.forDto().map(rentCar, RentCarListDto.class))
                .collect(Collectors.toList());
        return new SuccessDataResult<List<RentCarListDto>>(response, "Listelendi");
    }

    @Override
    public DataResult<RentCarGetDto> getById(int rentCarId) throws BusinessException {
        checkIfRentCarExists(rentCarId);

        RentCar result = this.rentCarDao.getById(rentCarId);
        RentCarGetDto response = this.modelMapperService.forDto().map(result, RentCarGetDto.class);
        return new SuccessDataResult<RentCarGetDto>(response, "Listelendi..");
    }


    public void checkIfRentCarReturnDateByCarId(int carId) throws BusinessException {
        if (this.rentCarDao.getRentCarByCar_CarIdAndDateOfReceiptIsNull(carId) != null) {
            throw new BusinessException("Araç Kiralandı..");
        }
    }

    public void checkIfRentCarExists(int rentCarId) throws BusinessException {
        if (!this.rentCarDao.existsByRentCarId(rentCarId)) {
            throw new BusinessException(rentCarId + " No'lu Araç Kiralama Bulunamadı");
        }
    }


    private void isCarReturnedFromRent(int carId, double returnDistance) throws BusinessException {
        if (returnDistance > 0) {
            this.carService.isCarReturnedFromRent(carId, returnDistance);
        }
    }



    /* public double invoiceTotalFeeCalculatorByRentId(int rentCarId) throws BusinessException {
        checkIfRentCarExists(rentCarId);

        RentCar rentCar = this.rentCarDao.getById(rentCarId);
        List<Integer> additionalList = this.orderedAdditionalService.getAdditionalIdsByRentId(rentCarId);

        double additionalTotalFee = this.additionalService.totalAdditionalFeeCalculator(additionalList);


        double carDayOfDailyPriceTotalFee = this.carService.totalCarDailyPriceCalculator
                (rentCar.getCar().getCarId(), rentCar.getDateOfIssue(), rentCar.getDateOfReceipt());

        double differentCityTotalFee = rentCar.getToCity().getId() == rentCar.getFromCity().getId() ? 0 : 750;

        return additionalTotalFee + carDayOfDailyPriceTotalFee + differentCityTotalFee;
    }*/


}
