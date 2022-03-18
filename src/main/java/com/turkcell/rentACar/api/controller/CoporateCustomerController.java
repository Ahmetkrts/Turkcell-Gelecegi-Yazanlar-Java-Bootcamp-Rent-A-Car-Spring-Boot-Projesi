package com.turkcell.rentACar.api.controller;

import com.turkcell.rentACar.business.abstracts.CorporateCustomerService;
import com.turkcell.rentACar.business.dtos.corporateCustomer.CorporateCustomerGetDto;
import com.turkcell.rentACar.business.dtos.corporateCustomer.CorporateCustomerListDto;
import com.turkcell.rentACar.business.request.corporateCustomer.CreateCorporateCustomerRequest;
import com.turkcell.rentACar.business.request.corporateCustomer.DeleteCorporateCustomerRequest;
import com.turkcell.rentACar.business.request.corporateCustomer.UpdateCorporateCustomerRequest;
import com.turkcell.rentACar.core.exception.BusinessException;
import com.turkcell.rentACar.core.result.DataResult;
import com.turkcell.rentACar.core.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/corporateCustomers")
public class CoporateCustomerController {
    private CorporateCustomerService corporateCustomerService;

    @Autowired
    public CoporateCustomerController(CorporateCustomerService corporateCustomerService) {
        this.corporateCustomerService = corporateCustomerService;
    }

    @PostMapping("/add")
    Result add(@RequestBody @Valid CreateCorporateCustomerRequest createCorporateCustomerRequest) throws BusinessException {
        return this.corporateCustomerService.add(createCorporateCustomerRequest);
    }

    @PutMapping("/update")
    Result update(@RequestBody @Valid UpdateCorporateCustomerRequest updateCorporateCustomerRequest) throws BusinessException {
        return this.corporateCustomerService.update(updateCorporateCustomerRequest);
    }

    @DeleteMapping("/delete")
    Result delete(@RequestBody DeleteCorporateCustomerRequest deleteCorporateCustomerRequest) throws BusinessException {
        return this.corporateCustomerService.delete(deleteCorporateCustomerRequest);
    }

    @GetMapping("/getAll")
    DataResult<List<CorporateCustomerListDto>> getAll() {
        return this.corporateCustomerService.getAll();
    }

    @GetMapping("/getById")
    DataResult<CorporateCustomerGetDto> getById(@RequestParam int corporateCustomerId) throws BusinessException {
        return this.corporateCustomerService.getById(corporateCustomerId);
    }
}
