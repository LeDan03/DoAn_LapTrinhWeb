package stu.edu.vn.nhom3.doan_laptrinhweb.services;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import stu.edu.vn.nhom3.doan_laptrinhweb.dto.CategoryDTO;
import stu.edu.vn.nhom3.doan_laptrinhweb.dto.ProductDTO;
import stu.edu.vn.nhom3.doan_laptrinhweb.dto.RegisterUserDTO;
import stu.edu.vn.nhom3.doan_laptrinhweb.dto.UpdateUserDTO;
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

    @Autowired
    PasswordEncoder passwordEncoder;

    @Transactional
    public ResponseEntity<User> updateUser(int id, UpdateUserDTO userDTO)
    {
        User user = userRepository.findById(id).orElseThrow(()->new EntityNotFoundException("User not found"));
        if(userService.getUserByEmail(userDTO.getEmail())==null)
        {
            if(!userDTO.getEmail().isEmpty())
                user.setEmail(userDTO.getEmail());
            if(!userDTO.getNewFullName().isEmpty())
                user.setFullName(userDTO.getNewFullName());
            if(!userDTO.getNewPassword().isEmpty())
                user.setPasswordHash(passwordEncoder.encode(userDTO.getNewPassword()));
            user.setStatus(userDTO.isStatus());
            User result = userRepository.save(user);
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.badRequest().build();
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
    public User getUserById(int id) {
        return userRepository.findById(id).orElseThrow(()->new EntityNotFoundException("User not found"));
    }
}
