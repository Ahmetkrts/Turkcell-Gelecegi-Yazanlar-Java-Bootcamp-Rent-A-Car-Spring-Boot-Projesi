package com.turkcell.rentACar.business.abstracts;

import com.turkcell.rentACar.business.dtos.creditCard.CreditCardGetDto;
import com.turkcell.rentACar.business.dtos.creditCard.CreditCardListDto;
import com.turkcell.rentACar.business.request.creditCard.CreateCreditCardRequest;
import com.turkcell.rentACar.business.request.creditCard.DeleteCreditCardRequest;
import com.turkcell.rentACar.business.request.creditCard.UpdateCreditCardRequest;
import com.turkcell.rentACar.core.exception.BusinessException;
import com.turkcell.rentACar.core.result.DataResult;
import com.turkcell.rentACar.core.result.Result;

import java.util.List;

public interface CreditCardService {

    Result add(CreateCreditCardRequest createCreditCardRequest) throws BusinessException;

    DataResult<List<CreditCardListDto>> getAll();

    DataResult<CreditCardGetDto> getById(int creditCardId) throws BusinessException;

    Result update(UpdateCreditCardRequest updateCreditCardRequest) throws BusinessException;

    Result delete(DeleteCreditCardRequest deleteCreditCardRequest) throws BusinessException;
}
