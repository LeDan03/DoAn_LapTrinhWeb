package stu.edu.vn.nhom3.doan_laptrinhweb.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;
import stu.edu.vn.nhom3.doan_laptrinhweb.dto.CategoryDTO;
import stu.edu.vn.nhom3.doan_laptrinhweb.dto.ProductDTO;
import stu.edu.vn.nhom3.doan_laptrinhweb.dto.RegisterUserDTO;
import stu.edu.vn.nhom3.doan_laptrinhweb.model.Category;
import stu.edu.vn.nhom3.doan_laptrinhweb.model.Product;
import stu.edu.vn.nhom3.doan_laptrinhweb.model.User;
import stu.edu.vn.nhom3.doan_laptrinhweb.repository.AdminRepository;
import stu.edu.vn.nhom3.doan_laptrinhweb.repository.CategoryRepository;
import stu.edu.vn.nhom3.doan_laptrinhweb.repository.ProductRepository;
import stu.edu.vn.nhom3.doan_laptrinhweb.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin("*")
@Service
public class AdminService {
    @Autowired
    AdminRepository adminRepository;

    @Autowired
    UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    ProductRepository productRepository;

    public ResponseEntity<User> disableUser(String email)
    {

        User user = userService.getUserByEmail(email);
        if(user != null)
        {
            adminRepository.updateUserStatusByEmail(false,email);
            user.setStatus(false);
            return ResponseEntity.ok(user);
        }
        else
            return ResponseEntity.notFound().build();
    }
    public List<RegisterUserDTO> getAllUser(){
        List<User> users=new ArrayList<>();
        users=userRepository.findAll();
        List<RegisterUserDTO> userDTOs=new ArrayList<>();
        userDTOs=users.stream().map(user -> {
            return RegisterUserDTO.builder()
                    .id(user.getId())
                    .email(user.getEmail())
                    .fullName(user.getFullName())
                    .role_id(user.getRole_id())
                    .status(user.isStatus())
                    .build();
        }).collect(Collectors.toList());
        return userDTOs;
    }

    public List<CategoryDTO> getAllCategory(){
        List<Category> categories=new ArrayList<>();
        categories=categoryRepository.findAll();
        List<CategoryDTO> categoryDTOS=new ArrayList<>();
        categoryDTOS=categories.stream().map(category -> {
            return CategoryDTO.builder()
                    .name(category.getName())
                    .code(category.getCode())
                    .build();
        }).collect(Collectors.toList());
        return categoryDTOS;
    }
    public List<ProductDTO> getAllProduct(){
        List<Product> products=new ArrayList<>();
        products=productRepository.findAll();
        List<ProductDTO> productDTOS=new ArrayList<>();
        productDTOS=products.stream().map(product -> {
            return ProductDTO.builder()
                    .name(product.getName())
                    .price(product.getPrice())
                    .theme(product.getTheme())
                    .unit(product.getUnit())
                    .category_id(product.getCate_id())
                    .description(product.getDescription())
                    .build();
        }).collect(Collectors.toList());
        return productDTOS;
    }
}
