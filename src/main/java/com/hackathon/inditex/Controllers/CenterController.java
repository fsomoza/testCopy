package com.hackathon.inditex.Controllers;

import com.hackathon.inditex.Dtos.CenterDto;
import com.hackathon.inditex.Dtos.CreateCenterDto;
import com.hackathon.inditex.Dtos.MessageResponseDto;
import com.hackathon.inditex.Dtos.UpdateCenterDto;
import com.hackathon.inditex.Services.CenterService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/centers")
public class CenterController {

    @Autowired
    private CenterService centerService;

    @PostMapping
    public ResponseEntity<MessageResponseDto> createCenter(@Valid @RequestBody CreateCenterDto createCenterDto){
        MessageResponseDto response = centerService.createCenter(createCenterDto);
        if(response.getMessage().equals("Logistics center created successfully.")){
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        }else{
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    };

    @GetMapping
    public ResponseEntity<List<CenterDto>> getAllCenters() {
        List<CenterDto> centers = centerService.getAllCenters();
        return new ResponseEntity<>(centers, HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<MessageResponseDto> updateCenter(@PathVariable long id,@RequestBody UpdateCenterDto updateCenterDto){
        MessageResponseDto response = centerService.updateCenter(updateCenterDto, id);

        if (response.getMessage().equals("Center not found.")) {
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } else if (response.getMessage().equals("Logistics center updated successfully.")) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponseDto> deleteCenter(@PathVariable Long id){
        MessageResponseDto response = centerService.deleteCenter(id);
        if (response.getMessage().equals("Center not found.")) {
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(response,HttpStatus.OK);
    }
}
