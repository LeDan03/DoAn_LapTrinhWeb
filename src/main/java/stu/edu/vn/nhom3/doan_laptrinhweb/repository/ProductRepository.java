package stu.edu.vn.nhom3.doan_laptrinhweb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import stu.edu.vn.nhom3.doan_laptrinhweb.model.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {

//    Product findByName(String name);
}
