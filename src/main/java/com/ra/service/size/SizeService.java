package com.ra.service.size;

import com.ra.dto.request.size.SizeRequestDTO;
import com.ra.exception.SizeNotFoundException;
import com.ra.model.Size;

import java.util.List;

public interface SizeService {
    List<Size>findAll();
    Size createSize(SizeRequestDTO sizeRequestDTO);
    Size findById(Long sizeId) throws SizeNotFoundException;
    Size updateSize(Long sizeId, SizeRequestDTO sizeRequestDTO) throws SizeNotFoundException;
    Size findByName(String name);
}
