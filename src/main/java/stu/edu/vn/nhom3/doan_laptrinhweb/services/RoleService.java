package stu.edu.vn.nhom3.doan_laptrinhweb.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import stu.edu.vn.nhom3.doan_laptrinhweb.model.Role;
import stu.edu.vn.nhom3.doan_laptrinhweb.repository.RoleRepository;

import java.util.List;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    public void addDefaultRole(){
        List<Role> roles = roleRepository.findAll();
        if(roles.size()==0) {
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
    public int getAdminRole()
    {
        Role roleAdmin = roleRepository.findByName("admin");
        return roleAdmin.getId();
    }
}
