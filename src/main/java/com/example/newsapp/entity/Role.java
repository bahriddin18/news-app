package com.example.newsapp.entity;

import com.example.newsapp.entity.template.AbsEntity;
import com.example.newsapp.enums.Permissions;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Role extends AbsEntity {

    @Column(unique = true, nullable = false)
    private String name; // ADMIN, USER ...


    @Enumerated(EnumType.STRING)
    @ElementCollection
    private List<Permissions> permissions;

    @Column(length = 500)
    private String description;



}
