package stu.edu.vn.nhom3.doan_laptrinhweb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import stu.edu.vn.nhom3.doan_laptrinhweb.model.CartProduct;

import java.util.Optional;

public interface CartProductRepository extends JpaRepository<CartProduct, Integer> {
    Optional<CartProduct> findByCartIdAndProductId(Integer cartId, Integer productId);
}
