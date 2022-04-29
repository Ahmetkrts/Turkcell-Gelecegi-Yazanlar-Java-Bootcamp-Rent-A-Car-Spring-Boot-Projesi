package com.turkcell.rentACar.api.controller;

import com.turkcell.rentACar.business.abstracts.InvoiceService;
import com.turkcell.rentACar.business.dtos.invoice.InvoiceGetDto;
import com.turkcell.rentACar.business.dtos.invoice.InvoiceListDto;
import com.turkcell.rentACar.business.request.invoice.CreateInvoiceRequest;
import com.turkcell.rentACar.business.request.invoice.DeleteInvoiceRequest;
import com.turkcell.rentACar.business.request.invoice.UpdateInvoiceRequest;
import com.turkcell.rentACar.core.exception.BusinessException;
import com.turkcell.rentACar.core.result.DataResult;
import com.turkcell.rentACar.core.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/Invoices")
public class InvoiceController {
    private InvoiceService invoiceService;

    @Autowired
    public InvoiceController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    /*@PostMapping("/add")
    Result add(@RequestBody @Valid CreateInvoiceRequest createInvoiceRequest) throws BusinessException {
        return this.invoiceService.add(createInvoiceRequest);
    }*/

    @PutMapping("/update")
    Result update(@RequestBody @Valid UpdateInvoiceRequest updateInvoiceRequest) throws BusinessException {
        return this.invoiceService.update(updateInvoiceRequest);
    }

    @DeleteMapping("/delete")
    Result delete(@RequestBody DeleteInvoiceRequest deleteInvoiceRequest) throws BusinessException {
        return this.invoiceService.delete(deleteInvoiceRequest);
    }

    @GetMapping("/getAll")
    DataResult<List<InvoiceListDto>> getAll() {
        return this.invoiceService.getAll();
    }

    @GetMapping("/getById")
    DataResult<InvoiceGetDto> getById(int invoiceId) throws BusinessException {
        return this.invoiceService.getById(invoiceId);
    }

    @GetMapping("/getByCustomerId")
    DataResult<List<InvoiceListDto>> getByCustomerId(@RequestParam int customerId) throws BusinessException {
        return this.invoiceService.getByCustomerId(customerId);
    }

    @GetMapping("/getByStartingAndEndingDate")
    DataResult<List<InvoiceListDto>> getByBetweenStartingAndEndingDates(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startingDate, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endingDate) throws BusinessException {
        return this.invoiceService.getByBetweenStartingAndEndingDates(startingDate, endingDate);
    }

}
