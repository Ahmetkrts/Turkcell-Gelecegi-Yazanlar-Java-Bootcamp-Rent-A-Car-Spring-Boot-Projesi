package com.turkcell.rentACar.api.controller;

import com.turkcell.rentACar.business.abstracts.CreditCardService;
import com.turkcell.rentACar.business.dtos.creditCard.CreditCardGetDto;
import com.turkcell.rentACar.business.dtos.creditCard.CreditCardListDto;
import com.turkcell.rentACar.business.request.creditCard.CreateCreditCardRequest;
import com.turkcell.rentACar.business.request.creditCard.DeleteCreditCardRequest;
import com.turkcell.rentACar.business.request.creditCard.UpdateCreditCardRequest;
import com.turkcell.rentACar.core.exception.BusinessException;
import com.turkcell.rentACar.core.result.DataResult;
import com.turkcell.rentACar.core.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/CreditCards")
public class CreditCardController {

    private CreditCardService creditCardService;

    @Autowired
    public CreditCardController(CreditCardService creditCardService) {
        this.creditCardService = creditCardService;
    }

    @PostMapping("/add")
    Result add(@RequestBody @Valid CreateCreditCardRequest createCreditCardRequest) throws BusinessException {
        return this.creditCardService.add(createCreditCardRequest);
    }

    @GetMapping("/getAll")
    DataResult<List<CreditCardListDto>> getAll() {
        return this.creditCardService.getAll();
    }

    @GetMapping("/getById")
    DataResult<CreditCardGetDto> getById(@RequestParam int creditCardId) throws BusinessException {
        return this.creditCardService.getById(creditCardId);
    }

    @PutMapping("/update")
    Result update(@RequestBody @Valid UpdateCreditCardRequest updateCreditCardRequest) throws BusinessException {
        return this.creditCardService.update(updateCreditCardRequest);
    }

    @DeleteMapping("/delete")
    Result delete(@RequestBody DeleteCreditCardRequest deleteCreditCardRequest) throws BusinessException {
        return this.creditCardService.delete(deleteCreditCardRequest);
    }
}
