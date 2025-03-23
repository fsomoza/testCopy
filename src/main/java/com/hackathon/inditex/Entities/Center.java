package com.hackathon.inditex.Entities;

import com.hackathon.inditex.Dtos.CenterDto;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "centers", uniqueConstraints = @UniqueConstraint(columnNames = {"latitude", "longitude"}))
public class Center {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String capacity;

    private String status;

    private Integer currentLoad;

    private Integer maxCapacity;

    @Embedded
    private Coordinates coordinates;

    public static CenterDto convertToDTO(Center center) {
        CenterDto dto = new CenterDto();
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
