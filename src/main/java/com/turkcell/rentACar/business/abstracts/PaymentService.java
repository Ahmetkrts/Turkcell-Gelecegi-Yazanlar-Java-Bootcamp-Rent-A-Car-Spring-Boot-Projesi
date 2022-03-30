package com.turkcell.rentACar.business.abstracts;

import com.turkcell.rentACar.business.dtos.payment.PaymentGetDto;
import com.turkcell.rentACar.business.dtos.payment.PaymentListDto;
import com.turkcell.rentACar.business.request.payment.CreateExtraPaymentRequest;
import com.turkcell.rentACar.business.request.payment.CreatePaymentRequest;
import com.turkcell.rentACar.business.request.payment.DeletePaymentRequest;
import com.turkcell.rentACar.business.request.payment.UpdatePaymentRequest;
import com.turkcell.rentACar.core.exception.BusinessException;
import com.turkcell.rentACar.core.result.DataResult;
import com.turkcell.rentACar.core.result.Result;

import java.util.List;

public interface PaymentService {

    Result add(CreatePaymentRequest createPaymentRequest) throws BusinessException;

    Result update(UpdatePaymentRequest updatePaymentRequest) throws BusinessException;

    Result delete(DeletePaymentRequest deletePaymentRequest) throws BusinessException;

    DataResult<List<PaymentListDto>> getAll();

    DataResult<PaymentGetDto> getById(int paymentId) throws BusinessException;

    Result extraDaysRentCarPayment(CreateExtraPaymentRequest createExtraPaymentRequest) throws BusinessException;

    void checkIfPaymentIdExist(int paymentId) throws BusinessException;
}
