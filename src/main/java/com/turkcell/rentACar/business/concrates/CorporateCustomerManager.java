package com.turkcell.rentACar.business.concrates;

import com.turkcell.rentACar.business.abstracts.CorporateCustomerService;
import com.turkcell.rentACar.business.constants.messages.BusinessMessages;
import com.turkcell.rentACar.business.dtos.corporateCustomer.CorporateCustomerGetDto;
import com.turkcell.rentACar.business.dtos.corporateCustomer.CorporateCustomerListDto;
import com.turkcell.rentACar.business.request.corporateCustomer.CreateCorporateCustomerRequest;
import com.turkcell.rentACar.business.request.corporateCustomer.DeleteCorporateCustomerRequest;
import com.turkcell.rentACar.business.request.corporateCustomer.UpdateCorporateCustomerRequest;
import com.turkcell.rentACar.core.exception.BusinessException;
import com.turkcell.rentACar.core.mapping.ModelMapperService;
import com.turkcell.rentACar.core.result.DataResult;
import com.turkcell.rentACar.core.result.Result;
import com.turkcell.rentACar.core.result.SuccessDataResult;
import com.turkcell.rentACar.core.result.SuccessResult;
import com.turkcell.rentACar.dataAccess.abstracts.CorporateCustomerDao;
import com.turkcell.rentACar.entities.concrates.CorporateCustomer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CorporateCustomerManager implements CorporateCustomerService {
    private CorporateCustomerDao corporateCustomerDao;
    private ModelMapperService modelMapperService;

    @Autowired
    public CorporateCustomerManager(CorporateCustomerDao corporateCustomerDao, ModelMapperService modelMapperService) {
        this.corporateCustomerDao = corporateCustomerDao;
        this.modelMapperService = modelMapperService;
    }

    @Override
    public Result add(CreateCorporateCustomerRequest createCorporateCustomerRequest) throws BusinessException {
        CorporateCustomer corporateCustomer = this.modelMapperService.forRequest().map(createCorporateCustomerRequest, CorporateCustomer.class);
        this.corporateCustomerDao.save(corporateCustomer);
        return new SuccessResult(BusinessMessages.ADDED);
    }

    @Override
    public Result update(UpdateCorporateCustomerRequest updateCorporateCustomerRequest) throws BusinessException {
        checkIfCorporateCustomerIdExists(updateCorporateCustomerRequest.getUserId());

        CorporateCustomer corporateCustomer = this.modelMapperService.forRequest().map(updateCorporateCustomerRequest, CorporateCustomer.class);

        this.corporateCustomerDao.save(corporateCustomer);
        return new SuccessResult(BusinessMessages.UPDATED);
    }

    @Override
    public Result delete(DeleteCorporateCustomerRequest deleteCorporateCustomerRequest) throws BusinessException {
        checkIfCorporateCustomerIdExists(deleteCorporateCustomerRequest.getUserId());

        CorporateCustomer corporateCustomer = this.modelMapperService.forRequest().map(deleteCorporateCustomerRequest, CorporateCustomer.class);
        this.corporateCustomerDao.delete(corporateCustomer);
        return new SuccessResult(BusinessMessages.DELETED);
    }

    @Override
    public DataResult<List<CorporateCustomerListDto>> getAll() {
        List<CorporateCustomer> response = this.corporateCustomerDao.findAll();

        List<CorporateCustomerListDto> result = response
                .stream()
                .map(corporateCustomer -> this.modelMapperService.forDto().map(corporateCustomer, CorporateCustomerListDto.class))
                .collect(Collectors.toList());

        return new SuccessDataResult<List<CorporateCustomerListDto>>(result, BusinessMessages.LISTED);
    }

    @Override
    public DataResult<CorporateCustomerGetDto> getById(int corporateCustomerId) throws BusinessException {
        checkIfCorporateCustomerIdExists(corporateCustomerId);

        CorporateCustomer response = this.corporateCustomerDao.getById(corporateCustomerId);
        CorporateCustomerGetDto result = this.modelMapperService.forDto().map(response, CorporateCustomerGetDto.class);
        return new SuccessDataResult<>(result, corporateCustomerId + BusinessMessages.CORPORATE_LISTED);
    }

    public void checkIfCorporateCustomerIdExists(int CorporateCustomerId) throws BusinessException {
        if (!this.corporateCustomerDao.existsById(CorporateCustomerId)) {
            throw new BusinessException(CorporateCustomerId + BusinessMessages.CORPORATE_NOT_FOUND);
        }
    }
}
