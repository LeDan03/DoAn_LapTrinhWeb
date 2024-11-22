package stu.edu.vn.nhom3.doan_laptrinhweb.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import stu.edu.vn.nhom3.doan_laptrinhweb.dto.RegisterUserDTO;
import stu.edu.vn.nhom3.doan_laptrinhweb.model.User;
import stu.edu.vn.nhom3.doan_laptrinhweb.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    PasswordService   passwordService;
    RoleService   roleService;
    @Autowired
    PasswordEncoder passwordEncoder;

    public User getUserByName(String username)
    {
        return userRepository.getUserByName(username);
    }
    public RegisterUserDTO getUserDTOByName(String username)
    {
        User user = getUserByName(username);
        if(user != null)
        {
            RegisterUserDTO userDTO = new RegisterUserDTO();
            userDTO= RegisterUserDTO.builder()
                    .id(user.getId())
                    .name(user.getName())
                    .email(user.getEmail())
                    .password(user.getPasswordHash())
                    .status(user.isStatus())
                    .build();
            return userDTO;
        }
        return null;
    }
    public List<RegisterUserDTO> getAllUsers(){
        List<User> users=new ArrayList<>();
        users=userRepository.findAll();
        List<RegisterUserDTO> userDTOs=new ArrayList<>();
        userDTOs=users.stream().map(user -> {
            return RegisterUserDTO.builder()
                    .id(user.getId())
                    .name(user.getName())
                    .email(user.getEmail())
                    .build();
        }).collect(Collectors.toList());
        return userDTOs;
    }

    public User putInfoInUser(String username, String email ,String password,int role_id) {
        User user=new User();
        user.setName(username);
        user.setEmail(email);
        user.setPasswordHash(passwordEncoder.encode(password));
        user.setRole_id(role_id);
        return user;
    }

    public void saveUser(User user) {
        userRepository.save(user);
    }

    public boolean isValidUserLogin(String username, String password) {
        if(userRepository.getUserByName(username)!=null)
            if(passwordEncoder.matches(password,userRepository.getUserByName(username).getPasswordHash()))
                return true;
        return false;
    }

    public boolean isValidUserRegister(String username, String password) {
        User user= userRepository.getUserByName(username);
        if(user!=null)
            return false;
        else{
            if (username.length()<8||password.length()<8) {
                return false;
            }
        }
        return true;
    }

    public boolean isAdmin(String username) {
        RegisterUserDTO userDTO=getUserDTOByName(username);
        if(userDTO.getRole_id()== roleService.getAdminRole())
            return true;
        return false;
    }

    public void updateUser(String oldName, User user) {
        if(userRepository.getUserByName(oldName)!=null)
        {
            userRepository.updateUserByName(oldName
                                            ,user.getName()
                                            ,passwordEncoder.encode(user.getPassword())
                                            ,user.getEmail());
            ResponseEntity.ok("UPDATE USER SUCCESSFUL");
            return;
        }
        ResponseEntity.notFound().build();
    }
}
