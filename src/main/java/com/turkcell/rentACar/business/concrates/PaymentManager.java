package com.turkcell.rentACar.business.concrates;

import com.turkcell.rentACar.business.abstracts.*;
import com.turkcell.rentACar.business.adapters.posAdapters.abstracts.BankService;
import com.turkcell.rentACar.business.constants.messages.BusinessMessages;
import com.turkcell.rentACar.business.dtos.payment.PaymentGetDto;
import com.turkcell.rentACar.business.dtos.payment.PaymentListDto;
import com.turkcell.rentACar.business.request.creditCard.CreateCreditCardForPaymentRequest;
import com.turkcell.rentACar.business.request.creditCard.CreateCreditCardRequest;
import com.turkcell.rentACar.business.request.invoice.CreateInvoiceRequest;
import com.turkcell.rentACar.business.request.payment.CreateExtraPaymentRequest;
import com.turkcell.rentACar.business.request.payment.CreatePaymentRequest;
import com.turkcell.rentACar.business.request.payment.DeletePaymentRequest;
import com.turkcell.rentACar.business.request.payment.UpdatePaymentRequest;
import com.turkcell.rentACar.core.exception.BusinessException;
import com.turkcell.rentACar.core.mapping.ModelMapperService;
import com.turkcell.rentACar.core.result.DataResult;
import com.turkcell.rentACar.core.result.Result;
import com.turkcell.rentACar.core.result.SuccessDataResult;
import com.turkcell.rentACar.core.result.SuccessResult;
import com.turkcell.rentACar.dataAccess.abstracts.PaymentDao;
import com.turkcell.rentACar.entities.concrates.Invoice;
import com.turkcell.rentACar.entities.concrates.Payment;
import com.turkcell.rentACar.entities.concrates.RentCar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PaymentManager implements PaymentService {

    private PaymentDao paymentDao;
    private ModelMapperService modelMapperService;
    private BankService bankService;
    private OrderedAdditionalService orderedAdditionalService;
    private AdditionalService additionalService;
    private CarService carService;
    private InvoiceService invoiceService;
    private RentCarService rentCarService;
    private CustomerService customerService;
    private CreditCardService creditCardService;

    @Autowired
    public PaymentManager(PaymentDao paymentDao, ModelMapperService modelMapperService,
                          BankService bankService,
                          OrderedAdditionalService orderedAdditionalService,
                          AdditionalService additionalService,
                          CarService carService,
                          InvoiceService invoiceService,
                          RentCarService rentCarService,
                          CustomerService customerService,
                          CreditCardService creditCardService) {
        this.paymentDao = paymentDao;
        this.modelMapperService = modelMapperService;
        this.bankService = bankService;
        this.orderedAdditionalService = orderedAdditionalService;
        this.additionalService = additionalService;
        this.carService = carService;
        this.invoiceService = invoiceService;
        this.rentCarService = rentCarService;
        this.customerService = customerService;
        this.creditCardService = creditCardService;
    }

    @Override
    public Result add(CreatePaymentRequest createPaymentRequest) throws BusinessException {
        double totalFee = totalFeeCalculator(createPaymentRequest);
        Integer invoiceId = PaymentSuccessor(createPaymentRequest, totalFee);
        Invoice invoice = new Invoice();
        invoice.setInvoiceId(invoiceId);
        Payment payment = new Payment(0, totalFee, invoice);
        this.paymentDao.save(payment);
        return new SuccessResult(BusinessMessages.ADDED);
    }

    @Override
    public Result update(UpdatePaymentRequest updatePaymentRequest) throws BusinessException {

        this.invoiceService.checkIfInvoiceIdExists(updatePaymentRequest.getInvoiceId());
        checkIfPaymentIdExist(updatePaymentRequest.getPaymentId());
        Payment payment = this.modelMapperService.forRequest().map(updatePaymentRequest, Payment.class);
        this.paymentDao.save(payment);
        return new SuccessResult(BusinessMessages.UPDATED);
    }

    @Override
    public Result delete(DeletePaymentRequest deletePaymentRequest) throws BusinessException {
        checkIfPaymentIdExist(deletePaymentRequest.getPaymentId());
        Payment payment = this.modelMapperService.forRequest().map(deletePaymentRequest, Payment.class);
        this.paymentDao.deleteById(payment.getPaymentId());
        return new SuccessResult(BusinessMessages.DELETED);
    }

    @Override
    public DataResult<List<PaymentListDto>> getAll() {
        List<Payment> response = this.paymentDao.findAll();
        List<PaymentListDto> result = response
                .stream()
                .map(payment -> this.modelMapperService.forDto().map(payment, PaymentListDto.class))
                .collect(Collectors.toList());

        return new SuccessDataResult<>(result, BusinessMessages.LISTED);
    }

    @Override
    public DataResult<PaymentGetDto> getById(int paymentId) throws BusinessException {
        checkIfPaymentIdExist(paymentId);
        Payment payment = this.paymentDao.getById(paymentId);
        PaymentGetDto result = this.modelMapperService.forDto().map(payment, PaymentGetDto.class);
        return new SuccessDataResult<>(result, BusinessMessages.PAYMENT_LISTED);
    }


    @Transactional
    public Result extraDaysRentCarPayment(CreateExtraPaymentRequest createExtraPaymentRequest) throws BusinessException {
        double totalFee = extraDaysPaymentTotalFeeCalculator(createExtraPaymentRequest);
        checkIfPaymentDone(createExtraPaymentRequest.getCreateCreditCardForPaymentRequest(), totalFee);
        CreateInvoiceRequest createInvoiceRequest = new CreateInvoiceRequest(LocalDate.now(), totalFee, createExtraPaymentRequest.getCustomerId(), createExtraPaymentRequest.getRentCarId());

        Integer invoiceId = this.invoiceService.add(createInvoiceRequest).getData();
        Invoice invoice = new Invoice();
        invoice.setInvoiceId(invoiceId);
        Payment payment = new Payment(0, totalFee, invoice);
        this.paymentDao.save(payment);

        return new SuccessResult(BusinessMessages.ADDED);
    }

    private double extraDaysPaymentTotalFeeCalculator(CreateExtraPaymentRequest createExtraPaymentRequest) throws BusinessException {
        double carDailyPrice = this.carService.totalCarDailyPriceCalculator(createExtraPaymentRequest.getCarId(), createExtraPaymentRequest.getDateOfReceipt(), createExtraPaymentRequest.getCarReturnDate());
        List<Integer> additionalIds = this.orderedAdditionalService.getAdditionalIdsByRentId(createExtraPaymentRequest.getRentCarId());
        double additionalDailyPrice = this.additionalService.totalAdditionalFeeCalculator(additionalIds);
        return carDailyPrice + additionalDailyPrice;
    }

    @Override
    public void checkIfPaymentIdExist(int paymentId) throws BusinessException {
        if (this.paymentDao.existsById(paymentId)) {
            throw new BusinessException(paymentId + BusinessMessages.PAYMENT_NOT_FOUND);
        }
    }

    private void checkIfCreditCardSave(CreatePaymentRequest createPaymentRequest) throws BusinessException {
        CreateCreditCardRequest request = this.modelMapperService.forRequest().map(createPaymentRequest.getCreateCreditCardForPaymentRequest(), CreateCreditCardRequest.class);
        request.setCustomer_UserId(createPaymentRequest.getRentCarRequest().getCustomer_UserId());

        if (createPaymentRequest.isSaveCreditCard()) {

            this.creditCardService.add(request);
        }
    }

    private double totalFeeCalculator(CreatePaymentRequest createPaymentRequest) throws BusinessException {
        this.customerService.checkIfCustomerIdExist(createPaymentRequest.getRentCarRequest().getCustomer_UserId());
        this.carService.checkIfCarExist(createPaymentRequest.getRentCarRequest().getCarId());

        RentCar rentCar = this.modelMapperService.forRequest().map(createPaymentRequest.getRentCarRequest(), RentCar.class);
        double differentCityAndCarDailyPrice = this.rentCarService.totalRentCarDailyPriceAndDifferntCityCalculator(rentCar);
        double additionalTotalFee = this.additionalService.totalAdditionalFeeCalculator(createPaymentRequest.getAdditionalList());
        return differentCityAndCarDailyPrice + additionalTotalFee;
    }

    private void checkIfPaymentDone(CreateCreditCardForPaymentRequest creditCard, double totalFee) throws BusinessException {
        if (!this.bankService.payment(creditCard, totalFee)) {
            throw new BusinessException(BusinessMessages.PAYMENT_FAILED);
        }
    }

    @Transactional
    Integer PaymentSuccessor(CreatePaymentRequest createPaymentRequest, double totalFee) throws BusinessException {
        CreateCreditCardForPaymentRequest creditCard = createPaymentRequest.getCreateCreditCardForPaymentRequest();
        checkIfPaymentDone(creditCard, totalFee);
        checkIfCreditCardSave(createPaymentRequest);

        RentCar rentCar = this.modelMapperService.forRequest().map(createPaymentRequest.getRentCarRequest(), RentCar.class);
        Integer createdRentCarId = this.rentCarService.addForIndividual(createPaymentRequest.getRentCarRequest()).getData();
        CreateInvoiceRequest createInvoiceRequest = new CreateInvoiceRequest(LocalDate.now(), totalFee, rentCar.getCustomer().getUserId(), createdRentCarId);
        Integer invoiceId = this.invoiceService.add(createInvoiceRequest).getData();
        this.orderedAdditionalService.addAdditionals(createdRentCarId, createPaymentRequest.getAdditionalList());
        return invoiceId;
    }

}
