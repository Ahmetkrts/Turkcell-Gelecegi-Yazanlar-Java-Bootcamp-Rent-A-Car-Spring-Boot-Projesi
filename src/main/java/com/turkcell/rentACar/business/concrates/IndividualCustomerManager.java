package com.turkcell.rentACar.business.concrates;

import com.turkcell.rentACar.business.abstracts.IndividualCustomerService;
import com.turkcell.rentACar.business.constants.messages.BusinessMessages;
import com.turkcell.rentACar.business.dtos.individualCustomer.IndividualCustomerGetDto;
import com.turkcell.rentACar.business.dtos.individualCustomer.IndividualCustomerListDto;
import com.turkcell.rentACar.business.request.individualCustomer.CreateIndividualCustomerRequest;
import com.turkcell.rentACar.business.request.individualCustomer.DeleteIndividualCustomerRequest;
import com.turkcell.rentACar.business.request.individualCustomer.UpdateIndividualCustomerRequest;
import com.turkcell.rentACar.core.exception.BusinessException;
import com.turkcell.rentACar.core.mapping.ModelMapperService;
import com.turkcell.rentACar.core.result.DataResult;
import com.turkcell.rentACar.core.result.Result;
import com.turkcell.rentACar.core.result.SuccessDataResult;
import com.turkcell.rentACar.core.result.SuccessResult;
import com.turkcell.rentACar.dataAccess.abstracts.IndividualCustomerDao;
import com.turkcell.rentACar.entities.concrates.IndividualCustomer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class IndividualCustomerManager implements IndividualCustomerService {
    private IndividualCustomerDao individualCustomerDao;
    private ModelMapperService modelMapperService;

    @Autowired
    public IndividualCustomerManager(IndividualCustomerDao individualCustomerDao, ModelMapperService modelMapperService) {
        this.individualCustomerDao = individualCustomerDao;
        this.modelMapperService = modelMapperService;
    }

    @Override
    public Result add(CreateIndividualCustomerRequest createIndividualCustomerRequest) throws BusinessException {
        IndividualCustomer individualCustomer = this.modelMapperService.forRequest().map(createIndividualCustomerRequest, IndividualCustomer.class);
        this.individualCustomerDao.save(individualCustomer);
        return new SuccessResult(BusinessMessages.ADDED);
    }

    @Override
    public Result update(UpdateIndividualCustomerRequest updateIndividualCustomerRequest) throws BusinessException {
        checkIfIndividualCustomerIdExists(updateIndividualCustomerRequest.getUserId());

        IndividualCustomer individualCustomer = this.modelMapperService.forRequest().map(updateIndividualCustomerRequest, IndividualCustomer.class);
        this.individualCustomerDao.save(individualCustomer);
        return new SuccessResult(BusinessMessages.UPDATED);
    }

    @Override
    public Result delete(DeleteIndividualCustomerRequest deleteIndividualCustomerRequest) throws BusinessException {
        checkIfIndividualCustomerIdExists(deleteIndividualCustomerRequest.getUserId());

        IndividualCustomer individualCustomer = this.modelMapperService.forRequest().map(deleteIndividualCustomerRequest, IndividualCustomer.class);
        this.individualCustomerDao.deleteById(individualCustomer.getUserId());
        return new SuccessResult(BusinessMessages.DELETED);
    }

    @Override
    public DataResult<List<IndividualCustomerListDto>> getAll() {
        List<IndividualCustomer> response = this.individualCustomerDao.findAll();

        List<IndividualCustomerListDto> result = response
                .stream()
                .map(individualCustomer -> this.modelMapperService.forDto().map(individualCustomer, IndividualCustomerListDto.class))
                .collect(Collectors.toList());

        return new SuccessDataResult<List<IndividualCustomerListDto>>(result, BusinessMessages.LISTED);
    }

    @Override
    public DataResult<IndividualCustomerGetDto> getById(int individualCustomerId) throws BusinessException {
        checkIfIndividualCustomerIdExists(individualCustomerId);

        IndividualCustomer response = this.individualCustomerDao.getById(individualCustomerId);
        IndividualCustomerGetDto result = this.modelMapperService.forDto().map(response, IndividualCustomerGetDto.class);
        return new SuccessDataResult<>(result, individualCustomerId + BusinessMessages.INDIVIDUAL_LISTED);
    }

    public void checkIfIndividualCustomerIdExists(int IndividualCustomerId) throws BusinessException {
        if (!this.individualCustomerDao.existsById(IndividualCustomerId)) {
            throw new BusinessException(IndividualCustomerId + BusinessMessages.INDIVIDUAL_NOT_FOUND);
        }
    }
}
