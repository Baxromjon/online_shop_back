package com.example.online_shop_back.service;

import com.example.online_shop_back.entity.Address;
import com.example.online_shop_back.entity.Region;
import com.example.online_shop_back.payload.AddressDTO;
import com.example.online_shop_back.payload.ApiResult;
import com.example.online_shop_back.repository.AddressRepository;
import com.example.online_shop_back.repository.RegionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class AddressService {

    @Autowired
    AddressRepository addressRepository;

    @Autowired
    RegionRepository regionRepository;

    public ApiResult add(AddressDTO addressDTO) {
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
                    regionOptional.get()
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
}
