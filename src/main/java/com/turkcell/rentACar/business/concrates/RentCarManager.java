package com.turkcell.rentACar.business.concrates;

import com.turkcell.rentACar.business.abstracts.*;
import com.turkcell.rentACar.business.constants.messages.BusinessMessages;
import com.turkcell.rentACar.business.dtos.rentCar.RentCarGetDto;
import com.turkcell.rentACar.business.dtos.rentCar.RentCarListDto;
import com.turkcell.rentACar.business.request.payment.CreateExtraPaymentRequest;
import com.turkcell.rentACar.business.request.rentCar.CreateRentCarRequest;
import com.turkcell.rentACar.business.request.rentCar.DeleteRentCarRequest;
import com.turkcell.rentACar.business.request.rentCar.UpdateRentCarRequest;
import com.turkcell.rentACar.business.request.rentCar.UpdateReturnCarRequest;
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

import java.time.temporal.ChronoUnit;
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
    private CustomerService customerService;
    private PaymentService paymentService;


    @Autowired
    public RentCarManager(RentCarDao rentCarDao,
                          ModelMapperService modelMapperService,
                          @Lazy CarMaintenanceService carMaintenanceService,
                          @Lazy CarService carService,
                          @Lazy CityService cityService,
                          @Lazy CorporateCustomerService corporateCustomerService,
                          @Lazy IndividualCustomerService individualCustomerService,
                          @Lazy CustomerService customerService,
                          @Lazy PaymentService paymentService) {
        this.rentCarDao = rentCarDao;
        this.modelMapperService = modelMapperService;
        this.carMaintenanceService = carMaintenanceService;
        this.carService = carService;
        this.cityService = cityService;
        this.corporateCustomerService = corporateCustomerService;
        this.individualCustomerService = individualCustomerService;
        this.customerService = customerService;
        this.paymentService = paymentService;
    }

    //controller de ayrı bir end point oluşturup rent carı ekledikten sonra orderadditional'ı daha sonra ekle

    @Override
    public DataResult<Integer> addForIndividual(CreateRentCarRequest createRentCarRequest) throws BusinessException {
        this.carMaintenanceService.checkIfCarMaintenanceReturnDateByCarId(createRentCarRequest.getCarId());
        checkIfRentCarReturnDateByCarId(createRentCarRequest.getCarId());
        this.individualCustomerService.checkIfIndividualCustomerIdExists(createRentCarRequest.getCustomer_UserId());
        this.carService.checkIfCarExist(createRentCarRequest.getCarId());
        this.cityService.checkIfCityIdExists(createRentCarRequest.getFromCityId());
        this.cityService.checkIfCityIdExists(createRentCarRequest.getToCityId());

        RentCar rentCar = this.modelMapperService.forRequest().map(createRentCarRequest, RentCar.class);


        rentCar.setStartDistance(this.carService.getById(rentCar.getCar().getCarId()).getData().getDistance());

        rentCar.setRentCarId(0);
        RentCar returnRentcar = this.rentCarDao.save(rentCar);

        return new SuccessDataResult<Integer>(returnRentcar.getRentCarId(), BusinessMessages.ADDED);
    }

    @Override
    public Result addForCorporate(CreateRentCarRequest createRentCarRequest) throws BusinessException {
        this.carMaintenanceService.checkIfCarMaintenanceReturnDateByCarId(createRentCarRequest.getCarId());
        checkIfRentCarReturnDateByCarId(createRentCarRequest.getCarId());
        this.corporateCustomerService.checkIfCorporateCustomerIdExists(createRentCarRequest.getCustomer_UserId());
        this.carService.checkIfCarExist(createRentCarRequest.getCarId());
        this.cityService.checkIfCityIdExists(createRentCarRequest.getFromCityId());
        this.cityService.checkIfCityIdExists(createRentCarRequest.getToCityId());

        RentCar rentCar = this.modelMapperService.forRequest().map(createRentCarRequest, RentCar.class);

        rentCar.setStartDistance(this.carService.getById(rentCar.getCar().getCarId()).getData().getDistance());

        rentCar.setRentCarId(0);
        this.rentCarDao.save(rentCar);

        return new SuccessResult(BusinessMessages.ADDED);
    }

    @Override
    public Result update(UpdateRentCarRequest updateRentCarRequest) throws BusinessException {
        checkIfRentCarExists(updateRentCarRequest.getRentCarId());
        checkIfRentCarReturnDateByCarId(updateRentCarRequest.getCarId());
        this.carService.checkIfCarExist(updateRentCarRequest.getCarId());
        this.carMaintenanceService.checkIfCarMaintenanceReturnDateByCarId(updateRentCarRequest.getCarId());
        this.cityService.checkIfCityIdExists(updateRentCarRequest.getFromCityId());
        this.cityService.checkIfCityIdExists(updateRentCarRequest.getToCityId());
        this.customerService.checkIfCustomerIdExist(updateRentCarRequest.getCustomer_UserId());


        RentCar rentCar = this.modelMapperService.forRequest().map(updateRentCarRequest, RentCar.class);
        this.rentCarDao.save(rentCar);

        return new SuccessResult(BusinessMessages.UPDATED);
    }

    @Override
    public Result updateForReturnCar(UpdateReturnCarRequest updateReturnCarRequest) throws BusinessException {
        this.carService.checkIfCarExist(updateReturnCarRequest.getCarId());
        RentCar rentCar = this.rentCarDao.getRentCarByCar_CarIdOrderByRentCarIdDesc(updateReturnCarRequest.getCarId()).get(0);
        int daysBetweenTwoDates = (int) ChronoUnit.DAYS.between(rentCar.getDateOfReceipt(), updateReturnCarRequest.getCarReturnDate());
        if (daysBetweenTwoDates > 0) {
            CreateExtraPaymentRequest createExtraPaymentRequest = new
                    CreateExtraPaymentRequest(rentCar.getRentCarId(),
                    rentCar.getCustomer().getUserId(),
                    rentCar.getCar().getCarId(),
                    rentCar.getDateOfReceipt(),
                    updateReturnCarRequest.getCarReturnDate(),
                    updateReturnCarRequest.getCreateCreditCardForPaymentRequest());

            this.paymentService.extraDaysRentCarPayment(createExtraPaymentRequest);

        }
        isCarReturnedFromRent(updateReturnCarRequest.getCarId(), updateReturnCarRequest.getReturnDistance());
        rentCar.setCarReturnDate(updateReturnCarRequest.getCarReturnDate());
        rentCar.setReturnDistance(updateReturnCarRequest.getReturnDistance());
        this.rentCarDao.save(rentCar);
        return new SuccessResult(BusinessMessages.UPDATED);
    }

    @Override
    public Result delete(DeleteRentCarRequest deleteRentCarRequest) throws BusinessException {
        checkIfRentCarExists(deleteRentCarRequest.getRentCarId());

        RentCar rentCar = this.modelMapperService.forRequest().map(deleteRentCarRequest, RentCar.class);
        this.rentCarDao.deleteById(rentCar.getRentCarId());
        return new SuccessResult(BusinessMessages.DELETED);
    }

    @Override
    public DataResult<List<RentCarListDto>> getAll() {
        List<RentCar> result = this.rentCarDao.findAll();
        List<RentCarListDto> response = result.stream()
                .map(rentCar -> this.modelMapperService.forDto().map(rentCar, RentCarListDto.class))
                .collect(Collectors.toList());
        return new SuccessDataResult<List<RentCarListDto>>(response, BusinessMessages.LISTED);
    }

    @Override
    public DataResult<List<RentCarListDto>> getRentCarsByCarId(int carId) throws BusinessException {
        this.carService.checkIfCarExist(carId);

        List<RentCar> result = this.rentCarDao.getRentCarByCar_CarId(carId);
        List<RentCarListDto> response = result.stream()
                .map(rentCar -> this.modelMapperService.forDto().map(rentCar, RentCarListDto.class))
                .collect(Collectors.toList());
        return new SuccessDataResult<List<RentCarListDto>>(response, carId + BusinessMessages.RENT_BY_CAR_ID_LISTED);
    }

    @Override
    public DataResult<RentCarGetDto> getById(int rentCarId) throws BusinessException {
        checkIfRentCarExists(rentCarId);

        RentCar result = this.rentCarDao.getById(rentCarId);
        RentCarGetDto response = this.modelMapperService.forDto().map(result, RentCarGetDto.class);
        return new SuccessDataResult<RentCarGetDto>(response, rentCarId + BusinessMessages.RENT_LISTED);
    }


    public void checkIfRentCarReturnDateByCarId(int carId) throws BusinessException {
        if (this.rentCarDao.getRentCarByCar_CarIdAndCarReturnDateIsNull(carId) != null) {
            throw new BusinessException(carId + BusinessMessages.RENT_CAR_RENTED);
        }
    }

    public void checkIfRentCarExists(int rentCarId) throws BusinessException {
        if (!this.rentCarDao.existsByRentCarId(rentCarId)) {
            throw new BusinessException(rentCarId + BusinessMessages.RENT_NOT_FOUND);
        }
    }


    private void isCarReturnedFromRent(int carId, double returnDistance) throws BusinessException {
        if (returnDistance > 0) {

            this.carService.isCarReturnFromRent(carId, returnDistance);
        }
    }

    public double totalRentCarDailyPriceAndDifferntCityCalculator(RentCar rentCar) throws BusinessException {
        this.cityService.checkIfCityIdExists(rentCar.getToCity().getId());
        this.cityService.checkIfCityIdExists(rentCar.getToCity().getId());
        this.carService.checkIfCarExist(rentCar.getCar().getCarId());

        double differentCity = rentCar.getToCity().getId() == rentCar.getFromCity().getId() ? 0 : 750;

        double carDayOfDailyPriceTotalFee = this.carService.totalCarDailyPriceCalculator
                (rentCar.getCar().getCarId(), rentCar.getDateOfIssue(), rentCar.getDateOfReceipt());

        return differentCity + carDayOfDailyPriceTotalFee;

    }


}
