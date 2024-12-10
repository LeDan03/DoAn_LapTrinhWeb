package stu.edu.vn.nhom3.doan_laptrinhweb.services;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import stu.edu.vn.nhom3.doan_laptrinhweb.dto.RegisterUserDTO;
import stu.edu.vn.nhom3.doan_laptrinhweb.dto.UpdateUserDTO;
import stu.edu.vn.nhom3.doan_laptrinhweb.model.User;
import stu.edu.vn.nhom3.doan_laptrinhweb.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    RoleService   roleService;
    @Autowired
    PasswordEncoder passwordEncoder;

    public User getUserByEmail(String email)
    {
        return userRepository.getUserByEmail(email);
    }
    public RegisterUserDTO getUserDTOByEmail(String email)
    {
        User user = getUserByEmail(email);
        if(user != null)
        {
            RegisterUserDTO userDTO = new RegisterUserDTO();
            userDTO= RegisterUserDTO.builder()
                    .id(user.getId())
                    .email(user.getEmail())
                    .status(user.isStatus())
                    .fullName(user.getFullName())
                    .build();
            return userDTO;
        }
        return null;
    }
    @Transactional
    public User updateUserByEmail(UpdateUserDTO userDTO)
    {
        User user = userRepository.getUserByEmail(userDTO.getEmail());
        if (user != null){
            userRepository.updateUserByEmail(userDTO.getEmail(),
                    userDTO.getNewFullName(),
                    passwordEncoder.encode(userDTO.getNewPassword()));
            user = userRepository.getUserByEmail(userDTO.getEmail());
        }
        return  user;
    }

    public boolean isValidUserLogin(String email, String password) {
        if(userRepository.getUserByEmail(email)!=null)
            if(passwordEncoder.matches(password,userRepository.getUserByEmail(email).getPasswordHash()))
                return true;
        return false;
    }

    public boolean isValidUserRegister(String email, String password) {
        User user= userRepository.getUserByEmail(email);
        if(user!=null)
            return false;
        else{
            if (password.length()<8) {
                return false;
            }
        }
        return true;
    }

}
