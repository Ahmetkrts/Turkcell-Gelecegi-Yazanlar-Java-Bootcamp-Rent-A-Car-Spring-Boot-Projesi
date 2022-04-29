package com.turkcell.rentACar.api.controller;

import com.turkcell.rentACar.business.abstracts.OrderedAdditionalService;
import com.turkcell.rentACar.business.dtos.orderedAdditional.OrderedAdditionalListDto;
import com.turkcell.rentACar.business.request.orderedAdditional.CreateOrderedAdditionalRequest;
import com.turkcell.rentACar.business.request.orderedAdditional.DeleteOrderedAdditionalRequest;
import com.turkcell.rentACar.business.request.orderedAdditional.UpdateOrderedAdditionalRequest;
import com.turkcell.rentACar.core.exception.BusinessException;
import com.turkcell.rentACar.core.result.DataResult;
import com.turkcell.rentACar.core.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/orderedAdditionals")
public class OrderedAdditionalController {

    private OrderedAdditionalService orderedAdditionalService;

    @Autowired
    public OrderedAdditionalController(OrderedAdditionalService orderedAdditionalService) {
        this.orderedAdditionalService = orderedAdditionalService;
    }

   /* @PostMapping("/add")
    Result add(@RequestBody @Valid CreateOrderedAdditionalRequest createOrderedAdditionalRequest) throws BusinessException {
        return this.orderedAdditionalService.add(createOrderedAdditionalRequest);
    }

    @PutMapping("/update")
    Result update(@RequestBody @Valid UpdateOrderedAdditionalRequest updateOrderedAdditionalRequest) throws BusinessException {
        return this.orderedAdditionalService.update(updateOrderedAdditionalRequest);
    }

    @DeleteMapping("/delete")
    Result delete(@RequestBody DeleteOrderedAdditionalRequest deleteOrderedAdditionalRequest) throws BusinessException {
        return this.orderedAdditionalService.delete(deleteOrderedAdditionalRequest);
    }*/

    @GetMapping("/getAll")
    DataResult<List<OrderedAdditionalListDto>> getAll() {
        return this.orderedAdditionalService.getAll();
    }

    @GetMapping("/getById")
    DataResult<List<OrderedAdditionalListDto>> getById(@RequestParam int orderedAdditionalId) throws BusinessException {
        return this.orderedAdditionalService.getById(orderedAdditionalId);
    }


}
