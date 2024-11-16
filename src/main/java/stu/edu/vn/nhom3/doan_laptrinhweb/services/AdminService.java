package stu.edu.vn.nhom3.doan_laptrinhweb.services;

import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import stu.edu.vn.nhom3.doan_laptrinhweb.dto.UserDTO;
import stu.edu.vn.nhom3.doan_laptrinhweb.model.User;
import stu.edu.vn.nhom3.doan_laptrinhweb.repository.AdminRepository;

@Service
public class AdminService {
    @Autowired
    AdminRepository adminRepository;

    @Autowired
    UserService userService;
    public String disableUser(String username,UserDTO user)
    {
        if(adminRepository.findByName(username) != null)
        {
            adminRepository.updateUserStatusByName(false,username);
            return "UPDATE STATUS SUCCESSFUL";
        }

        return "UPDATE STATUS FAILED";
    }

}
