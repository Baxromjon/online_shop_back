package com.example.online_shop_back.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private UUID id;
    @NotNull
    private String firstName;
    @NotNull
    private String lastName;
    private String phoneNumber;
    private Date birthDate;


}
