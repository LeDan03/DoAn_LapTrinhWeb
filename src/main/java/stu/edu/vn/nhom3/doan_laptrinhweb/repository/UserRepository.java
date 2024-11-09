package stu.edu.vn.nhom3.doan_laptrinhweb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import stu.edu.vn.nhom3.doan_laptrinhweb.dto.UserDTO;
import stu.edu.vn.nhom3.doan_laptrinhweb.model.User;

@Repository
@Transactional
public interface UserRepository extends JpaRepository<User, Integer> {
    User getUserByName(String name);
}
