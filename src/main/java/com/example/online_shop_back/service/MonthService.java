package com.example.online_shop_back.service;

import com.example.online_shop_back.entity.Month;
import com.example.online_shop_back.payload.ApiResult;
import com.example.online_shop_back.payload.MonthDTO;
import com.example.online_shop_back.repository.MonthRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class MonthService {
    @Autowired
    MonthRepository monthRepository;

    public ApiResult add(MonthDTO monthDTO) {
        try {
            Optional<Month> optional = monthRepository.findByMonth(monthDTO.getMonth());
            if (optional.isPresent()) {
                return new ApiResult(false, "this month allready added");
            }
            Month month = new Month(monthDTO.getMonth());
            monthRepository.save(month);
            return new ApiResult(true, "successfully added");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public ApiResult edit(UUID id, MonthDTO monthDTO) {
        try {
            Optional<Month> optional = monthRepository.findById(id);
            if (!optional.isPresent()) {
                return new ApiResult(false, "Month not found");
            }
            Month month = optional.get();
            month.setMonth(monthDTO.getMonth());
            monthRepository.save(month);
            return new ApiResult(true, "Successfully edited");
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public ApiResult getAll() {
        try {
            List<Month> all = monthRepository.findAll();
            return new ApiResult(all, true);
        }catch (Exception e){
            e.printStackTrace();
            return new ApiResult(false, "Error in get all Months");
        }
    }

    public ApiResult delete(UUID id) {
        try {
            monthRepository.deleteById(id);
            return new ApiResult(true, "Successfully deleted");
        }catch (Exception e){
            e.printStackTrace();
            return new ApiResult(false, "Error in delete month");
        }
    }
}
