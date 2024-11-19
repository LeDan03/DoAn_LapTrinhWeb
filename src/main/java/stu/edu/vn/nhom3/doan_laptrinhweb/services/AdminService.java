package stu.edu.vn.nhom3.doan_laptrinhweb.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import stu.edu.vn.nhom3.doan_laptrinhweb.dto.RegisterUserDTO;
import stu.edu.vn.nhom3.doan_laptrinhweb.repository.AdminRepository;

@Service
public class AdminService {
    @Autowired
    AdminRepository adminRepository;

    @Autowired
    UserService userService;
    public String disableUser(String username, RegisterUserDTO user)
    {
        if(adminRepository.findByName(username) != null)
        {
            adminRepository.updateUserStatusByName(false,username);
            return "UPDATE STATUS SUCCESSFUL";
        }

        return "UPDATE STATUS FAILED";
    }

}
