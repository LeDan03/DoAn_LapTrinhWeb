package stu.edu.vn.nhom3.doan_laptrinhweb.services;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import stu.edu.vn.nhom3.doan_laptrinhweb.dto.CategoryDTO;
import stu.edu.vn.nhom3.doan_laptrinhweb.model.Category;
import stu.edu.vn.nhom3.doan_laptrinhweb.model.Product;
import stu.edu.vn.nhom3.doan_laptrinhweb.repository.CategoryRepository;
import stu.edu.vn.nhom3.doan_laptrinhweb.repository.ProductRepository;
import stu.edu.vn.nhom3.doan_laptrinhweb.response.Response;

import java.util.List;
import java.util.logging.Logger;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductService productService;
    @Autowired
    private ProductRepository productRepository;

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

    public Category findById(int id) {
        return categoryRepository.findById(id).get();
    }

    public boolean addCategory(Category category) {
        if(!isExistedCategory(category.getName())) {
            categoryRepository.save(category);
            return true;
        }
        return false;
    }

    public Response deleteCategory(int id) {
        try {
            Category category = categoryRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Category not found"));
                if (category.getId() > 0){
                    boolean check = isEmptyCategory(id);
                    if (check){
                        return new Response(false, "Category có tồn tại sản phẩm!!!");
                    }else{
                        categoryRepository.delete(category);
                        return new Response(true, "Xóa thành công");
                    }
                }else{
                    return new Response(false, "Category found: " + category.getName());
                }
        } catch (EntityNotFoundException e) {
            return new Response(false, e.getMessage());
        }
    }

    public Response updateCategory(int id, CategoryDTO updatedCategory) {
        try {
            Category category = categoryRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Category not found"));
            if (category.getId() > 0){
                category.setName(updatedCategory.getName());
                category.setCode(updatedCategory.getCode());
                Category updatedCategoryObj = categoryRepository.save(category);
                return  new Response(true, "Update thành công");
            }else{
                return new Response(false, "Category found: " + category.getName());
            }
        } catch (EntityNotFoundException e) {
            return new Response(false, e.getMessage());
        }
    }

    public boolean isEmptyCategory(int id)
    {
        for(Product p : productRepository.findAll())
        {
            if(p.getCategory().getId() == id) return true;
        }
        return false;
    }
}
