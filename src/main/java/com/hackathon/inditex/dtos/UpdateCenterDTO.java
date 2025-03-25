package com.hackathon.inditex.dtos;

import com.hackathon.inditex.Entities.Coordinates;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCenterDTO {
    private String name;
    private String capacity;
    private String status;
    private Integer maxCapacity;
    private Integer currentLoad;
    private Coordinates coordinates;
}