package stu.edu.vn.nhom3.doan_laptrinhweb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import stu.edu.vn.nhom3.doan_laptrinhweb.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User getUserByName(String name);
    User getUserById(int id);
    User getUserByEmail(String email);
}
