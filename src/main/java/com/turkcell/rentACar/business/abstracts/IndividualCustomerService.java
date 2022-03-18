package com.turkcell.rentACar.business.abstracts;

import com.turkcell.rentACar.business.dtos.individualCustomer.IndividualCustomerGetDto;
import com.turkcell.rentACar.business.dtos.individualCustomer.IndividualCustomerListDto;
import com.turkcell.rentACar.business.request.individualCustomer.CreateIndividualCustomerRequest;
import com.turkcell.rentACar.business.request.individualCustomer.DeleteIndividualCustomerRequest;
import com.turkcell.rentACar.business.request.individualCustomer.UpdateIndividualCustomerRequest;
import com.turkcell.rentACar.core.exception.BusinessException;
import com.turkcell.rentACar.core.result.DataResult;
import com.turkcell.rentACar.core.result.Result;

import java.util.List;

public interface IndividualCustomerService {
    Result add(CreateIndividualCustomerRequest createIndividualCustomerRequest) throws BusinessException;

    Result update(UpdateIndividualCustomerRequest updateIndividualCustomerRequest) throws BusinessException;

    Result delete(DeleteIndividualCustomerRequest deleteIndividualCustomerRequest) throws BusinessException;

    DataResult<List<IndividualCustomerListDto>> getAll();

    DataResult<IndividualCustomerGetDto> getById(int individualCustomerId) throws BusinessException;

    void checkIfIndividualCustomerIdExists(int IndividualCustomerId) throws BusinessException;
}
