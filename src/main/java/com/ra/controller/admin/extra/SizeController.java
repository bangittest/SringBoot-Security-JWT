package com.ra.controller.admin.extra;

import com.ra.dto.request.size.SizeRequestDTO;
import com.ra.exception.SizeNotFoundException;
import com.ra.model.Size;
import com.ra.service.size.SizeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class SizeController {
    @Autowired
    private SizeService sizeService;
    @GetMapping("/size")
    public ResponseEntity<?>findSize(){
        List<Size>list=sizeService.findAll();
        return ResponseEntity.ok(list);
    }
    @PostMapping("/size")
    public ResponseEntity<?>createSize(@RequestBody @Valid SizeRequestDTO sizeRequestDTO){
        Size size1=sizeService.createSize(sizeRequestDTO);
        return ResponseEntity.ok(size1);
    }
    @GetMapping("/size/{id}")
    public ResponseEntity<?>findById(@PathVariable String id) throws SizeNotFoundException {

        try {
            Long sizeId= Long.valueOf(id);
            return ResponseEntity.ok(sizeService.findById(sizeId));
        }catch (NumberFormatException e) {
            return new ResponseEntity<>("Please enter a valid number", HttpStatus.BAD_REQUEST);
        }
    }
    @PutMapping("/size/{id}")
    public ResponseEntity<?>updateSize(@PathVariable String id,@RequestBody @Valid SizeRequestDTO sizeRequestDTO) throws SizeNotFoundException {
        try {
            Long sizeId= Long.valueOf(id);
            return ResponseEntity.ok(sizeService.updateSize(sizeId,sizeRequestDTO));
        }catch (NumberFormatException e) {
            return new ResponseEntity<>("Please enter a valid number", HttpStatus.BAD_REQUEST);
        }
    }
}
