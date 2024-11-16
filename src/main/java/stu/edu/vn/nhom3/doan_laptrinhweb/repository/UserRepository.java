package stu.edu.vn.nhom3.doan_laptrinhweb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import stu.edu.vn.nhom3.doan_laptrinhweb.dto.UserDTO;
import stu.edu.vn.nhom3.doan_laptrinhweb.model.User;

@Repository
@Transactional
public interface UserRepository extends JpaRepository<User, Integer> {
    User getUserByName(String name);

    @Transactional
    @Modifying
    @Query("UPDATE User u set u.name=:newName, u.passwordHash=:passwordHash, u.email=:email where u.name=:oldName")
    void updateUserByName(@Param("oldName")String oldName, @Param("newName")String newName
                         ,@Param("passwordHash")String passwordHash, @Param("email")String email);
}
