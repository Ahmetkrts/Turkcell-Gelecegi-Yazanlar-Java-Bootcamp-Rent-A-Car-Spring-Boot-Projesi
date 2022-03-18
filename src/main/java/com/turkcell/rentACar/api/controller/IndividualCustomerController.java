package com.turkcell.rentACar.api.controller;

import com.turkcell.rentACar.business.abstracts.IndividualCustomerService;
import com.turkcell.rentACar.business.dtos.individualCustomer.IndividualCustomerGetDto;
import com.turkcell.rentACar.business.dtos.individualCustomer.IndividualCustomerListDto;
import com.turkcell.rentACar.business.request.individualCustomer.CreateIndividualCustomerRequest;
import com.turkcell.rentACar.business.request.individualCustomer.DeleteIndividualCustomerRequest;
import com.turkcell.rentACar.business.request.individualCustomer.UpdateIndividualCustomerRequest;
import com.turkcell.rentACar.core.exception.BusinessException;
import com.turkcell.rentACar.core.result.DataResult;
import com.turkcell.rentACar.core.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/individualCustomers")
public class IndividualCustomerController {
    private IndividualCustomerService individualCustomerService;

    @Autowired
    public IndividualCustomerController(IndividualCustomerService individualCustomerService) {
        this.individualCustomerService = individualCustomerService;
    }

    @PostMapping("/add")
    Result add(@RequestBody @Valid CreateIndividualCustomerRequest createIndividualCustomerRequest) throws BusinessException {
        return this.individualCustomerService.add(createIndividualCustomerRequest);
    }

    @PutMapping("/update")
    Result update(@RequestBody @Valid UpdateIndividualCustomerRequest updateIndividualCustomerRequest) throws BusinessException {
        return this.individualCustomerService.update(updateIndividualCustomerRequest);
    }

    @DeleteMapping("/delete")
    Result delete(@RequestBody DeleteIndividualCustomerRequest deleteIndividualCustomerRequest) throws BusinessException {
        return this.individualCustomerService.delete(deleteIndividualCustomerRequest);
    }

    @GetMapping("/getAll")
    DataResult<List<IndividualCustomerListDto>> getAll() {
        return this.individualCustomerService.getAll();
    }

    @GetMapping("/getById")
    DataResult<IndividualCustomerGetDto> getById(@RequestParam int individualCustomerId) throws BusinessException {
        return this.individualCustomerService.getById(individualCustomerId);
    }
}
