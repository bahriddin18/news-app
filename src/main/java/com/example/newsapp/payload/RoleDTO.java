package com.example.newsapp.payload;

import com.example.newsapp.enums.Permissions;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleDTO {

    @NotBlank
    private String name;

    private String description;

    @NotEmpty
    private List<Permissions> permissions;


}
