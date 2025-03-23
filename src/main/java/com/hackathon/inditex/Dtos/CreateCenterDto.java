package com.hackathon.inditex.Dtos;

import com.hackathon.inditex.Entities.Center;
import com.hackathon.inditex.Entities.Coordinates;
import jakarta.validation.constraints.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateCenterDto {

    @NotNull(message = "Name cannot be null")
    @NotBlank(message = "Name cannot be blank")
    private String name;

    @NotNull(message = "Capacity cannot be null")
    @NotBlank(message = "Capacity cannot be blank")
    private String capacity;

    @NotNull(message = "Status cannot be null")
    @NotBlank(message = "Status cannot be blank")
    private String status;

    @NotNull(message = "Max capacity cannot be null")
    @Min(value = 1, message = "Max capacity must be at least 1")
    private Integer maxCapacity;

    @NotNull(message = "Current load cannot be null")
    @Min(value = 0, message = "Current load cannot be negative")
    private Integer currentLoad;

    @NotNull(message = "Coordinates cannot be null")
    private Coordinates coordinates;

    public static Center toCenter(CreateCenterDto createCenterDto) {
        Center center = new Center();
        center.setName(createCenterDto.getName());
        center.setCapacity(createCenterDto.getCapacity());
        center.setStatus(createCenterDto.getStatus());
        center.setMaxCapacity(createCenterDto.getMaxCapacity());
        center.setCurrentLoad(createCenterDto.getCurrentLoad());
        center.setCoordinates(createCenterDto.getCoordinates());
        return center;
    }
}


