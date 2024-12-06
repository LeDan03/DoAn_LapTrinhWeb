package stu.edu.vn.nhom3.doan_laptrinhweb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import stu.edu.vn.nhom3.doan_laptrinhweb.model.Role;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Role findByName(String name);
}
