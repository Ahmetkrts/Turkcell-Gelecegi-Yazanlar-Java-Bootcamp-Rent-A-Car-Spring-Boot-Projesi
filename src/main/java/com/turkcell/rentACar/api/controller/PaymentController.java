package com.turkcell.rentACar.api.controller;

import com.turkcell.rentACar.business.abstracts.PaymentService;
import com.turkcell.rentACar.business.dtos.payment.PaymentGetDto;
import com.turkcell.rentACar.business.dtos.payment.PaymentListDto;
import com.turkcell.rentACar.business.request.payment.CreatePaymentRequest;
import com.turkcell.rentACar.business.request.payment.DeletePaymentRequest;
import com.turkcell.rentACar.business.request.payment.UpdatePaymentRequest;
import com.turkcell.rentACar.core.exception.BusinessException;
import com.turkcell.rentACar.core.result.DataResult;
import com.turkcell.rentACar.core.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/Payments")
public class PaymentController {

    private PaymentService paymentService;

    @Autowired
    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/add")
    Result add(@RequestBody @Valid CreatePaymentRequest createPaymentRequest) throws BusinessException {
        return this.paymentService.add(createPaymentRequest);
    }

    @PutMapping("/update")
    Result update(@RequestBody @Valid UpdatePaymentRequest updatePaymentRequest) throws BusinessException {
        return this.paymentService.update(updatePaymentRequest);
    }

    @DeleteMapping("/delete")
    Result delete(@RequestBody DeletePaymentRequest deletePaymentRequest) throws BusinessException {
        return this.paymentService.delete(deletePaymentRequest);
    }

    @GetMapping("/getAll")
    DataResult<List<PaymentListDto>> getAll() {
        return this.paymentService.getAll();
    }

    @GetMapping("/getById")
    DataResult<PaymentGetDto> getById(@RequestParam int paymentId) throws BusinessException {
        return this.paymentService.getById(paymentId);
    }

}
