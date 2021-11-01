package com.example.newsapp.component;

import com.example.newsapp.entity.Role;
import com.example.newsapp.entity.User;
import com.example.newsapp.enums.Permissions;
import com.example.newsapp.repository.RoleRepository;
import com.example.newsapp.repository.UserRepository;
import com.example.newsapp.urils.AppConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;

import static com.example.newsapp.enums.Permissions.*;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {
    @Value("${spring.sql.init.mode}")
    private String modeType;

    final UserRepository userRepository;

    final RoleRepository roleRepository;

    final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        if (modeType.equals("always")) {
            Permissions[] values = Permissions.values();
            Role admin = roleRepository.save(new Role(
                    AppConstants.ADMIN,
                    Arrays.asList(values), "Admin all permissions"
            ));

            Role user = roleRepository.save(new Role(
                    AppConstants.USER,
                    Arrays.asList(ADD_COMMENT, EDIT_COMMENT, DELETE_MY_COMMENT, EDIT_MY_COMMENT), "User"
            ));
            userRepository.save(new User(
                    "Admin", "Admin", passwordEncoder.encode("123"), admin, true
            ));

            userRepository.save(new User(
                    "User", "User", passwordEncoder.encode("123"), user, true
            ));


        }
    }

}
