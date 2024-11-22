package stu.edu.vn.nhom3.doan_laptrinhweb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import stu.edu.vn.nhom3.doan_laptrinhweb.dto.RegisterUserDTO;
import stu.edu.vn.nhom3.doan_laptrinhweb.model.User;
import stu.edu.vn.nhom3.doan_laptrinhweb.services.PasswordService;
import stu.edu.vn.nhom3.doan_laptrinhweb.services.UserService;

import java.util.Map;

@RestController
@RequestMapping(value = "/user")
public class UserController {
    @Autowired
    UserService userService;

    @Autowired
    PasswordService passwordService;

    @GetMapping("/dangnhap")
    public ResponseEntity<RegisterUserDTO> login(@RequestParam(value = "username") String username, @RequestParam(value = "password") String password)
    {
        RegisterUserDTO userDTO = userService.getUserDTOByName(username);
        if(userDTO!=null)
        {
            if(userDTO.isStatus()){
                if(passwordService.passwordEncoders().matches(password,userDTO.getPassword()))
                    return ResponseEntity.ok(userDTO);//tra ve 200 va userDTO
                else
                    return ResponseEntity.notFound().build();
            }
        }
        return ResponseEntity.badRequest().body(null);
    }

    @PostMapping("/dangky")
    public String registerProcess(@RequestParam Map<String,Object> params)
    {
        if(userService.isValidUserRegister((String)params.get("username"),(String)params.get("password")))
        {
            User user = userService.putInfoInUser(  (String)params.get("username")
                    ,(String) params.get("email")
                    ,(String)params.get("password")
                    ,2);
            userService.saveUser(user);
            return "Registered successfully";
        }
        return "Registration failed";
    }

//    @PutMapping
//    public ResponseEntity<String> updateUser(@RequestParam Map<String,Object> params)
//    {
//        RegisterUserDTO userDTO = userService.getUserDTOByName( (String)params.get("oldName"));
//        userDTO.setName((String)params.get("newName"));
//        userDTO.setPassword((String)params.get("newPassword"));
//        userDTO.setEmail((String)params.get("newEmail"));
//        return userService.updateUser((String) params.get("oldName"),userDTO);
//    }
}
