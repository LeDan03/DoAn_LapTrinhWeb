package stu.edu.vn.nhom3.doan_laptrinhweb.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseBody;
import stu.edu.vn.nhom3.doan_laptrinhweb.dto.UserDTO;
import stu.edu.vn.nhom3.doan_laptrinhweb.model.User;
import stu.edu.vn.nhom3.doan_laptrinhweb.repository.UserRepository;

import java.util.ArrayList;
import java.util.Date;
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
    public UserDTO getUserDTOByName(String username)
    {
        User user = getUserByName(username);
        if(user != null)
        {
            UserDTO userDTO = new UserDTO();
            userDTO=UserDTO.builder()
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
    public List<UserDTO> getAllUsers(){
        List<User> users=new ArrayList<>();
        users=userRepository.findAll();
        List<UserDTO> userDTOs=new ArrayList<>();
        userDTOs=users.stream().map(user -> {
            return UserDTO.builder()
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
        user.setCreateBy(new Date());
        user.setUpdateDate(new Date());
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
        UserDTO userDTO=getUserDTOByName(username);
        if(userDTO.getRole_id()== roleService.getAdminRole())
            return true;
        return false;
    }

    public ResponseEntity<String> updateUser(String oldName,UserDTO userDTO) {
        if(userRepository.getUserByName(oldName)!=null)
        {
            userRepository.updateUserByName(oldName
                                            ,userDTO.getName()
                                            ,passwordEncoder.encode(userDTO.getPassword())
                                            ,userDTO.getEmail());
            return ResponseEntity.ok("UPDATE USER SUCCESSFUL");
        }
        return ResponseEntity.notFound().build();
    }
}
