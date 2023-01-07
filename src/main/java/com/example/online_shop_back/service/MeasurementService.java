package com.example.online_shop_back.service;

import com.example.online_shop_back.entity.Measurement;
import com.example.online_shop_back.payload.ApiResult;
import com.example.online_shop_back.payload.MeasurementDTO;
import com.example.online_shop_back.repository.MeasurementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class MeasurementService {
    @Autowired
    MeasurementRepository measurementRepository;

    public ApiResult add(MeasurementDTO measurementDTO) {
        Optional<Measurement> byName = measurementRepository.findByName(measurementDTO.getName());
        if (byName.isPresent()) {
            return new ApiResult(false, "This measurement allready exists");
        }
        Measurement measurement = new Measurement(measurementDTO.getName());
        measurementRepository.save(measurement);
        return new ApiResult<>(true, "Successfully added");
    }

    public ApiResult edit(UUID id, MeasurementDTO measurementDTO) {
        Optional<Measurement> byId = measurementRepository.findById(id);
        if (!byId.isPresent()) {
            return new ApiResult(false, "Measurement not found");
        }
        Measurement measurement = byId.get();
        measurement.setName(measurementDTO.getName());
        measurementRepository.save(measurement);
        return new ApiResult(true, "Successfully edited");

    }

    public ApiResult getAll() {
        try {
            List<Measurement> all = measurementRepository.findAll();
            return new ApiResult(all, true);
        }catch (Exception e){
            e.printStackTrace();
            return new ApiResult(false, "Error in get all Measurement");
        }
    }
}
