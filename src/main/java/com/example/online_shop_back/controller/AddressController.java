package com.example.online_shop_back.controller;

import com.example.online_shop_back.entity.User;
import com.example.online_shop_back.payload.AddressDTO;
import com.example.online_shop_back.payload.ApiResult;
import com.example.online_shop_back.service.AddressService;
import com.example.online_shop_back.utils.CurrentUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/address")
public class AddressController {
    @Autowired
    AddressService addressService;

    @PostMapping("/add_address")
    public HttpEntity<?> add(@RequestBody AddressDTO addressDTO, @CurrentUser User user) {
        ApiResult add = addressService.add(addressDTO, user);
        return ResponseEntity.status(add.getSuccess() ? HttpStatus.CREATED : HttpStatus.CONFLICT).body(add);
    }

    @PostMapping("/edit_address/{id}")
    public HttpEntity<?> edit(@PathVariable UUID id, @RequestBody AddressDTO addressDTO) {
        ApiResult edit = addressService.edit(id, addressDTO);
        return ResponseEntity.status(edit.getSuccess() ? HttpStatus.CREATED : HttpStatus.CONFLICT).body(edit);
    }

    @DeleteMapping("/delete/{id}")
    public HttpEntity<?> delete(@PathVariable UUID id) {
        ApiResult delete = addressService.delete(id);
        return ResponseEntity.status(delete.getSuccess() ? HttpStatus.OK : HttpStatus.CONFLICT).body(delete);
    }

    @GetMapping("/get_address_by_id/{id}")
    public HttpEntity<?> getById(@PathVariable UUID id) {
        ApiResult byId = addressService.getById(id);
        return ResponseEntity.status(byId.getSuccess() ? HttpStatus.OK : HttpStatus.CONFLICT).body(byId);
    }

    @GetMapping("/get_all_by_userId/{id}")
    public HttpEntity<?> getAllByUserId(@PathVariable UUID id) {
        ApiResult allByUserId = addressService.getAllByUserId(id);
        return ResponseEntity.status(allByUserId.getSuccess() ? HttpStatus.OK : HttpStatus.CONFLICT).body(allByUserId);
    }

}
