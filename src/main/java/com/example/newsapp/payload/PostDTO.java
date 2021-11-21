package com.example.newsapp.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostDTO {

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String text;

    private String url; // url is not nullable because service have default url
}
