package stu.edu.vn.nhom3.doan_laptrinhweb.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import stu.edu.vn.nhom3.doan_laptrinhweb.model.User;

public interface AdminRepository extends JpaRepository<User,Integer> {

}
