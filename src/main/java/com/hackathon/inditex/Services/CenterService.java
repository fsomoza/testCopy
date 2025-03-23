package com.hackathon.inditex.Services;

import com.hackathon.inditex.Dtos.CenterDto;
import com.hackathon.inditex.Dtos.CreateCenterDto;
import com.hackathon.inditex.Dtos.MessageResponseDto;
import com.hackathon.inditex.Dtos.UpdateCenterDto;
import com.hackathon.inditex.Entities.Center;
import com.hackathon.inditex.Entities.Coordinates;
import com.hackathon.inditex.Repositories.CenterRepository;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CenterService {

    @Autowired
    private CenterRepository centerRepository;

    public MessageResponseDto createCenter(CreateCenterDto createCenterDto) {
        double latitude=createCenterDto.getCoordinates().getLatitude();
        double longitude=createCenterDto.getCoordinates().getLongitude();
        String capacity=createCenterDto.getCapacity();

        if(checkIfCenterExists(latitude, longitude)){
            return new MessageResponseDto("There is already a logistics center in that position.");
        }
        if (createCenterDto.getCurrentLoad() > createCenterDto.getMaxCapacity()) {
            return new MessageResponseDto("Current load cannot exceed max capacity.");
        }

        if(!checkCapacityFormat(capacity)){
            return  new MessageResponseDto("Illegal format. Allowed: B, M, S, BM, BS, MS, or BMS.");
        }

        Center centerEntity =CreateCenterDto.toCenter(createCenterDto);
        centerRepository.save(centerEntity);
        return new MessageResponseDto("Logistics center created successfully.");

    }

    public MessageResponseDto updateCenter(UpdateCenterDto updateCenterDto, long id){

        String capacity=updateCenterDto.getCapacity();

        Optional<Center> center = centerRepository.findById(id);
        if(!center.isPresent()){
            return new MessageResponseDto("Center Id doesn't exist");
        }
        Center oldCenter=center.get();
        if(updateCenterDto.getCoordinates()!=null && !hasSameCoordinates(oldCenter.getCoordinates(),updateCenterDto.getCoordinates())){
            if(checkIfCenterExists(updateCenterDto.getCoordinates().getLatitude(), updateCenterDto.getCoordinates().getLongitude())){
                return new MessageResponseDto("There is already a logistics center in that position.");
            }
        }

        if (updateCenterDto.getCurrentLoad()!=null && updateCenterDto.getMaxCapacity()!=null  && updateCenterDto.getCurrentLoad() > updateCenterDto.getMaxCapacity()) {
            return new MessageResponseDto("Current load cannot exceed max capacity.");
        }

        if(updateCenterDto.getCapacity()!=null && !checkCapacityFormat(capacity)){
            return  new MessageResponseDto("Illegal format. Allowed: B, M, S, BM, BS, MS, or BMS.");
        }

        Center updatedCenter= UpdateCenterDto.toCenter(updateCenterDto,center.get());
        centerRepository.save(updatedCenter);

        return new MessageResponseDto("Logistics center updated successfully.");    }

    public List<CenterDto> getAllCenters() {
        List<Center> centers = centerRepository.findAll();
        return centers.stream()
                .map(Center::convertToDTO)
                .collect(Collectors.toList());
    }

    public MessageResponseDto deleteCenter(Long id) {
        Optional<Center> center = centerRepository.findById(id);
        if(!center.isPresent()){
            return new MessageResponseDto("Center Id doesn't exist");
        }
        centerRepository.deleteById(id);
        return new MessageResponseDto("Logistics center deleted successfully.");
    }



    private Boolean checkIfCenterExists(double latitude, double longitude){
        Optional<Center> existingCenter= centerRepository.findByLatitudeAndLongitude(latitude,longitude);
        if (existingCenter.isPresent()) {
            return true;
        }else {
            return false;
        }
    }


    private boolean checkCapacityFormat(String capacity){
        if (!capacity.matches("^(B|M|S|BM|BS|MS|BMS)$")) {
            return false;
        }
        return true;
    }

    private boolean hasSameCoordinates(Coordinates oldCoordinates, Coordinates newCoordinates){


        if(!oldCoordinates.getLatitude().equals(newCoordinates.getLatitude())){
            return false;
        }
        if(!oldCoordinates.getLongitude().equals(newCoordinates.getLongitude())){
            return false;
        }
        return true;
    }



}
