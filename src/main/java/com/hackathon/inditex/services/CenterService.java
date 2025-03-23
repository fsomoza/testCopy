package com.hackathon.inditex.services;


import com.hackathon.inditex.Entities.Center;
import com.hackathon.inditex.dtos.CenterDTO;
import com.hackathon.inditex.dtos.CreateCenterDTO;
import com.hackathon.inditex.dtos.MessageResponseDTO;
import com.hackathon.inditex.dtos.UpdateCenterDTO;
import com.hackathon.inditex.repositories.CenterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CenterService {

    @Autowired
    private CenterRepository centerRepository;

    public MessageResponseDTO createCenter(CreateCenterDTO createCenterDTO) {
        // Validate capacity string
        String capacity = createCenterDTO.getCapacity();
        if (capacity != null) {
            // Check that capacity contains only valid combinations: B, M, S, BM, BS, MS, or BMS
            if (!capacity.matches("^(B|M|S|BM|BS|MS|BMS)$")) {
                return new MessageResponseDTO("Invalid capacity format. Should be one of: B, M, S, BM, BS, MS, or BMS.");
            }

            // The regex now handles the uniqueness check, but if you want to keep the distinct count check as a safeguard:
            if (capacity.length() != capacity.chars().distinct().count()) {
                return new MessageResponseDTO("Invalid capacity format. Each type (B, M, S) should appear at most once.");
            }
        }


        // Check if there is already a center at the same coordinates
        Optional<Center> existingCenter = centerRepository.findByCoordinates
                (
                createCenterDTO.getCoordinates().getLatitude(),
                createCenterDTO.getCoordinates().getLongitude()
        );

        if (existingCenter.isPresent()) {
            return new MessageResponseDTO("There is already a logistics center in that position.");
        }

        // Check if current load exceeds max capacity
        if (createCenterDTO.getCurrentLoad() > createCenterDTO.getMaxCapacity()) {
            return new MessageResponseDTO("Current load cannot exceed max capacity.");
        }

        // Create and save the new center
        Center center = new Center();
        center.setName(createCenterDTO.getName());
        center.setCapacity(createCenterDTO.getCapacity());
        center.setStatus(createCenterDTO.getStatus());
        center.setMaxCapacity(createCenterDTO.getMaxCapacity());
        center.setCurrentLoad(createCenterDTO.getCurrentLoad());
        center.setCoordinates(createCenterDTO.getCoordinates());

        centerRepository.save(center);

        return new MessageResponseDTO("Logistics center created successfully.");
    }

    public List<CenterDTO> getAllCenters() {
        List<Center> centers = centerRepository.findAll();
        return centers.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public MessageResponseDTO updateCenter(Long id, UpdateCenterDTO updateCenterDTO) {

        // Validate capacity string
        String capacity = updateCenterDTO.getCapacity();
        if (capacity != null) {
            // Check that capacity contains only valid combinations: B, M, S, BM, BS, MS, or BMS
            if (!capacity.matches("^(B|M|S|BM|BS|MS|BMS)$")) {
                return new MessageResponseDTO("Invalid capacity format. Should be one of: B, M, S, BM, BS, MS, or BMS.");
            }

            // The regex now handles the uniqueness check, but if you want to keep the distinct count check as a safeguard:
            if (capacity.length() != capacity.chars().distinct().count()) {
                return new MessageResponseDTO("Invalid capacity format. Each type (B, M, S) should appear at most once.");
            }
        }

        Optional<Center> centerOptional = centerRepository.findById(id);
        if (centerOptional.isEmpty()) {
            return new MessageResponseDTO("Center not found.");
        }

        Center center = centerOptional.get();

        // Only update fields that are provided in the DTO
        if (updateCenterDTO.getName() != null) {
            center.setName(updateCenterDTO.getName());
        }

        if (updateCenterDTO.getCapacity() != null) {
            center.setCapacity(updateCenterDTO.getCapacity());
        }

        if (updateCenterDTO.getStatus() != null) {
            center.setStatus(updateCenterDTO.getStatus());
        }

        if (updateCenterDTO.getMaxCapacity() != null) {
            center.setMaxCapacity(updateCenterDTO.getMaxCapacity());
        }

        if (updateCenterDTO.getCurrentLoad() != null) {
            center.setCurrentLoad(updateCenterDTO.getCurrentLoad());
        }

        if (updateCenterDTO.getCoordinates() != null) {
            // Check if there is already a center at the new coordinates (if different from current)
            if (updateCenterDTO.getCoordinates().getLatitude() != center.getCoordinates().getLatitude() ||
                    updateCenterDTO.getCoordinates().getLongitude() != center.getCoordinates().getLongitude()) {

                Optional<Center> existingCenter = centerRepository.findByCoordinates(
                        updateCenterDTO.getCoordinates().getLatitude(),
                        updateCenterDTO.getCoordinates().getLongitude()
                );

                if (existingCenter.isPresent() && !existingCenter.get().getId().equals(id)) {
                    return new MessageResponseDTO("There is already a logistics center in that position.");
                }
            }
            center.setCoordinates(updateCenterDTO.getCoordinates());
        }

        // Check if updated current load exceeds max capacity
        if (center.getCurrentLoad() > center.getMaxCapacity()) {
            return new MessageResponseDTO("Current load cannot exceed max capacity.");
        }

        centerRepository.save(center);
        return new MessageResponseDTO("Logistics center updated successfully.");
    }

    public MessageResponseDTO deleteCenter(Long id) {
        centerRepository.deleteById(id);
        return new MessageResponseDTO("Logistics center deleted successfully.");
    }

    private CenterDTO convertToDTO(Center center) {
        CenterDTO dto = new CenterDTO();
        dto.setId(center.getId());
        dto.setName(center.getName());
        dto.setCapacity(center.getCapacity());
        dto.setStatus(center.getStatus());
        dto.setMaxCapacity(center.getMaxCapacity());
        dto.setCurrentLoad(center.getCurrentLoad());
        dto.setCoordinates(center.getCoordinates());
        return dto;
    }
}