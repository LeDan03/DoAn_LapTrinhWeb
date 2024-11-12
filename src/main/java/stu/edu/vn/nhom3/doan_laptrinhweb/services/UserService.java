package stu.edu.vn.nhom3.doan_laptrinhweb.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import stu.edu.vn.nhom3.doan_laptrinhweb.dto.UserDTO;
import stu.edu.vn.nhom3.doan_laptrinhweb.model.User;
import stu.edu.vn.nhom3.doan_laptrinhweb.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User getUserByName(String username)
    {
        return userRepository.getUserByName(username);
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

    public void saveUser(User user) {
        userRepository.save(user);
    }

    public boolean isValidUserLogin(String username, String password) {
        if(userRepository.getUserByName(username)!=null)
            if(userRepository.getUserByName(username).getPasswordHash().equals(password))
                return true;
        return false;
    }


}
