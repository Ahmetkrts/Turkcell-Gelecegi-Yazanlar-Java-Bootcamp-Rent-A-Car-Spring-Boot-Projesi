package com.turkcell.rentACar.business.concrates;

import com.turkcell.rentACar.business.abstracts.IndividualCustomerService;
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
        return new SuccessResult("Eklendi");
    }

    @Override
    public Result update(UpdateIndividualCustomerRequest updateIndividualCustomerRequest) throws BusinessException {
        checkIfIndividualCustomerIdExists(updateIndividualCustomerRequest.getCustomerId());

        IndividualCustomer individualCustomer = this.modelMapperService.forRequest().map(updateIndividualCustomerRequest, IndividualCustomer.class);
        this.individualCustomerDao.save(individualCustomer);
        return new SuccessResult("Güncelledi");
    }

    @Override
    public Result delete(DeleteIndividualCustomerRequest deleteIndividualCustomerRequest) throws BusinessException {
        checkIfIndividualCustomerIdExists(deleteIndividualCustomerRequest.getCustomerId());

        IndividualCustomer individualCustomer = this.modelMapperService.forRequest().map(deleteIndividualCustomerRequest, IndividualCustomer.class);
        this.individualCustomerDao.delete(individualCustomer);
        return new SuccessResult("Silindi");
    }

    @Override
    public DataResult<List<IndividualCustomerListDto>> getAll() {
        List<IndividualCustomer> response = this.individualCustomerDao.findAll();

        List<IndividualCustomerListDto> result = response
                .stream()
                .map(individualCustomer -> this.modelMapperService.forDto().map(individualCustomer, IndividualCustomerListDto.class))
                .collect(Collectors.toList());

        return new SuccessDataResult<List<IndividualCustomerListDto>>(result, "Listelendi");
    }

    @Override
    public DataResult<IndividualCustomerGetDto> getById(int individualCustomerId) throws BusinessException {
        checkIfIndividualCustomerIdExists(individualCustomerId);

        IndividualCustomer response = this.individualCustomerDao.getById(individualCustomerId);
        IndividualCustomerGetDto result = this.modelMapperService.forDto().map(response, IndividualCustomerGetDto.class);
        return new SuccessDataResult<>(result, "Listelendi");
    }

    public void checkIfIndividualCustomerIdExists(int IndividualCustomerId) throws BusinessException {
        if (!this.individualCustomerDao.existsById(IndividualCustomerId)) {
            throw new BusinessException(IndividualCustomerId + " No'lu Normal Müşteri Bulunamadı..");
        }
    }
}
