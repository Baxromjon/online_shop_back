package com.example.online_shop_back.service;


import com.example.online_shop_back.entity.Criteria;
import com.example.online_shop_back.payload.ApiResult;
import com.example.online_shop_back.payload.CriteriaDTO;
import com.example.online_shop_back.repository.CriteriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class CriteriaService {
    @Autowired
    CriteriaRepository criteriaRepository;

    public ApiResult add(CriteriaDTO criteriaDTO) {
        try {
            Criteria criteria = new Criteria(criteriaDTO.getName(), criteriaDTO.getDescription(), criteriaDTO.getNegative());
            criteriaRepository.save(criteria);
            return new ApiResult(true, "Successfully saved");
        } catch (Exception e) {
            e.printStackTrace();
            return new ApiResult(false, "Error in save Criteria");
        }
    }

    public ApiResult edit(UUID id, CriteriaDTO criteriaDTO) {
        try {
            Optional<Criteria> optional = criteriaRepository.findById(id);
            if (optional.isPresent()) {
                Criteria criteria = optional.get();
                criteria.setName(criteriaDTO.getName());
                criteria.setDescription(criteriaDTO.getDescription());
                criteria.setNegative(criteriaDTO.getNegative());
                criteriaRepository.save(criteria);
                return new ApiResult(true, "Successfully edited");
            }
            return new ApiResult(false, "Criteria not found");
        } catch (Exception e) {
            e.printStackTrace();
            return new ApiResult(false, "Error in edit Criteria");
        }
    }
}
