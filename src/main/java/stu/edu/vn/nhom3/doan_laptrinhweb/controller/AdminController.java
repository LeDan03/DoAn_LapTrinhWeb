package stu.edu.vn.nhom3.doan_laptrinhweb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import stu.edu.vn.nhom3.doan_laptrinhweb.dto.RegisterUserDTO;
import stu.edu.vn.nhom3.doan_laptrinhweb.model.User;
import stu.edu.vn.nhom3.doan_laptrinhweb.services.AdminService;
import stu.edu.vn.nhom3.doan_laptrinhweb.services.UserService;

import java.util.List;

@RestController
@RequestMapping(value = "/admin")
public class AdminController {
    @Autowired
    AdminService adminService;

    @Autowired
    UserService userService;

    @DeleteMapping(value = "/deleteuser")
    public ResponseEntity<String> disableUser(String username) {
        return ResponseEntity.ok( adminService.disableUser(username,userService.getUserDTOByName(username)));
    }
    @GetMapping(value = "/getall")
    public ResponseEntity<List<RegisterUserDTO>> getAllUsers() {
        List<RegisterUserDTO> userDTOList = userService.getAllUsers();
        return ResponseEntity.ok(userDTOList);
    }

    @GetMapping("{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") int id) {
        User user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }
}
