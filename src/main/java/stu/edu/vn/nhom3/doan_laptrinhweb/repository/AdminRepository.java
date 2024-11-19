package stu.edu.vn.nhom3.doan_laptrinhweb.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import stu.edu.vn.nhom3.doan_laptrinhweb.model.User;

public interface AdminRepository extends JpaRepository<User,Integer> {

    @Transactional
    @Modifying
    @Query("UPDATE User u set u.status=:status where u.name=:username" )
    void updateUserStatusByName(@Param("status") boolean status, @Param("username") String username);

    User findByName(String name);
}
