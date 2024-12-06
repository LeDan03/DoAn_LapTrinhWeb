package stu.edu.vn.nhom3.doan_laptrinhweb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import stu.edu.vn.nhom3.doan_laptrinhweb.model.Category;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
//    void deleteCategoryById(int id);
//    void updateCategoryById(Category category);
}
