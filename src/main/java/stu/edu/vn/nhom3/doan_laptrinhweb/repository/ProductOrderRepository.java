package stu.edu.vn.nhom3.doan_laptrinhweb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import stu.edu.vn.nhom3.doan_laptrinhweb.model.ProductOrder;
import stu.edu.vn.nhom3.doan_laptrinhweb.model.ProductOrderId;

@Repository
public interface ProductOrderRepository extends JpaRepository<ProductOrder, ProductOrderId> {

}
