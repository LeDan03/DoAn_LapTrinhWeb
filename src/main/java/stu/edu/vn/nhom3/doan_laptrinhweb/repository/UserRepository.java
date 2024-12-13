package stu.edu.vn.nhom3.doan_laptrinhweb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import stu.edu.vn.nhom3.doan_laptrinhweb.model.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    @Modifying
    @Query("UPDATE User u set  u.passwordHash=:newPassword, u.fullName=:newFullName where u.email=:email")
    void updateUserByEmail(@Param("email")String email,
                           @Param("newFullName") String newFullName,
                           @Param("newPassword") String newPassword);
    Optional<User> findByEmail(String email);
    User getUserByEmail(String email);
    User findUserById(int id);
}
