package com.turkcell.rentACar.business.concrates;

import com.turkcell.rentACar.business.abstracts.AdditionalService;
import com.turkcell.rentACar.business.dtos.AdditionalGetDto;
import com.turkcell.rentACar.business.dtos.AdditionalListDto;
import com.turkcell.rentACar.business.request.CreateAdditionalRequest;
import com.turkcell.rentACar.business.request.DeleteAdditionalRequest;
import com.turkcell.rentACar.business.request.UpdateAdditionalRequest;
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
        return new SuccessResult("Eklendi");
    }

    @Override
    public Result update(UpdateAdditionalRequest updateAdditionalRequest) throws BusinessException {
        checkIfNameExists(updateAdditionalRequest.getName());
        checkIfAdditionIdExists(updateAdditionalRequest.getAdditionalId());
        Additional additional = this.modelMapperService.forRequest().map(updateAdditionalRequest, Additional.class);

        this.additionalDao.save(additional);
        return new SuccessResult("Güncellendi");
    }

    @Override
    public Result delete(DeleteAdditionalRequest deleteAdditionalRequest) throws BusinessException {
        checkIfAdditionIdExists(deleteAdditionalRequest.getAdditionalId());
        Additional additional = this.modelMapperService.forRequest().map(deleteAdditionalRequest, Additional.class);
        this.additionalDao.delete(additional);
        return new SuccessResult("Silindi");
    }

    @Override
    public DataResult<List<AdditionalListDto>> getAll() {
        List<Additional> response = this.additionalDao.findAll();
        List<AdditionalListDto> result = response
                .stream()
                .map(additional -> this.modelMapperService.forDto().map(additional, AdditionalListDto.class))
                .collect(Collectors.toList());
        return new SuccessDataResult<>(result, "Listelendi");
    }

    @Override
    public DataResult<AdditionalGetDto> getById(int additionalId) throws BusinessException {
        checkIfAdditionIdExists(additionalId);
        Additional response = this.additionalDao.getById(additionalId);
        AdditionalGetDto result = this.modelMapperService.forDto().map(response, AdditionalGetDto.class);

        return new SuccessDataResult<>(result, additionalId + " No'lu Ek Hizmet Listelendi");
    }


    private void checkIfNameExists(String name) throws BusinessException {
        if (this.additionalDao.existsByName(name)) {
            throw new BusinessException("İsim Aynı Olamaz");
        }
    }

    @Override
    public void checkIfAdditionIdExists(int additionalId) throws BusinessException {
        if (!this.additionalDao.existsByAdditionalId(additionalId)) {
            throw new BusinessException(additionalId + " No'lu Ek Hizmet Bulunamadı");
        }
    }

    @Override
    public double totalAdditionalFeeCalculator(List<Integer> additionalList) {
        int listSize = additionalList.size();
        double total = 0;
        for (int i = 0; i < listSize; i++) {

            total += this.additionalDao.getById(additionalList.get(i)).getDailyPrice();
        }
        return total;
    }


}
