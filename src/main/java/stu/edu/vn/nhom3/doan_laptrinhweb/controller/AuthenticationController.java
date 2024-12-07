package stu.edu.vn.nhom3.doan_laptrinhweb.controller;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import stu.edu.vn.nhom3.doan_laptrinhweb.dto.LoginUserDTO;
import stu.edu.vn.nhom3.doan_laptrinhweb.dto.RegisterUserDTO;
import stu.edu.vn.nhom3.doan_laptrinhweb.dto.UpdateUserDTO;
import stu.edu.vn.nhom3.doan_laptrinhweb.model.User;
import stu.edu.vn.nhom3.doan_laptrinhweb.repository.UserRepository;
import stu.edu.vn.nhom3.doan_laptrinhweb.response.LoginResponse;
import stu.edu.vn.nhom3.doan_laptrinhweb.services.AuthenticationService;
import stu.edu.vn.nhom3.doan_laptrinhweb.services.JwtService;
import stu.edu.vn.nhom3.doan_laptrinhweb.services.UserService;

@CrossOrigin("*")
@RestController
@RequestMapping(value = "/auth")
public class AuthenticationController {

    private final JwtService jwtService;
    private final AuthenticationService authenticationService;
    private final BCryptPasswordEncoder passwordEncoder;

    private final UserService userService;
    @Autowired
    private final UserRepository userRepository;

    public AuthenticationController(JwtService jwtService, AuthenticationService authenticationService, BCryptPasswordEncoder passwordEncoder, UserService userService, UserRepository userRepository) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @PostMapping("/signup")
    public ResponseEntity<User> register(@RequestBody RegisterUserDTO registerUserDto) {
        if(!jwtService.isDuplicateEmail(registerUserDto.getEmail())&&registerUserDto.getEmail()!=null
            &&registerUserDto.getPassword()!=null)
        {
            User registeredUser = authenticationService.signup(registerUserDto);
            User toShowUser = new User();
            toShowUser.setEmail(registerUserDto.getEmail());
            toShowUser.setFullName(registerUserDto.getFullName());
            return ResponseEntity.ok(toShowUser);
        }
        return ResponseEntity.badRequest().body(null);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@RequestBody LoginUserDTO loginUserDto) {
        User authenticatedUser = authenticationService.authenticate(loginUserDto);
        String jwtToken = jwtService.generateToken(authenticatedUser);

        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setToken(jwtToken);
        loginResponse.setExpiresIn(jwtService.getExpirationTime());
        loginResponse.setEmail(authenticatedUser.getEmail());
        loginResponse.setFullname(authenticatedUser.getFullName());
        loginResponse.setRoleId(authenticatedUser.getRole_id());
        loginResponse.setStatus(authenticatedUser.isStatus());
        Authentication authentication = new UsernamePasswordAuthenticationToken(authenticatedUser, null, authenticatedUser.getAuthorities());
        return ResponseEntity.ok(loginResponse);
    }

    @PutMapping(value = "/update")
    public ResponseEntity<User> update(@RequestBody UpdateUserDTO updateUserDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated())
            return ResponseEntity.status(403).body(null);

        User authenticationUser  = (User ) authentication.getPrincipal();
        if (!authenticationUser .getEmail().equals(updateUserDto.getEmail()))
            return ResponseEntity.status(403).body(null);

        User updatedUser  = userService.updateUserByEmail(updateUserDto);
        return ResponseEntity.ok(updatedUser );
    }
}

