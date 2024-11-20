package stu.edu.vn.nhom3.doan_laptrinhweb.services;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import stu.edu.vn.nhom3.doan_laptrinhweb.dto.LoginUserDTO;
import stu.edu.vn.nhom3.doan_laptrinhweb.dto.RegisterUserDTO;
import stu.edu.vn.nhom3.doan_laptrinhweb.model.User;
import stu.edu.vn.nhom3.doan_laptrinhweb.repository.UserRepository;

@Service
public class AuthenticationService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    public AuthenticationService(
            UserRepository userRepository,
            AuthenticationManager authenticationManager,
            PasswordEncoder passwordEncoder
    ) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
    public User signup(RegisterUserDTO input) {
        User user = new User();
        user.setEmail(input.getEmail());
        user.setName(input.getName());
        user.setPasswordHash(passwordEncoder.encode(input.getPassword()));
        user.setStatus(true);
        user.setRole_id(2);

        return userRepository.save(user);
    }

    //
    public User authenticate(LoginUserDTO input) {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            // input.getEmail(),
                            input.getName(),
                            input.getPassword()
                    )
            );
        return userRepository.findByName(input.getName())
                .orElseThrow();
    }
}
