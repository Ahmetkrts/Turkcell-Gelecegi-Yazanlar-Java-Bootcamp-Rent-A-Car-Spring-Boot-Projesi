package com.turkcell.rentACar.api.controller;

import com.turkcell.rentACar.business.abstracts.ColorService;
import com.turkcell.rentACar.business.dtos.ColorGetDto;
import com.turkcell.rentACar.business.dtos.ColorListDto;
import com.turkcell.rentACar.business.request.*;
import com.turkcell.rentACar.core.exception.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/colors")
public class ColorsController {

    private ColorService colorService;

    @Autowired
    public ColorsController(ColorService colorService) {
        this.colorService = colorService;
    }

    @GetMapping("/getall")
    public List<ColorListDto> getAll() {

        return this.colorService.getAll();
    }

    @PostMapping("/add")
    public String add(@RequestBody CreateColorRequest createColorRequest) {
        try {
            this.colorService.add(createColorRequest);
        } catch (Exception e) {
            return e.getMessage();
        }
        return "Eklendi";
    }

    @GetMapping("getById")
    public ColorGetDto getById(@RequestParam int colorId) {
        return this.colorService.getById(colorId);
    }

    @PostMapping("/update")
    public void update(@RequestBody UpdateColorRequest updateColorRequest) throws BusinessException {
        this.colorService.update(updateColorRequest);
    }

    @DeleteMapping("/delete")
    public void delete(@RequestBody DeleteColorRequest deleteColorRequest) {
        this.colorService.delete(deleteColorRequest);
    }
}
