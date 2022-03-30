package com.turkcell.rentACar.business.concrates;

import com.turkcell.rentACar.business.abstracts.CustomerService;
import com.turkcell.rentACar.business.abstracts.InvoiceService;
import com.turkcell.rentACar.business.abstracts.RentCarService;
import com.turkcell.rentACar.business.constants.messages.BusinessMessages;
import com.turkcell.rentACar.business.dtos.invoice.InvoiceGetDto;
import com.turkcell.rentACar.business.dtos.invoice.InvoiceListDto;
import com.turkcell.rentACar.business.request.invoice.CreateInvoiceRequest;
import com.turkcell.rentACar.business.request.invoice.DeleteInvoiceRequest;
import com.turkcell.rentACar.business.request.invoice.UpdateInvoiceRequest;
import com.turkcell.rentACar.core.exception.BusinessException;
import com.turkcell.rentACar.core.mapping.ModelMapperService;
import com.turkcell.rentACar.core.result.DataResult;
import com.turkcell.rentACar.core.result.Result;
import com.turkcell.rentACar.core.result.SuccessDataResult;
import com.turkcell.rentACar.core.result.SuccessResult;
import com.turkcell.rentACar.dataAccess.abstracts.InvoiceDao;
import com.turkcell.rentACar.entities.concrates.Invoice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class InvoiceManager implements InvoiceService {
    private InvoiceDao invoiceDao;
    private RentCarService rentCarService;
    private ModelMapperService modelMapperService;
    private CustomerService customerService;

    @Autowired
    public InvoiceManager(InvoiceDao invoiceDao,
                          ModelMapperService modelMapperService,
                          @Lazy RentCarService rentCarService,
                          @Lazy CustomerService customerService) {
        this.invoiceDao = invoiceDao;
        this.rentCarService = rentCarService;
        this.modelMapperService = modelMapperService;
        this.customerService = customerService;
    }

    @Override
    public DataResult<Integer> add(CreateInvoiceRequest createInvoiceRequest) throws BusinessException {

        this.customerService.checkIfCustomerIdExist(createInvoiceRequest.getCustomer_UserId());
        this.rentCarService.checkIfRentCarExists(createInvoiceRequest.getRentCarId());
        Invoice response = this.modelMapperService.forRequest().map(createInvoiceRequest, Invoice.class);

        response.setInvoiceId(0);
        Invoice invoice = this.invoiceDao.save(response);

        return new SuccessDataResult<Integer>(invoice.getInvoiceId(), BusinessMessages.ADDED);
    }

    @Override
    public Result update(UpdateInvoiceRequest updateInvoiceRequest) throws BusinessException {
        this.customerService.checkIfCustomerIdExist(updateInvoiceRequest.getCustomer_UserId());
        this.rentCarService.checkIfRentCarExists(updateInvoiceRequest.getRentCarId());
        checkIfInvoiceIdExists(updateInvoiceRequest.getInvoiceId());

        Invoice response = this.modelMapperService.forRequest().map(updateInvoiceRequest, Invoice.class);
        this.invoiceDao.save(response);
        return new SuccessResult(BusinessMessages.UPDATED);
    }

    @Override
    public Result delete(DeleteInvoiceRequest deleteInvoiceRequest) throws BusinessException {
        checkIfInvoiceIdExists(deleteInvoiceRequest.getInvoiceId());

        Invoice invoice = this.modelMapperService.forRequest().map(deleteInvoiceRequest, Invoice.class);
        this.invoiceDao.deleteById(invoice.getInvoiceId());
        return new SuccessResult(BusinessMessages.DELETED);
    }

    @Override
    public DataResult<List<InvoiceListDto>> getAll() {
        List<Invoice> response = this.invoiceDao.findAll();
        List<InvoiceListDto> result = response
                .stream()
                .map(invoice -> this.modelMapperService.forDto().map(invoice, InvoiceListDto.class))
                .collect(Collectors.toList());
        return new SuccessDataResult<>(result, BusinessMessages.LISTED);
    }

    @Override
    public DataResult<InvoiceGetDto> getById(int invoiceId) throws BusinessException {
        checkIfInvoiceIdExists(invoiceId);

        Invoice response = this.invoiceDao.getById(invoiceId);
        InvoiceGetDto result = this.modelMapperService.forDto().map(response, InvoiceGetDto.class);

        return new SuccessDataResult<>(result, invoiceId + BusinessMessages.INVOICE_LISTED);
    }

    @Override
    public DataResult<List<InvoiceListDto>> getByCustomerId(int customerId) throws BusinessException {
        checkIfCustomerIdExists(customerId);
        this.customerService.checkIfCustomerIdExist(customerId);

        List<Invoice> response = this.invoiceDao.getByCustomerUserId(customerId);
        List<InvoiceListDto> result = response
                .stream()
                .map(invoice -> this.modelMapperService.forDto().map(invoice, InvoiceListDto.class))
                .collect(Collectors.toList());
        return new SuccessDataResult<>(result, customerId + BusinessMessages.INVOICE_BY_CUSTOMER_LISTED);
    }

    @Override
    public DataResult<List<InvoiceListDto>> getByBetweenStartingAndEndingDates(LocalDate startingDate, LocalDate endingDate) throws BusinessException {
        checkIfTodayDateWithDateByDate(startingDate);
        checkIfTodayDateWithDateByDate(endingDate);
        checkIfStatingDateLessThanEndingDate(startingDate, endingDate);

        List<Invoice> response = this.invoiceDao.getByCreateDateBetween(startingDate, endingDate);
        List<InvoiceListDto> result = response
                .stream()
                .map(invoice -> this.modelMapperService.forDto().map(invoice, InvoiceListDto.class))
                .collect(Collectors.toList());
        return new SuccessDataResult<>(result, startingDate + " - " + endingDate + BusinessMessages.INVOICE_BETWEEN_DATES_LISTED);
    }

    public void checkIfInvoiceIdExists(int invoiceId) throws BusinessException {
        if (!this.invoiceDao.existsById(invoiceId)) {
            throw new BusinessException(invoiceId + BusinessMessages.INVOICE_NOT_FOUND);
        }
    }

    private void checkIfCustomerIdExists(int customerId) throws BusinessException {
        if (!this.invoiceDao.existsByCustomer_UserId(customerId)) {
            throw new BusinessException(customerId + BusinessMessages.CUSTOMER_NOT_FOUND);
        }
    }

    private void checkIfTodayDateWithDateByDate(LocalDate date) throws BusinessException {
        LocalDate today = LocalDate.now();
        if (today.isBefore(date)) {
            throw new BusinessException(BusinessMessages.INVOICE_CANNOT_SEARCH_FOR_THE_NEXT_DAYS);
        }
    }

    private void checkIfStatingDateLessThanEndingDate(LocalDate startingDate, LocalDate endingDate) throws BusinessException {
        if (startingDate.isAfter(endingDate)) {
            throw new BusinessException(BusinessMessages.INVOICE_STARTING_DATE_CANNOT_GREATER_THAN_END_DATE);
        }
    }


}
