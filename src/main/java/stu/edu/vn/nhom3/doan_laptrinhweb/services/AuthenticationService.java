package stu.edu.vn.nhom3.doan_laptrinhweb.services;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import stu.edu.vn.nhom3.doan_laptrinhweb.dto.LoginUserDTO;
import stu.edu.vn.nhom3.doan_laptrinhweb.dto.RegisterUserDTO;
import stu.edu.vn.nhom3.doan_laptrinhweb.model.User;
import stu.edu.vn.nhom3.doan_laptrinhweb.repository.RoleRepository;
import stu.edu.vn.nhom3.doan_laptrinhweb.repository.UserRepository;

@Service
public class AuthenticationService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;
    private final RoleService roleService;
    private final RoleRepository roleRepository;

    public AuthenticationService(
            UserRepository userRepository,
            AuthenticationManager authenticationManager,
            PasswordEncoder passwordEncoder,
            RoleService roleService, RoleRepository roleRepository) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
        this.roleRepository = roleRepository;
    }
    public User signup(RegisterUserDTO input) {
        User user = new User();
        user.setEmail(input.getEmail());
        user.setPasswordHash(passwordEncoder.encode(input.getPassword()));
        user.setStatus(true);
        user.setRole_id(2);
        user.setFullName(input.getFullName());
        return userRepository.save(user);
    }

    public User authenticate(LoginUserDTO input) {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            input.getEmail(),
                            input.getPassword()
                    )
            );
        return userRepository.findByEmail(input.getEmail())
                .orElseThrow();
    }
}
