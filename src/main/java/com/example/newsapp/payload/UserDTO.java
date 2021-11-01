package com.example.newsapp.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    @NotNull(message = "full name required")
    private String fullName;

    @NotNull(message = "user name required")
    private String userName;


    @NotNull(message = "Password required")
    private String password;

    @NotNull(message = "Role required")
    private Integer roleId;

    @NotNull
    private Boolean enabled;
}
