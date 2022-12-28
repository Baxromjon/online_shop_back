package com.example.online_shop_back.payload;

import com.example.online_shop_back.utils.ValidPassword;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
//@ValidPassword(password = "password", prePassword = "prePassword")
public class RegisterDTO {
    private UUID id;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @Pattern(regexp = "\\(?\\+[0-9]{1,3}\\)? ?-?[0-9]{1,3} ?-?[0-9]{3,5} ?-?[0-9]{5}( ?-?[0-9]{3})? ?(\\w{1,10}\\s?\\d{1,6})?")
    private String phoneNumber;

    @NotBlank
    @Size(min = 6, max = 12)
    private String password;

    @NotBlank
    @Size(min = 6, max = 12)
    private String prePassword;
}
