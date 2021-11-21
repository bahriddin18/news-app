package com.example.newsapp.config;

import com.example.newsapp.entity.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing
public class ReturnWhoWrote {
    @Bean
    AuditorAware<User> auditorAware() {
        return new WhoWrote();
    } // Who wrote
}
