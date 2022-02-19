package com.turkcell.rentACar.business.concrates;

import com.turkcell.rentACar.business.abstracts.ColorService;
import com.turkcell.rentACar.business.dtos.ColorGetDto;
import com.turkcell.rentACar.business.dtos.ColorListDto;
import com.turkcell.rentACar.business.request.CreateColorRequest;
import com.turkcell.rentACar.business.request.DeleteColorRequest;
import com.turkcell.rentACar.business.request.UpdateColorRequest;
import com.turkcell.rentACar.core.exception.BusinessException;
import com.turkcell.rentACar.core.mapping.ModelMapperService;
import com.turkcell.rentACar.core.result.*;
import com.turkcell.rentACar.dataAccess.abstracts.ColorDao;
import com.turkcell.rentACar.entities.concrates.Color;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ColorManager implements ColorService {

    private ColorDao colorDao;
    private ModelMapperService modelMapperService;

    @Autowired
    public ColorManager(ColorDao colorDao, ModelMapperService modelMapperService) {
        this.colorDao = colorDao;
        this.modelMapperService = modelMapperService;
    }

    @Override
    public Result add(CreateColorRequest createColorRequest) throws BusinessException {
        Color color = this.modelMapperService.forRequest().map(createColorRequest, Color.class);

        if (checkIfName(color.getName())) {

            this.colorDao.save(color);
            return new SuccessResult("eklendi");
        }

        return new ErrorResult("Eklenemedi isismler Aynı");
    }

    @Override
    public DataResult<List<ColorListDto>> getAll() {
        List<Color> response = this.colorDao.findAll();
        List<ColorListDto> result = response
                .stream()
                .map(color -> this.modelMapperService.forDto().map(color, ColorListDto.class))
                .collect(Collectors.toList());
        return new SuccessDataResult<List<ColorListDto>>(result, "Listelendi");

    }

    @Override
    public DataResult<ColorGetDto> getById(int colorId) {
        Color response = this.colorDao.getById(colorId);
        ColorGetDto result = this.modelMapperService.forDto().map(response, ColorGetDto.class);
        return new SuccessDataResult<ColorGetDto>("Listelendi");
    }

    @Override
    public Result update(UpdateColorRequest updateColorRequest) throws BusinessException {
        Color color = this.modelMapperService.forRequest().map(updateColorRequest, Color.class);

        if (checkIfName(color.getName())) {
            this.colorDao.save(color);
            return new SuccessResult("eklendi");
        }
        return new ErrorResult("Eklenemedi isismler Aynı");
    }

    @Override
    public Result delete(DeleteColorRequest deleteColorRequest) {
        Color color = this.modelMapperService.forRequest().map(deleteColorRequest, Color.class);
        this.colorDao.delete(color);
        return new SuccessResult("Veri Silindi");
    }

    private boolean checkIfName(String name) throws BusinessException {
        var result = this.colorDao.findAll();

        for (Color colorListDto : result) {
            if (colorListDto.getName().equals(name)) {
                throw new BusinessException("İsimler Aynı Olamaz");

            }
        }
        return true;

    }
}
