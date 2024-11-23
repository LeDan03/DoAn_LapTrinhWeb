package stu.edu.vn.nhom3.doan_laptrinhweb.controller;

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
    @Autowired
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
        if(!jwtService.isDuplicateUsername(registerUserDto.getName())&&registerUserDto.getName()!=null
            &&registerUserDto.getPassword()!=null&&registerUserDto.getEmail()!=null)
        {
            User registeredUser = authenticationService.signup(registerUserDto);
            return ResponseEntity.ok(registeredUser);
        }
        return ResponseEntity.status(400).header("Registered failure","Register information is invalid").body(null);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@RequestBody LoginUserDTO loginUserDto) {
        User authenticatedUser = authenticationService.authenticate(loginUserDto);
        String jwtToken = jwtService.generateToken(authenticatedUser);

        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setToken(jwtToken);
        loginResponse.setExpiresIn(jwtService.getExpirationTime());
        loginResponse.setUsername(authenticatedUser.getUsername());
        loginResponse.setStatus(authenticatedUser.isStatus());
        loginResponse.setRoleId(authenticatedUser.getRole_id());
        Authentication authentication = new UsernamePasswordAuthenticationToken(authenticatedUser, null, authenticatedUser.getAuthorities());
        return ResponseEntity.ok(loginResponse);
    }

    @PutMapping(value = "/update")
    public ResponseEntity<User> update(@RequestBody UpdateUserDTO updateUserDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User authenticationUser = (User) authentication.getPrincipal();

        if(!authenticationUser.getName().equals(updateUserDto.getOldName()))
            return ResponseEntity.status(403).body(null);
        else {
            authenticationUser.setName(updateUserDto.getNewName());
            authenticationUser.setEmail(updateUserDto.getEmail());
            authenticationUser.setPasswordHash(passwordEncoder.encode(updateUserDto.getPassword()));

           // userService.updateUser(updateUserDto.getOldName(), authenticationUser);
            userRepository.save(authenticationUser);
            return ResponseEntity.ok().body(authenticationUser);
        }
    }
}
