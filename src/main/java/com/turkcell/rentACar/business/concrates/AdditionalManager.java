package com.turkcell.rentACar.business.concrates;

import com.turkcell.rentACar.business.abstracts.AdditionalService;
import com.turkcell.rentACar.business.constants.messages.BusinessMessages;
import com.turkcell.rentACar.business.dtos.additional.AdditionalGetDto;
import com.turkcell.rentACar.business.dtos.additional.AdditionalListDto;
import com.turkcell.rentACar.business.request.additional.CreateAdditionalRequest;
import com.turkcell.rentACar.business.request.additional.DeleteAdditionalRequest;
import com.turkcell.rentACar.business.request.additional.UpdateAdditionalRequest;
import com.turkcell.rentACar.core.exception.BusinessException;
import com.turkcell.rentACar.core.mapping.ModelMapperService;
import com.turkcell.rentACar.core.result.DataResult;
import com.turkcell.rentACar.core.result.Result;
import com.turkcell.rentACar.core.result.SuccessDataResult;
import com.turkcell.rentACar.core.result.SuccessResult;
import com.turkcell.rentACar.dataAccess.abstracts.AdditionalDao;
import com.turkcell.rentACar.entities.concrates.Additional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdditionalManager implements AdditionalService {
    private AdditionalDao additionalDao;
    private ModelMapperService modelMapperService;

    @Autowired
    public AdditionalManager(AdditionalDao additionalDao, ModelMapperService modelMapperService) {
        this.additionalDao = additionalDao;
        this.modelMapperService = modelMapperService;
    }

    @Override
    public Result add(CreateAdditionalRequest createAdditionalRequest) throws BusinessException {
        checkIfNameExists(createAdditionalRequest.getName());

        Additional response = this.modelMapperService.forRequest().map(createAdditionalRequest, Additional.class);
        this.additionalDao.save(response);

        return new SuccessResult(BusinessMessages.ADDED);
    }

    @Override
    public Result update(UpdateAdditionalRequest updateAdditionalRequest) throws BusinessException {
        checkIfNameExists(updateAdditionalRequest.getName());
        checkIfAdditionIdExists(updateAdditionalRequest.getAdditionalId());

        Additional additional = this.modelMapperService.forRequest().map(updateAdditionalRequest, Additional.class);
        this.additionalDao.save(additional);

        return new SuccessResult(BusinessMessages.UPDATED);
    }

    @Override
    public Result delete(DeleteAdditionalRequest deleteAdditionalRequest) throws BusinessException {
        checkIfAdditionIdExists(deleteAdditionalRequest.getAdditionalId());

        Additional additional = this.modelMapperService.forRequest().map(deleteAdditionalRequest, Additional.class);
        this.additionalDao.deleteById(additional.getAdditionalId());

        return new SuccessResult(BusinessMessages.DELETED);
    }

    @Override
    public DataResult<List<AdditionalListDto>> getAll() {
        List<Additional> response = this.additionalDao.findAll();
        List<AdditionalListDto> result = response
                .stream()
                .map(additional -> this.modelMapperService.forDto().map(additional, AdditionalListDto.class))
                .collect(Collectors.toList());
        return new SuccessDataResult<>(result, BusinessMessages.LISTED);
    }

    @Override
    public DataResult<AdditionalGetDto> getById(int additionalId) throws BusinessException {
        checkIfAdditionIdExists(additionalId);
        Additional response = this.additionalDao.getById(additionalId);
        AdditionalGetDto result = this.modelMapperService.forDto().map(response, AdditionalGetDto.class);

        return new SuccessDataResult<>(result, additionalId + BusinessMessages.ADDITIONAL_LISTED);
    }


    private void checkIfNameExists(String name) throws BusinessException {
        if (this.additionalDao.existsByName(name)) {
            throw new BusinessException(BusinessMessages.SAME_NAME);
        }
    }

    @Override
    public void checkIfAdditionIdExists(int additionalId) throws BusinessException {
        if (!this.additionalDao.existsByAdditionalId(additionalId)) {
            throw new BusinessException(additionalId + BusinessMessages.ADDITIONAL_NOT_FOUND);
        }
    }

    @Override
    public double totalAdditionalFeeCalculator(List<Integer> additionalList) {
        double total = 0;
        for (Integer integer : additionalList) {
            total += this.additionalDao.getById(integer).getDailyPrice();
        }
        return total;
    }


}
