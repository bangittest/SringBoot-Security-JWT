package com.ra.service.color;

import com.ra.dto.request.color.ColorRequestDTO;
import com.ra.exception.ColorExceptionNotFound;
import com.ra.model.Color;

import java.util.List;

public interface ColorService {
    List<Color>findAll();
    Color createColor(ColorRequestDTO colorRequestDTO);
    Color updateColor(Long colorId,ColorRequestDTO colorRequestDTO) throws ColorExceptionNotFound;
    Color findById(Long colorId) throws ColorExceptionNotFound;
    Color findByColorName(String colorName);
}
