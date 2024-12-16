package stu.edu.vn.nhom3.doan_laptrinhweb.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import stu.edu.vn.nhom3.doan_laptrinhweb.model.Role;
import stu.edu.vn.nhom3.doan_laptrinhweb.repository.RoleRepository;

import java.util.List;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Transactional
    public void addDefaultRole(){
        List<Role> roles = roleRepository.findAll();
        if(roles.isEmpty()) {
            Role roleAdmin = new Role();
            roleAdmin.setName("ADMIN");
            roleRepository.save(roleAdmin);
            Role roleUser = new Role();
            roleUser.setName("USER");
            roleRepository.save(roleUser);
        }
    }
    public List<Role> getAllRole()
    {
        return roleRepository.findAll();
    }
}
