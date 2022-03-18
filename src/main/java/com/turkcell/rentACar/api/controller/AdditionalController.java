package com.turkcell.rentACar.api.controller;

import com.turkcell.rentACar.business.abstracts.AdditionalService;
import com.turkcell.rentACar.business.dtos.additional.AdditionalGetDto;
import com.turkcell.rentACar.business.dtos.additional.AdditionalListDto;
import com.turkcell.rentACar.business.request.additional.CreateAdditionalRequest;
import com.turkcell.rentACar.business.request.additional.DeleteAdditionalRequest;
import com.turkcell.rentACar.business.request.additional.UpdateAdditionalRequest;
import com.turkcell.rentACar.core.exception.BusinessException;
import com.turkcell.rentACar.core.result.DataResult;
import com.turkcell.rentACar.core.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/Additionals")
public class AdditionalController {
    private AdditionalService additionalService;

    @Autowired
    public AdditionalController(AdditionalService additionalService) {
        this.additionalService = additionalService;
    }

    @PostMapping("/add")
    Result add(@RequestBody @Valid CreateAdditionalRequest createAdditionalRequest) throws BusinessException {
        return this.additionalService.add(createAdditionalRequest);
    }

    @PutMapping("/update")
    Result update(@RequestBody @Valid UpdateAdditionalRequest updateAdditionalRequest) throws BusinessException {
        return this.additionalService.update(updateAdditionalRequest);
    }

    @DeleteMapping("/delete")
    Result delete(@RequestBody DeleteAdditionalRequest deleteAdditionalRequest) throws BusinessException {
        return this.additionalService.delete(deleteAdditionalRequest);
    }

    @GetMapping("/getAll")
    DataResult<List<AdditionalListDto>> getAll() {
        return this.additionalService.getAll();
    }

    @GetMapping("/getById")
    DataResult<AdditionalGetDto> getById(int additionalId) throws BusinessException {
        return this.additionalService.getById(additionalId);
    }
}
