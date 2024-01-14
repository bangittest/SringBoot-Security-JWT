package com.ra.controller.admin.extra;

import com.ra.dto.request.color.ColorRequestDTO;
import com.ra.exception.ColorExceptionNotFound;
import com.ra.model.Color;
import com.ra.service.color.ColorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ResponseEntity<?>findColorId(@PathVariable Long id) throws ColorExceptionNotFound {
        Color color=colorService.findById(id);
        return ResponseEntity.ok(color);
    }
    @PutMapping("/color/{id}")
    public ResponseEntity<?>updateColor(@PathVariable Long id,@RequestBody @Valid ColorRequestDTO colorRequestDTO) throws ColorExceptionNotFound {
        Color color1=colorService.updateColor(id,colorRequestDTO);
        return ResponseEntity.ok(color1);
    }

}
