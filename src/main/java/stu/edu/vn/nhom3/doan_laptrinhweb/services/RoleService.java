package stu.edu.vn.nhom3.doan_laptrinhweb.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import stu.edu.vn.nhom3.doan_laptrinhweb.model.Role;
import stu.edu.vn.nhom3.doan_laptrinhweb.repository.RoleRepository;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    public void addDefaultRole(){
        Role roleAdmin = new Role();
        roleAdmin.setName("admin");
        roleRepository.save(roleAdmin);
        Role roleUser = new Role();
        roleUser.setName("user");
        roleRepository.save(roleUser);
    }
}
