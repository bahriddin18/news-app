package com.example.newsapp.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterDTO {

    @NotNull(message = "full name not empty")
    private String fullName;

    @NotNull(message = "user name not empty")
    private String userName;


    @NotNull(message = "Password not empty")
    private String password;


    @NotNull(message = "Password not empty")
    private String prePassword;


}
