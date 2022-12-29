package com.example.online_shop_back.service;

import com.example.online_shop_back.entity.Detail;
import com.example.online_shop_back.payload.ApiResult;
import com.example.online_shop_back.payload.DetailDTO;
import com.example.online_shop_back.repository.DetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class DetailService {
    @Autowired
    DetailRepository detailRepository;

    public ApiResult add(DetailDTO detailDTO) {
        try {
            Optional<Detail> optional = detailRepository.findByName(detailDTO.getName());
            if (optional.isPresent()) {
                return new ApiResult(false, "Detail allready added");
            }
            Detail detail = new Detail(detailDTO.getName());
            detailRepository.save(detail);
            return new ApiResult(true, "Successfully added");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public ApiResult edit(UUID id, DetailDTO detailDTO) {
        try {
            Optional<Detail> detailOptional = detailRepository.findById(id);
            if (!detailOptional.isPresent()) {
                return new ApiResult(false, "Detail not found");
            }
            Detail detail = detailOptional.get();
            detail.setName(detailDTO.getName());
            detailRepository.save(detail);
            return new ApiResult(true, "Successfully edited");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
