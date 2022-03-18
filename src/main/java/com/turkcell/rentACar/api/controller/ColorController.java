package com.turkcell.rentACar.api.controller;

import com.turkcell.rentACar.business.abstracts.ColorService;
import com.turkcell.rentACar.business.dtos.color.ColorGetDto;
import com.turkcell.rentACar.business.dtos.color.ColorListDto;
import com.turkcell.rentACar.business.request.color.CreateColorRequest;
import com.turkcell.rentACar.business.request.color.DeleteColorRequest;
import com.turkcell.rentACar.business.request.color.UpdateColorRequest;
import com.turkcell.rentACar.core.exception.BusinessException;
import com.turkcell.rentACar.core.result.DataResult;
import com.turkcell.rentACar.core.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/colors")
public class ColorController {

    private ColorService colorService;

    @Autowired
    public ColorController(ColorService colorService) {
        this.colorService = colorService;
    }

    @GetMapping("/getall")
    public DataResult<List<ColorListDto>> getAll() {

        return this.colorService.getAll();
    }

    @PostMapping("/add")
    public Result add(@RequestBody @Valid CreateColorRequest createColorRequest) throws BusinessException {

        return this.colorService.add(createColorRequest);

    }

    @GetMapping("getById")
    public DataResult<ColorGetDto> getById(@RequestParam int colorId) throws BusinessException {
        return this.colorService.getById(colorId);
    }

    @PutMapping("/update")
    public Result update(@RequestBody @Valid UpdateColorRequest updateColorRequest) throws BusinessException {
        return this.colorService.update(updateColorRequest);
    }

    @DeleteMapping("/delete")
    public Result delete(@RequestBody DeleteColorRequest deleteColorRequest) throws BusinessException {
        return this.colorService.delete(deleteColorRequest);
    }
}
