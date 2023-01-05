package com.example.online_shop_back.service;

import com.example.online_shop_back.entity.CurrencyType;
import com.example.online_shop_back.payload.ApiResult;
import com.example.online_shop_back.payload.CurrencyTypeDTO;
import com.example.online_shop_back.repository.CurrencyTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class CurrencyTypeService {
    @Autowired
    CurrencyTypeRepository currencyTypeRepository;

    public ApiResult add(CurrencyTypeDTO currencyTypeDTO) {
        try {
            Optional<CurrencyType> typeOptional = currencyTypeRepository.findByName(currencyTypeDTO.getName());
            if (typeOptional.isPresent()) {
                return new ApiResult(false, "Currency type allready exists");
            }
            CurrencyType currencyType = new CurrencyType(currencyTypeDTO.getName(), currencyTypeDTO.getDescription());
            currencyTypeRepository.save(currencyType);
            return new ApiResult(true, "Successfully saved");
        } catch (Exception e) {
            e.printStackTrace();
            return new ApiResult(false, "Error in save Currency Type");
        }
    }

    public ApiResult edit(UUID id, CurrencyTypeDTO currencyTypeDTO) {
        try {
            Optional<CurrencyType> typeOptional = currencyTypeRepository.findById(id);
            if (typeOptional.isPresent()) {
                CurrencyType currencyType = typeOptional.get();
                currencyType.setName(currencyTypeDTO.getName());
                currencyType.setDescription(currencyTypeDTO.getDescription());
                currencyTypeRepository.save(currencyType);
                return new ApiResult(true, "Successfully edited");
            }
            return new ApiResult(false, "Currency Type not found");
        } catch (Exception e) {
            e.printStackTrace();
            return new ApiResult(false, "Error in edit Currency type");
        }
    }
}
