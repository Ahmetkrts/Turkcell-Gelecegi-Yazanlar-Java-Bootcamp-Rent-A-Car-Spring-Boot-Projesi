package com.turkcell.rentACar.business.concrates;

import com.turkcell.rentACar.business.abstracts.ColorService;
import com.turkcell.rentACar.business.dtos.color.ColorGetDto;
import com.turkcell.rentACar.business.dtos.color.ColorListDto;
import com.turkcell.rentACar.business.request.color.CreateColorRequest;
import com.turkcell.rentACar.business.request.color.DeleteColorRequest;
import com.turkcell.rentACar.business.request.color.UpdateColorRequest;
import com.turkcell.rentACar.core.exception.BusinessException;
import com.turkcell.rentACar.core.mapping.ModelMapperService;
import com.turkcell.rentACar.core.result.DataResult;
import com.turkcell.rentACar.core.result.Result;
import com.turkcell.rentACar.core.result.SuccessDataResult;
import com.turkcell.rentACar.core.result.SuccessResult;
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
        checkIfNameExists(createColorRequest.getName());

        Color color = this.modelMapperService.forRequest().map(createColorRequest, Color.class);
        this.colorDao.save(color);
        return new SuccessResult("eklendi");

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
    public DataResult<ColorGetDto> getById(int colorId) throws BusinessException {
        checkIfColorIdExists(colorId);

        Color response = this.colorDao.getById(colorId);
        ColorGetDto result = this.modelMapperService.forDto().map(response, ColorGetDto.class);
        return new SuccessDataResult<ColorGetDto>("Listelendi");
    }

    @Override
    public Result update(UpdateColorRequest updateColorRequest) throws BusinessException {
        checkIfNameExists(updateColorRequest.getName());
        checkIfColorIdExists(updateColorRequest.getColorId());

        Color color = this.modelMapperService.forRequest().map(updateColorRequest, Color.class);
        this.colorDao.save(color);
        return new SuccessResult("eklendi");

    }

    @Override
    public Result delete(DeleteColorRequest deleteColorRequest) throws BusinessException {
        checkIfColorIdExists(deleteColorRequest.getColorId());

        Color color = this.modelMapperService.forRequest().map(deleteColorRequest, Color.class);
        this.colorDao.deleteById(color.getColorId());
        return new SuccessResult("Veri Silindi");
    }

    private void checkIfNameExists(String name) throws BusinessException {
        if (this.colorDao.existsByName(name)) {
            throw new BusinessException("İsimler Aynı Olamaz...");
        }
    }

    public void checkIfColorIdExists(int colorId) throws BusinessException {
        if (!this.colorDao.existsById(colorId)) {
            throw new BusinessException(colorId + " No'lu Renk Bulunamadı");
        }
    }
}
