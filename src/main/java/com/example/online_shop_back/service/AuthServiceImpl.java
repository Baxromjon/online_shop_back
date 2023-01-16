package com.example.online_shop_back.service;

import com.example.online_shop_back.entity.Role;
import com.example.online_shop_back.entity.User;
import com.example.online_shop_back.enums.RoleNameEnum;
import com.example.online_shop_back.payload.ApiResult;
import com.example.online_shop_back.payload.RegisterDTO;
import com.example.online_shop_back.repository.AuthService;
import com.example.online_shop_back.repository.RoleRepository;
import com.example.online_shop_back.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService, UserDetailsService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String phoneNumber) throws UsernameNotFoundException {

        return userRepository
                .findFirstByPhoneNumberAndEnabledIsTrueAndAccountNonExpiredIsTrueAndCredentialsNonExpiredIsTrueAndAccountNonLockedIsTrue(phoneNumber)
                .orElseThrow(() -> new UsernameNotFoundException(phoneNumber));
    }


    public UserDetails loadUserByUserId(String userId) throws UsernameNotFoundException {
        return userRepository.findById(UUID.fromString(userId)).orElseThrow(() -> new UsernameNotFoundException("userId"));
    }

    @Override
    public ApiResult register(RegisterDTO registerDTO) {
            Set<Role> role = roleRepository.findByName(RoleNameEnum.ROLE_USER);
            Optional<User> userOptional = userRepository.findByPhoneNumberAndRolesIn(registerDTO.getPhoneNumber(), role);
            User user = userOptional.orElseGet(User::new);
            Boolean exists = userRepository.existsByPhoneNumber(registerDTO.getPhoneNumber());
            String passwordRandom = randomPassword(6).toString();
            if (exists) {
                return new ApiResult(false, "Allready exists");
            }
            if (!userOptional.isPresent()) {
                user.setPhoneNumber(registerDTO.getPhoneNumber());
                user.setPassword(passwordEncoder.encode(passwordRandom));
                user.setRoles(role);
            }
            userRepository.save(user);
            return new ApiResult(passwordRandom,true, "Successfully registered");
    }

    public char[] randomPassword(int length){
        String capitalCaseLetters="ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String lowerCaseLetters = "abcdefghijklmnopqrstuvwxyz";
        String specialCharacters = "!@#$";
        String numbers = "1234567890";

        String combinedChars = capitalCaseLetters + lowerCaseLetters + specialCharacters + numbers;
        Random random = new Random();
        char[] password = new char[length];

        password[0] = lowerCaseLetters.charAt(random.nextInt(lowerCaseLetters.length()));
        password[1] = capitalCaseLetters.charAt(random.nextInt(capitalCaseLetters.length()));
        password[2] = specialCharacters.charAt(random.nextInt(specialCharacters.length()));
        password[3] = numbers.charAt(random.nextInt(numbers.length()));

        for(int i = 4; i< length ; i++) {
            password[i] = combinedChars.charAt(random.nextInt(combinedChars.length()));
        }
        return password;
    }
}
