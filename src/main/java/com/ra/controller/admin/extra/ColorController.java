package com.ra.controller.admin.extra;

import com.ra.dto.request.color.ColorRequestDTO;
import com.ra.exception.ColorExceptionNotFound;
import com.ra.model.Color;
import com.ra.service.color.ColorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class ColorController {
    @Autowired
    private ColorService colorService;
    @GetMapping("/color")
    public ResponseEntity<?>findAll(){
        List<Color>list=colorService.findAll();
        return ResponseEntity.ok(list);
    }
    @PostMapping("/color")
    public ResponseEntity<?>createColor(@RequestBody @Valid ColorRequestDTO colorRequestDTO){
        Color color1=colorService.createColor(colorRequestDTO);
        return ResponseEntity.ok(color1);
    }
    @GetMapping("/color/{id}")
    public ResponseEntity<?>findColorId(@PathVariable String id) throws ColorExceptionNotFound {
      try {
          Long colorId= Long.valueOf(id);
          Color color=colorService.findById(colorId);
          return ResponseEntity.ok(color);
      }catch (Exception e) {
          return new ResponseEntity<>("Application context error", HttpStatus.BAD_REQUEST);
      }
    }
    @PutMapping("/color/{id}")
    public ResponseEntity<?>updateColor(@PathVariable String id,@RequestBody @Valid ColorRequestDTO colorRequestDTO) throws ColorExceptionNotFound {
      try {
          Long colorId= Long.valueOf(id);
          Color color1=colorService.updateColor(colorId,colorRequestDTO);
          return ResponseEntity.ok(color1);
      }catch (Exception e) {
          return new ResponseEntity<>("Application context error", HttpStatus.BAD_REQUEST);
      }
    }

}
