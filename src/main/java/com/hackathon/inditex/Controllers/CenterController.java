package com.hackathon.inditex.Controllers;

import com.hackathon.inditex.dtos.CenterDTO;
import com.hackathon.inditex.dtos.CreateCenterDTO;
import com.hackathon.inditex.dtos.MessageResponseDTO;
import com.hackathon.inditex.dtos.UpdateCenterDTO;
import com.hackathon.inditex.services.CenterService;
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
    public ResponseEntity<MessageResponseDTO> createCenter(@RequestBody CreateCenterDTO createCenterDTO) {
        MessageResponseDTO response = centerService.createCenter(createCenterDTO);

        if (response.getMessage().equals("Logistics center created successfully.")) {
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<List<CenterDTO>> getAllCenters() {
        List<CenterDTO> centers = centerService.getAllCenters();
        return new ResponseEntity<>(centers, HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<MessageResponseDTO> updateCenter(@PathVariable Long id, @RequestBody UpdateCenterDTO updateCenterDTO) {
        MessageResponseDTO response = centerService.updateCenter(id, updateCenterDTO);

        if (response.getMessage().equals("Center not found.")) {
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } else if (response.getMessage().equals("Logistics center updated successfully.")) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponseDTO> deleteCenter(@PathVariable Long id) {
        MessageResponseDTO response = centerService.deleteCenter(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}