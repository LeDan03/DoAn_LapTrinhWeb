package stu.edu.vn.nhom3.doan_laptrinhweb.services;

import jakarta.transaction.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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

    public void saveUser() {
        User user=new User();
        user.setName("aaa");
        user.setEmail("bbb");
        user.setCreateBy(new Date());
        user.setStatus(true);
        user.setUpdateDate(new Date());
        user.setRole_id(1);
        user.setUs_passwordHash("a99999");
        userRepository.save(user);

    }
}
