package stu.edu.vn.nhom3.doan_laptrinhweb.services;

import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PasswordService {

    @Bean
    public PasswordEncoder passwordEncoders()
    {
        return new BCryptPasswordEncoder();
    }
}
