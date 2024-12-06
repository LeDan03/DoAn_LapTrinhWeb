package stu.edu.vn.nhom3.doan_laptrinhweb.services;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import stu.edu.vn.nhom3.doan_laptrinhweb.dto.CategoryDTO;
import stu.edu.vn.nhom3.doan_laptrinhweb.model.Category;
import stu.edu.vn.nhom3.doan_laptrinhweb.model.Product;
import stu.edu.vn.nhom3.doan_laptrinhweb.repository.CategoryRepository;

import java.util.List;
import java.util.logging.Logger;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductService productService;

    public List<Category> findAll() {
        return categoryRepository.findAll();
    }
    public boolean isExistedCategory(String cateName) {
        if(!findAll().isEmpty()){
            for(Category c : findAll().stream().toList()) {
                if(c.getName().equals(cateName))
                    return true;
            }
        }
        return false;
    }
    public boolean addCategory(Category category) {
        if(!isExistedCategory(category.getName())) {
            categoryRepository.save(category);
            return true;
        }
        return false;
    }

    public boolean deleteCategory(int id) {
        Category category = categoryRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("Category not found"));
        if(isEmptyCategory(id)) {
            categoryRepository.delete(category);
            return true;
        }
        else
            return false;
    }

    public void updateCategory(int id, CategoryDTO updatedCategory) {
        Category category = categoryRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Category not found with given id : " + id)
        );

        category.setName(updatedCategory.getName());
        category.setCode(updatedCategory.getCode());
        Category updatedCategoryObj = categoryRepository.save(category);
        ResponseEntity.ok(updatedCategoryObj);
    }
    public boolean isEmptyCategory(int id)
    {
        for(Product p : productService.findAll())
        {
            if(p.getId() == id)
                return false;
        }
        return true;
    }
}
