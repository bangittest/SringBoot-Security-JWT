package com.ra.service.size;

import com.ra.dto.request.size.SizeRequestDTO;
import com.ra.exception.SizeNotFoundException;
import com.ra.model.Size;
import com.ra.repository.SizeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SizeServiceImpl implements SizeService{
    @Autowired
    private SizeRepository sizeRepository;
    @Override
    public List<Size> findAll() {
        return sizeRepository.findAll();
    }

    @Override
    public Size createSize(SizeRequestDTO sizeRequestDTO) {
        Size size=new Size();
        size.setName(sizeRequestDTO.getName());
        return sizeRepository.save(size);
    }

    @Override
    public Size findById(Long sizeId) throws SizeNotFoundException {
        Optional<Size> sizeOptional=sizeRepository.findById(sizeId);
        if (sizeOptional.isPresent()){
            Size size=sizeOptional.get();
            return size;
        }
        throw new SizeNotFoundException("Size not found " +sizeId);
    }

    @Override
    public Size updateSize(Long sizeId, SizeRequestDTO sizeRequestDTO) throws SizeNotFoundException {
        Optional<Size>sizeOptional=sizeRepository.findById(sizeId);
        if (sizeOptional.isPresent()){
            Size size=sizeOptional.get();
            size.setName(sizeRequestDTO.getName());
            return sizeRepository.save(size);
        }
        throw new SizeNotFoundException("Size not found " +sizeId);
    }

    @Override
    public Size findByName(String name) throws SizeNotFoundException {
        Size size = sizeRepository.findSizeByName(name);
        if (size == null) {
            throw new SizeNotFoundException("Size not found for name: " + name);
        }
        return size;
    }
}
