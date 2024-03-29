package com.example.online_shop_back.service;

import com.example.online_shop_back.entity.Address;
import com.example.online_shop_back.entity.Region;
import com.example.online_shop_back.entity.User;
import com.example.online_shop_back.payload.AddressDTO;
import com.example.online_shop_back.payload.ApiResult;
import com.example.online_shop_back.repository.AddressRepository;
import com.example.online_shop_back.repository.RegionRepository;
import com.example.online_shop_back.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AddressService {

    @Autowired
    AddressRepository addressRepository;

    @Autowired
    RegionRepository regionRepository;

    @Autowired
    UserRepository userRepository;

    public ApiResult add(AddressDTO addressDTO, User user) {
        try {
            Optional<Region> regionOptional = regionRepository.findById(addressDTO.getRegionId());
            if (!regionOptional.isPresent()) {
                return new ApiResult(false, "Region not found");
            }
            Address address = new Address(
                    addressDTO.getStreet(),
                    addressDTO.getHome(),
                    addressDTO.getDistrict(),
                    addressDTO.getAddress(),
                    regionOptional.get(),
                    user
            );
            addressRepository.save(address);
            return new ApiResult(true, "Address successfully saved");
        } catch (Exception exception) {
            exception.printStackTrace();
            return new ApiResult(false, "Error in save Address");
        }
    }

    public ApiResult edit(UUID id, AddressDTO addressDTO) {
        try {
            Optional<Address> addressOptional = addressRepository.findById(id);
            if (!addressOptional.isPresent()) {
                return new ApiResult(false, "This Address not found");
            }
            Optional<Region> regionOptional = regionRepository.findById(addressDTO.getRegionId());
            if (!regionOptional.isPresent()) {
                return new ApiResult(false, "This region not found");
            }
            Address address = addressOptional.get();
            address.setStreet(addressDTO.getStreet());
            address.setHome(addressDTO.getHome());
            address.setDistrict(addressDTO.getDistrict());
            address.setAddress(addressDTO.getAddress());
            address.setRegion(regionOptional.get());
            addressRepository.save(address);
            return new ApiResult(true, "Address Successfully Edited");

        } catch (Exception e) {
            e.printStackTrace();
            return new ApiResult(false, "Error in edit address");
        }
    }

    public ApiResult delete(UUID id) {
        try {
            addressRepository.deleteById(id);
            addressRepository.deleteByAddressId(id);
            return new ApiResult(true, "Successfully deleted");
        } catch (Exception e) {
            e.printStackTrace();
            return new ApiResult(false, "Error in delete");
        }
    }

    public ApiResult getById(UUID id) {
        try {
            Address address = addressRepository.findById(id).orElseThrow();
            return new ApiResult(address, true);
        } catch (Exception e) {
            e.printStackTrace();
            return new ApiResult(false, "Error in get Address by Id");
        }
    }

    public ApiResult getAllByUserId(UUID id) {
        try {
            Optional<User> userOptional = userRepository.findById(id);
            if (!userOptional.isPresent()){
                return new ApiResult(false, "User not found");
            }
            List<Address> addressList = addressRepository.findByUserId(id);
            return new ApiResult(addressList,true);
        } catch (Exception e) {
            e.printStackTrace();
            return new ApiResult(false, "Error in get Address by User");
        }
    }
}
