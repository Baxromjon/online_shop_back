package com.example.online_shop_back.service;

import com.example.online_shop_back.entity.User;
import com.example.online_shop_back.payload.ApiResult;
import com.example.online_shop_back.payload.PasswordDTO;
import com.example.online_shop_back.payload.UserDTO;
import com.example.online_shop_back.repository.UserRepository;
import com.example.online_shop_back.security.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;


    public ApiResult editPassword(UUID id, PasswordDTO passwordDTO) {
        try {
            User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("user", "id", id));
            if (passwordDTO.getPassword().equals(passwordDTO.getPrePassword())) {
                user.setPassword(passwordEncoder.encode(passwordDTO.getPassword()));
                userRepository.save(user);
                return new ApiResult<>(true, "Successfully edited");
            }
            return new ApiResult<>(false, "Password don`t match");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public ApiResult editProfil(UUID id, UserDTO userDTO) {
        try {
            Optional<User> userOptional = userRepository.findById(id);
            User user=userOptional.get();
            user.setFirstName(userDTO.getFirstName());
            user.setLastName(userDTO.getLastName());
            user.setPhoneNumber(userDTO.getPhoneNumber()==null? user.getPhoneNumber() : userDTO.getPhoneNumber());
            user.setBirthDate(userDTO.getBirthDate());
            userRepository.save(user);
            return new ApiResult(true, "Successfully edited");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
