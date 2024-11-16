package stu.edu.vn.nhom3.doan_laptrinhweb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import stu.edu.vn.nhom3.doan_laptrinhweb.dto.UserDTO;
import stu.edu.vn.nhom3.doan_laptrinhweb.model.User;
import stu.edu.vn.nhom3.doan_laptrinhweb.services.AdminService;
import stu.edu.vn.nhom3.doan_laptrinhweb.services.UserService;

@RestController
@RequestMapping(value = "/admin")
public class AdminController {
    @Autowired
    AdminService adminService;

    @Autowired
    UserService userService;

    @GetMapping(value = "/deleteuser")
    public ResponseEntity<String> disableUser(String username) {
        return ResponseEntity.ok( adminService.disableUser(username,userService.getUserDTOByName(username)));
    }
}
