package com.example.online_shop_back.service;

import com.example.online_shop_back.entity.CurrencyType;
import com.example.online_shop_back.entity.PayType;
import com.example.online_shop_back.payload.ApiResult;
import com.example.online_shop_back.payload.PayTypeDTO;
import com.example.online_shop_back.repository.CurrencyTypeRepository;
import com.example.online_shop_back.repository.PayTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class PayTypeService {
    @Autowired
    PayTypeRepository paytypeRepository;

    @Autowired
    CurrencyTypeRepository currencyTypeRepository;


    public ApiResult add(PayTypeDTO payTypeDTO) {

        try {
            Optional<CurrencyType> currencyTypeOptional = currencyTypeRepository.findById(payTypeDTO.getCurrencyTypeId());
            if (!currencyTypeOptional.isPresent()) {
                return new ApiResult(false, "Currency type not found");
            }
            Optional<PayType> payTypeOptional = paytypeRepository.findByNameAndCurrencyType(payTypeDTO.getName(), currencyTypeOptional.get());
            if (payTypeOptional.isPresent()) {
                return new ApiResult(false, "Pay type allready exists");
            }
            PayType payType = new PayType(currencyTypeOptional.get(), payTypeDTO.getName());
            paytypeRepository.save(payType);
            return new ApiResult(true, "Pay Type successfully saved");
        } catch (Exception e) {
            e.printStackTrace();
            return new ApiResult(false, "Error in save Pay Type");
        }

    }

    public ApiResult edit(UUID id, PayTypeDTO payTypeDTO) {
        try {
            Optional<CurrencyType> currencyTypeOptional = currencyTypeRepository.findById(payTypeDTO.getCurrencyTypeId());
            if (!currencyTypeOptional.isPresent()) {
                return new ApiResult<>(false, "Currency type not found");
            }
            Optional<PayType> payTypeOptional = paytypeRepository.findById(id);
            if (!payTypeOptional.isPresent()) {
                return new ApiResult(false, "Pay type not found");
            }
            PayType payType = payTypeOptional.get();
            payType.setCurrencyType(currencyTypeOptional.get());
            payType.setName(payTypeDTO.getName());
            paytypeRepository.save(payType);
            return new ApiResult(true, "Successfully saved");
        } catch (Exception e) {
            e.printStackTrace();
            return new ApiResult(false, "Error in edit Paytype");
        }
    }
}
