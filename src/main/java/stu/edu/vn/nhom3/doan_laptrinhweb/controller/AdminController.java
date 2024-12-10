package stu.edu.vn.nhom3.doan_laptrinhweb.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import stu.edu.vn.nhom3.doan_laptrinhweb.dto.CategoryDTO;
import stu.edu.vn.nhom3.doan_laptrinhweb.dto.ProductDTO;
import stu.edu.vn.nhom3.doan_laptrinhweb.dto.RegisterUserDTO;
import stu.edu.vn.nhom3.doan_laptrinhweb.dto.UpdateUserDTO;
import stu.edu.vn.nhom3.doan_laptrinhweb.model.Category;

import stu.edu.vn.nhom3.doan_laptrinhweb.model.Image;
import stu.edu.vn.nhom3.doan_laptrinhweb.model.Product;
import stu.edu.vn.nhom3.doan_laptrinhweb.model.User;
import stu.edu.vn.nhom3.doan_laptrinhweb.repository.UserRepository;
import stu.edu.vn.nhom3.doan_laptrinhweb.services.*;


import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@CrossOrigin("*")

@Slf4j
@RestController
@RequestMapping(value = "/admin")
public class AdminController {
    @Autowired
    AdminService adminService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductService productService;

    @Autowired
    CloudinaryService cloudinaryService;

    @Autowired
    ImageService imageService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping(value = "getUserById/{id}")
    public ResponseEntity<RegisterUserDTO> getUserById(@PathVariable("id") int id)
    {
        User user = adminService.getUserById(id);
        RegisterUserDTO registerUserDTO = new RegisterUserDTO();
        registerUserDTO = RegisterUserDTO.builder()
                .id(user.getId())
                .fullName(user.getFullName())
                .status(user.isStatus())
                .email(user.getEmail())
                .role_id(user.getRole_id())
                .build();
        return ResponseEntity.ok(registerUserDTO);
    }

    @PutMapping(value = "/updateUser/{id}")
    public ResponseEntity<User> updateUser(@PathVariable("id") int id, @RequestBody UpdateUserDTO userDTO, HttpServletRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User authenticatedUser = (User) authentication.getPrincipal();
        String token = request.getHeader("Authorization");
        if(token!=null && token.startsWith("Bearer "))
        {
            token = token.substring(7);
            try{
                String emailFromToken = jwtService.extractUsername(token);
                if(emailFromToken.equals(authenticatedUser.getEmail()) && emailFromToken.equals(userRepository.findById(id).get().getEmail()))
                    return adminService.updateUser(id, userDTO);
                else
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
            }catch (Exception e)
            {
                System.out.println("Error extracting username from token: " + e.getMessage());
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
    }

    @GetMapping(value = "/getAllUser")
    public ResponseEntity<List<RegisterUserDTO>> getAllUser() {
        return ResponseEntity.ok(adminService.getAllUser());
    }

    @PostMapping(value = "/addCategory")
    public ResponseEntity<Category> addNewCategory(@RequestBody CategoryDTO categoryDTO) {
        Category category = new Category();
        category.setName(categoryDTO.getName());
        category.setCode(categoryDTO.getCode());
        boolean result = categoryService.addCategory(category);
        if (result)
            return ResponseEntity.ok(category);
        else
            return ResponseEntity.badRequest().build();
    }

    @PutMapping(value = "/updateCategory/{id}")
    public ResponseEntity<Category> updateCategory(@PathVariable("id") int id,@RequestBody CategoryDTO categoryDTO) {
        categoryService.updateCategory(id, categoryDTO);
        return ResponseEntity.ok().body(null);
    }

    @DeleteMapping(value = "/deleteCategory/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable("id") int id) {
        Logger logger = Logger.getLogger(this.getClass().getName());
        logger.info("Delete Category");
        boolean result = categoryService.deleteCategory(id);
        if(result)
            return ResponseEntity.ok("Da xoa Category co id: "+id);
        else
            return ResponseEntity.badRequest().body("Không xóa được Category có id: "+id);
    }

    @GetMapping(value = "/getAllCategory")
    public ResponseEntity<List<CategoryDTO>> getAllCategory()
    {
        return ResponseEntity.ok().body(adminService.getAllCategory());
    }

    @PostMapping(value = "/addProduct")
    public ResponseEntity<Product> addProduct(@ModelAttribute ProductDTO productDTO, @RequestParam("images") List<MultipartFile> images) {
        if(!productService.isExistedProduct(productDTO.getName()))
        {
            Product product = new Product();
            product.setName(productDTO.getName());
            product.setDescription(productDTO.getDescription());
            product.setPrice(productDTO.getPrice());
            product.setCate_id(productDTO.getCategory_id());
            product.setTheme(productDTO.getTheme());
            product.setUnit(productDTO.getUnit());
            try {
                Product product1 = productService.addProduct(product);
                if(images != null && !images.isEmpty()){
                    for (MultipartFile image: images)
                    {
                        String fileUrl = cloudinaryService.uploadFile(image);
                        imageService.addNewImage(product1.getId(), fileUrl);
                    }
                }
                return ResponseEntity.ok(product1);
            } catch (Exception e) {
                return ResponseEntity.status(501).build();//Yêu cầu không thể được máy chủ thực hiện
            }
        }
        return ResponseEntity.badRequest().body(null);
    }
    @DeleteMapping(value = "/deleteProduct/{id}")
    public void deleteProduct(@PathVariable("id") int id) {
        List<Image> images = imageService.getImagesByProductId(id);
        if(!images.isEmpty())
            for(Image image : images)
                cloudinaryService.deleteFile(image.getUrl());

        productService.deleteProduct(id);
        imageService.deleteAllImageFromProduct(id);

        ResponseEntity.ok().build();
    }

    @PutMapping(value = "/updateProduct/{id}")
    public void updateProduct(@PathVariable("id") int id, @ModelAttribute ProductDTO productDTO
            , @RequestParam("images") List<MultipartFile> images) {
        productService.updateProduct(id, productDTO);
        if(!images.isEmpty())
        {
            List<String> urls = new ArrayList<>();
            for(MultipartFile image : images)
            {
                String fileUrl = cloudinaryService.uploadFile(image);
                urls.add(fileUrl);
            }
            imageService.updateImages(id, urls);
        }
        ResponseEntity.ok().build();
    }

    @GetMapping(value = "/getAllProduct")
    public ResponseEntity<List<ProductDTO>> getAllProduct()
    {
        return ResponseEntity.ok().body(adminService.getAllProduct());
    }

}
