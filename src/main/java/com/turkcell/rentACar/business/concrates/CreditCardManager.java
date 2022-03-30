package com.turkcell.rentACar.business.concrates;

import com.turkcell.rentACar.business.abstracts.CreditCardService;
import com.turkcell.rentACar.business.abstracts.CustomerService;
import com.turkcell.rentACar.business.constants.messages.BusinessMessages;
import com.turkcell.rentACar.business.dtos.creditCard.CreditCardGetDto;
import com.turkcell.rentACar.business.dtos.creditCard.CreditCardListDto;
import com.turkcell.rentACar.business.request.creditCard.CreateCreditCardRequest;
import com.turkcell.rentACar.business.request.creditCard.DeleteCreditCardRequest;
import com.turkcell.rentACar.business.request.creditCard.UpdateCreditCardRequest;
import com.turkcell.rentACar.core.exception.BusinessException;
import com.turkcell.rentACar.core.mapping.ModelMapperService;
import com.turkcell.rentACar.core.result.DataResult;
import com.turkcell.rentACar.core.result.Result;
import com.turkcell.rentACar.core.result.SuccessDataResult;
import com.turkcell.rentACar.core.result.SuccessResult;
import com.turkcell.rentACar.dataAccess.abstracts.CreditCardDao;
import com.turkcell.rentACar.entities.concrates.CreditCard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CreditCardManager implements CreditCardService {

    private CreditCardDao creditCardDao;
    private ModelMapperService modelMapperService;
    private CustomerService customerService;

    @Autowired
    public CreditCardManager(CreditCardDao creditCardDao, ModelMapperService modelMapperService,
                             @Lazy CustomerService customerService) {
        this.creditCardDao = creditCardDao;
        this.modelMapperService = modelMapperService;
        this.customerService = customerService;
    }

    @Override
    public Result add(CreateCreditCardRequest createCreditCardRequest) throws BusinessException {
        this.customerService.checkIfCustomerIdExist(createCreditCardRequest.getCustomer_UserId());
        checkIfCreditCardNoExist(createCreditCardRequest.getCardNo());
        CreditCard creditCard = this.modelMapperService.forRequest().map(createCreditCardRequest, CreditCard.class);
        creditCard.setCreditCardId(0);
        System.out.println(creditCard.getCustomer().getUserId());
        this.creditCardDao.save(creditCard);
        return new SuccessResult(BusinessMessages.ADDED);
    }

    @Override
    public Result update(UpdateCreditCardRequest updateCreditCardRequest) throws BusinessException {
        checkIfCreditCardByCardIdExist(updateCreditCardRequest.getCreditCardId());
        this.customerService.checkIfCustomerIdExist(updateCreditCardRequest.getCustomer_UserId());
        checkIfCreditCardNoExist(updateCreditCardRequest.getCardNo());
        CreditCard creditCard = this.modelMapperService.forRequest().map(updateCreditCardRequest, CreditCard.class);
        this.creditCardDao.save(creditCard);
        return new SuccessResult(BusinessMessages.UPDATED);
    }

    @Override
    public Result delete(DeleteCreditCardRequest deleteCreditCardRequest) throws BusinessException {
        checkIfCreditCardByCardIdExist(deleteCreditCardRequest.getCreditCardId());

        CreditCard creditCard = this.modelMapperService.forRequest().map(deleteCreditCardRequest, CreditCard.class);
        this.creditCardDao.deleteById(creditCard.getCreditCardId());
        return new SuccessResult(BusinessMessages.DELETED);

    }

    @Override
    public DataResult<List<CreditCardListDto>> getAll() {
        List<CreditCard> response = this.creditCardDao.findAll();
        List<CreditCardListDto> result = response
                .stream()
                .map(creditCard -> this.modelMapperService.forDto().map(creditCard, CreditCardListDto.class))
                .collect(Collectors.toList());
        return new SuccessDataResult<>(result, BusinessMessages.LISTED);
    }

    @Override
    public DataResult<CreditCardGetDto> getById(int creditCardId) throws BusinessException {

        CreditCard creditCard = this.creditCardDao.getById(creditCardId);
        CreditCardGetDto result = this.modelMapperService.forDto().map(creditCard, CreditCardGetDto.class);

        return new SuccessDataResult<>(result, creditCardId + BusinessMessages.CREDİT_CARD_LISTED);
    }


    public void checkIfCreditCardNoExist(String creditCardNo) throws BusinessException {
        if (!this.creditCardDao.existsByCardNo(creditCardNo)) {
            throw new BusinessException(creditCardNo + BusinessMessages.CREDİT_SAME_CARD_NO);
        }
    }

    public void checkIfCreditCardByCardIdExist(int creditCardId) throws BusinessException {
        if (!this.creditCardDao.existsById(creditCardId)) {
            throw new BusinessException(creditCardId + BusinessMessages.CREDİT_CARD_NOT_FOUND);
        }
    }

}
