package com.ra.service.color;

import com.ra.dto.request.color.ColorRequestDTO;
import com.ra.exception.ColorExceptionNotFound;
import com.ra.exception.SizeNotFoundException;
import com.ra.model.Color;
import com.ra.repository.ColorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ColorServiceImpl implements ColorService{
    @Autowired
    private ColorRepository colorRepository;

    @Override
    public List<Color> findAll() {
        return colorRepository.findAll();
    }

    @Override
    public Color createColor(ColorRequestDTO colorRequestDTO) {
        Color color=new Color();
        color.setName(colorRequestDTO.getName());
        return colorRepository.save(color);
    }

    @Override
    public Color updateColor(Long colorId, ColorRequestDTO colorRequestDTO) throws ColorExceptionNotFound {
        Optional<Color> colorOptional=colorRepository.findById(colorId);
        if (colorOptional.isPresent()){
            Color colorNew= colorOptional.get();
            colorNew.setName(colorRequestDTO.getName());
            return colorRepository.save(colorNew);
        }
        throw new ColorExceptionNotFound("Could not find color with ID: " + colorId);
    }

    @Override
    public Color findById(Long colorId) throws ColorExceptionNotFound {
        Optional<Color> colorOptional=colorRepository.findById(colorId);
        if (colorOptional.isPresent()){
            Color color=colorOptional.get();
            return color;
        }
        throw new ColorExceptionNotFound("Could not find color with ID: " + colorId);
    }

    @Override
    public Color findByColorName(String colorName) throws ColorExceptionNotFound {
        Color color=colorRepository.findColorByName(colorName);
        if (color==null){
            throw  new ColorExceptionNotFound("Color not found for name:" + colorName);
        }
        return color;
    }


}
