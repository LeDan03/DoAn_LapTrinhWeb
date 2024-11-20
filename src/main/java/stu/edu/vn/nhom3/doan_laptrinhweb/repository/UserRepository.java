package stu.edu.vn.nhom3.doan_laptrinhweb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import stu.edu.vn.nhom3.doan_laptrinhweb.model.User;

import java.util.Optional;

@Repository
@Transactional
public interface UserRepository extends JpaRepository<User, Integer>, CrudRepository<User, Integer> {
    User getUserByName(String name);

    @Modifying
    @Query("UPDATE User u set u.name=:newName, u.passwordHash=:passwordHash, u.email=:email where u.name=:oldName")
    void updateUserByName(@Param("oldName")String oldName, @Param("newName")String newName
                         ,@Param("passwordHash")String passwordHash, @Param("email")String email);

    Optional<User> findByEmail(String email);

    Optional<User> findByName(String name);
}
