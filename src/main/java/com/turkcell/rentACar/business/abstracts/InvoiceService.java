package com.turkcell.rentACar.business.abstracts;

import com.turkcell.rentACar.business.dtos.invoice.InvoiceGetDto;
import com.turkcell.rentACar.business.dtos.invoice.InvoiceListDto;
import com.turkcell.rentACar.business.request.invoice.CreateInvoiceRequest;
import com.turkcell.rentACar.business.request.invoice.DeleteInvoiceRequest;
import com.turkcell.rentACar.business.request.invoice.UpdateInvoiceRequest;
import com.turkcell.rentACar.core.exception.BusinessException;
import com.turkcell.rentACar.core.result.DataResult;
import com.turkcell.rentACar.core.result.Result;

import java.time.LocalDate;
import java.util.List;

public interface InvoiceService {

    Result add(CreateInvoiceRequest createInvoiceRequest) throws BusinessException;

    Result update(UpdateInvoiceRequest updateInvoiceRequest) throws BusinessException;

    Result delete(DeleteInvoiceRequest deleteInvoiceRequest) throws BusinessException;

    DataResult<List<InvoiceListDto>> getAll();

    DataResult<InvoiceGetDto> getById(int invoiceId) throws BusinessException;

    DataResult<List<InvoiceListDto>> getByCustomerId(int customerId) throws BusinessException;

    DataResult<List<InvoiceListDto>> getByBetweenStartingAndEndingDates(LocalDate startingDate, LocalDate endingDate) throws BusinessException;


}
