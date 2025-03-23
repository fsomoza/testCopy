package com.hackathon.inditex.Dtos;

import com.hackathon.inditex.Entities.Coordinates;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CenterDto {
    private Long id;
    private String name;
    private String capacity;
    private String status;
    private Integer currentLoad;
    private Integer maxCapacity;
    private Coordinates coordinates;
}
