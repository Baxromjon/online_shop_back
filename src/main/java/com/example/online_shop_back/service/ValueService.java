package com.example.online_shop_back.service;

import com.example.online_shop_back.entity.Detail;
import com.example.online_shop_back.entity.Value;
import com.example.online_shop_back.payload.ApiResult;
import com.example.online_shop_back.payload.ValueDTO;
import com.example.online_shop_back.repository.DetailRepository;
import com.example.online_shop_back.repository.ValueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ValueService {
    @Autowired
    ValueRepository valueRepository;

    @Autowired
    DetailRepository detailRepository;

    public ApiResult add(ValueDTO valueDTO) {
        try {
            Detail detail = detailRepository.findById(valueDTO.getDetailId()).orElseGet(Detail::new);
            Optional<Value> valueOptional = valueRepository.findByNameAndDetail(valueDTO.getName(), detail);
            if (valueOptional.isPresent()) {
                return new ApiResult(false, "This value Allready exists");
            }
            Value value = new Value(valueDTO.getName(), detail);
            valueRepository.save(value);
            return new ApiResult(true, "Successfully added");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public ApiResult edit(UUID id, ValueDTO valueDTO) {
        try {
            Detail detail = detailRepository.findById(valueDTO.getDetailId()).orElseGet(Detail::new);
            Optional<Value> valueOptional = valueRepository.findById(id);
            if (!valueOptional.isPresent()) {
                return new ApiResult(false, "This value not found");
            }
            Value value = valueOptional.get();
            value.setName(valueDTO.getName());
            value.setDetail(detail);
            valueRepository.save(value);
            return new ApiResult(true, "Successfully edited");
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public ApiResult getAll() {
        try {
            List<Value> all = valueRepository.findAll();
            return new ApiResult(all, true);
        }catch (Exception e){
            e.printStackTrace();
            return new ApiResult(false, "Error in get all Values");
        }
    }
}
