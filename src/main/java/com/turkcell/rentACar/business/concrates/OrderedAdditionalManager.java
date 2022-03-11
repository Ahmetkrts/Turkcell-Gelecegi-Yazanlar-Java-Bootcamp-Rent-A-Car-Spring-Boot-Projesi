package com.turkcell.rentACar.business.concrates;

import com.turkcell.rentACar.business.abstracts.AdditionalService;
import com.turkcell.rentACar.business.abstracts.OrderedAdditionalService;
import com.turkcell.rentACar.business.dtos.OrderedAdditionalListDto;
import com.turkcell.rentACar.business.request.CreateOrderedAdditionalRequest;
import com.turkcell.rentACar.business.request.DeleteOrderedAdditionalRequest;
import com.turkcell.rentACar.business.request.UpdateOrderedAdditionalRequest;
import com.turkcell.rentACar.core.exception.BusinessException;
import com.turkcell.rentACar.core.mapping.ModelMapperService;
import com.turkcell.rentACar.core.result.DataResult;
import com.turkcell.rentACar.core.result.Result;
import com.turkcell.rentACar.core.result.SuccessDataResult;
import com.turkcell.rentACar.core.result.SuccessResult;
import com.turkcell.rentACar.dataAccess.abstracts.OrderedAdditionalDao;
import com.turkcell.rentACar.entities.concrates.OrderedAdditional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderedAdditionalManager implements OrderedAdditionalService {
    private OrderedAdditionalDao orderedAdditionalDao;
    private ModelMapperService modelMapperService;
    private AdditionalService additionalService;

    @Autowired
    public OrderedAdditionalManager(OrderedAdditionalDao orderedAdditionalDao, ModelMapperService modelMapperService,
                                    @Lazy AdditionalService additionalService) {
        this.orderedAdditionalDao = orderedAdditionalDao;
        this.modelMapperService = modelMapperService;
        this.additionalService = additionalService;
    }

    @Override
    public Result add(CreateOrderedAdditionalRequest createOrderedAdditionalRequest) throws BusinessException {

        OrderedAdditional response = this.modelMapperService.forRequest().map(createOrderedAdditionalRequest, OrderedAdditional.class);
        response.setOrderedAdditionalId(0);
        this.orderedAdditionalDao.save(response);
        return new SuccessResult("Eklendi..");
    }

    @Override
    public Result update(UpdateOrderedAdditionalRequest updateOrderedAdditionalRequest) throws BusinessException {
        checkIfOrderedAdditionalId(updateOrderedAdditionalRequest.getOrderedAdditionalId());
        OrderedAdditional response = this.modelMapperService.forRequest().map(updateOrderedAdditionalRequest, OrderedAdditional.class);
        this.orderedAdditionalDao.save(response);
        return new SuccessResult("Güncellendi..");
    }

    @Override
    public Result delete(DeleteOrderedAdditionalRequest deleteOrderedAdditionalRequest) throws BusinessException {
        checkIfOrderedAdditionalId(deleteOrderedAdditionalRequest.getOrderedAdditionalId());
        OrderedAdditional response = this.modelMapperService.forRequest().map(deleteOrderedAdditionalRequest, OrderedAdditional.class);
        this.orderedAdditionalDao.save(response);
        return new SuccessResult("Silindi..");
    }

    @Override
    public DataResult<List<OrderedAdditionalListDto>> getAll() {
        List<OrderedAdditional> response = this.orderedAdditionalDao.findAll();
        List<OrderedAdditionalListDto> result = response
                .stream()
                .map(orderedAdditional -> this.modelMapperService.forDto().map(orderedAdditional, OrderedAdditionalListDto.class))
                .collect(Collectors.toList());
        return new SuccessDataResult<List<OrderedAdditionalListDto>>(result, "Listelendi");
    }

    @Override
    public DataResult<List<OrderedAdditionalListDto>> getById(int orderedAdditionalId) throws BusinessException {
        checkIfOrderedAdditionalId(orderedAdditionalId);

        List<OrderedAdditional> response = this.orderedAdditionalDao.findAllByOrderedAdditionalId(orderedAdditionalId);
        List<OrderedAdditionalListDto> result = response
                .stream()
                .map(orderedAdditional -> this.modelMapperService.forDto().map(orderedAdditional, OrderedAdditionalListDto.class))
                .collect(Collectors.toList());
        return new SuccessDataResult<List<OrderedAdditionalListDto>>(result, "Listelendi");
    }

    @Override
    public void addAdditionals(int rentId, List<Integer> additionalList) throws BusinessException {
        int listSize = additionalList.size();
        for (int i = 0; i < listSize; i++) {
            CreateOrderedAdditionalRequest createOrderedAdditionalRequest = new CreateOrderedAdditionalRequest(rentId, additionalList.get(i));
            this.add(createOrderedAdditionalRequest);
        }
    }

    private void checkIfOrderedAdditionalId(int orderedAdditionalId) throws BusinessException {
        if (!this.orderedAdditionalDao.existsById(orderedAdditionalId)) {
            throw new BusinessException(orderedAdditionalId + " No'lu Ek Hizmetler Listesi Bulunamadı");
        }
    }

}
