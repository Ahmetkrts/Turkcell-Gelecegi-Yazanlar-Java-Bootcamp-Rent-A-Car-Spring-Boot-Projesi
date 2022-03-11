package com.turkcell.rentACar.business.abstracts;

import com.turkcell.rentACar.business.dtos.OrderedAdditionalListDto;
import com.turkcell.rentACar.business.request.CreateOrderedAdditionalRequest;
import com.turkcell.rentACar.business.request.DeleteOrderedAdditionalRequest;
import com.turkcell.rentACar.business.request.UpdateOrderedAdditionalRequest;
import com.turkcell.rentACar.core.exception.BusinessException;
import com.turkcell.rentACar.core.result.DataResult;
import com.turkcell.rentACar.core.result.Result;

import java.util.List;

public interface OrderedAdditionalService {


    Result add(CreateOrderedAdditionalRequest createOrderedAdditionalRequest) throws BusinessException;

    Result update(UpdateOrderedAdditionalRequest updateOrderedAdditionalRequest) throws BusinessException;

    Result delete(DeleteOrderedAdditionalRequest deleteOrderedAdditionalRequest) throws BusinessException;

    DataResult<List<OrderedAdditionalListDto>> getAll();

    DataResult<List<OrderedAdditionalListDto>> getById(int OrderedAdditionalId) throws BusinessException;

    void addAdditionals(int rentId, List<Integer> additionalList) throws BusinessException;
}
