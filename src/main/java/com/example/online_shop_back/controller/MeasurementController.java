package com.example.online_shop_back.controller;

import com.example.online_shop_back.payload.ApiResult;
import com.example.online_shop_back.payload.MeasurementDTO;
import com.example.online_shop_back.service.MeasurementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/measurement")
public class MeasurementController {

    @Autowired
    MeasurementService measurementService;

    @PostMapping("/add_measurement")
    public HttpEntity<?> addMeasurement(@RequestBody MeasurementDTO measurementDTO) {
        ApiResult add = measurementService.add(measurementDTO);
        return ResponseEntity.status(add.getSuccess() ? HttpStatus.CREATED : HttpStatus.CONFLICT).body(add);
    }

}
