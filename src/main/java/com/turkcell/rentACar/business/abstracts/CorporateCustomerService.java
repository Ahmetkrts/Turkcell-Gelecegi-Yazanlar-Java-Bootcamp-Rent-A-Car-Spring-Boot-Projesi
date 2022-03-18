package com.turkcell.rentACar.business.abstracts;

import com.turkcell.rentACar.business.dtos.corporateCustomer.CorporateCustomerGetDto;
import com.turkcell.rentACar.business.dtos.corporateCustomer.CorporateCustomerListDto;
import com.turkcell.rentACar.business.request.corporateCustomer.CreateCorporateCustomerRequest;
import com.turkcell.rentACar.business.request.corporateCustomer.DeleteCorporateCustomerRequest;
import com.turkcell.rentACar.business.request.corporateCustomer.UpdateCorporateCustomerRequest;
import com.turkcell.rentACar.core.exception.BusinessException;
import com.turkcell.rentACar.core.result.DataResult;
import com.turkcell.rentACar.core.result.Result;

import java.util.List;

public interface CorporateCustomerService {
    Result add(CreateCorporateCustomerRequest createCorporateCustomerRequest) throws BusinessException;

    Result update(UpdateCorporateCustomerRequest UpdateCorporateCustomerRequest) throws BusinessException;

    Result delete(DeleteCorporateCustomerRequest deleteCorporateCustomerRequest) throws BusinessException;

    DataResult<List<CorporateCustomerListDto>> getAll();

    DataResult<CorporateCustomerGetDto> getById(int corporateCustomerId) throws BusinessException;

    void checkIfCorporateCustomerIdExists(int CorporateCustomerId) throws BusinessException;
}
