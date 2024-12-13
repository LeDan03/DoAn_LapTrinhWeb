package stu.edu.vn.nhom3.doan_laptrinhweb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import stu.edu.vn.nhom3.doan_laptrinhweb.model.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    Order findById(long id);
}
