package com.example.online_shop_back.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PasswordDTO {
    @NotBlank
    @Size(min = 6, max = 12)
    private String password;

    @NotBlank
    @Size(min = 6, max = 12)
    private String prePassword;
}
