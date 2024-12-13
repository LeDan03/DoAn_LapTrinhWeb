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

    private RoleService   roleService;
    @Autowired
    PasswordEncoder passwordEncoder;

    public User getUserByEmail(String email)
    {
        return userRepository.getUserByEmail(email);
    }
    public User findUserById(int id)
    {
        return userRepository.findUserById(id);
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
    public ResponseEntity<User> updateUser(String email, UpdateUserDTO userDTO)
    {
        User user = getUserByEmail(email);
        if(getUserByEmail(userDTO.getEmail())==null)
        {
            if(!userDTO.getEmail().isEmpty())
                user.setEmail(userDTO.getEmail());
            if(!userDTO.getNewFullName().isEmpty())
                user.setFullName(userDTO.getNewFullName());
            if(!userDTO.getNewPassword().isEmpty())
                user.setPasswordHash(passwordEncoder.encode(userDTO.getNewPassword()));
            user.setStatus(userDTO.isStatus());
            User result = userRepository.save(user);
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.badRequest().build();
    }

}
