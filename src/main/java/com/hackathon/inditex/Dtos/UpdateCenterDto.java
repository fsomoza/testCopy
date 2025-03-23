package com.hackathon.inditex.Dtos;

import com.hackathon.inditex.Entities.Center;
import com.hackathon.inditex.Entities.Coordinates;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Optional;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCenterDto {
    private String name;
    private String capacity;
    private String status;
    private Integer maxCapacity;
    private Integer currentLoad;
    private Coordinates coordinates;

    public static Center toCenter(UpdateCenterDto updateCenterDto,Center center) {

        // Only update fields that are provided in the DTO
        if (updateCenterDto.getName() != null) {
            center.setName(updateCenterDto.getName());
        }

        if (updateCenterDto.getCapacity() != null) {
            center.setCapacity(updateCenterDto.getCapacity());
        }

        if (updateCenterDto.getStatus() != null) {
            center.setStatus(updateCenterDto.getStatus());
        }

        if (updateCenterDto.getMaxCapacity() != null) {
            center.setMaxCapacity(updateCenterDto.getMaxCapacity());
        }

        if (updateCenterDto.getCurrentLoad() != null) {
            center.setCurrentLoad(updateCenterDto.getCurrentLoad());
        }

        if (updateCenterDto.getCoordinates() != null) {
            center.setCoordinates(updateCenterDto.getCoordinates());
        }
        return center;
    }
}
