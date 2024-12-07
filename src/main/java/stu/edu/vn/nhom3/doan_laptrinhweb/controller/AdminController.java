package stu.edu.vn.nhom3.doan_laptrinhweb.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import stu.edu.vn.nhom3.doan_laptrinhweb.configs.JwtAuthenticationFilter;
import stu.edu.vn.nhom3.doan_laptrinhweb.dto.CategoryDTO;
import stu.edu.vn.nhom3.doan_laptrinhweb.dto.ProductDTO;
import stu.edu.vn.nhom3.doan_laptrinhweb.dto.RegisterUserDTO;
import stu.edu.vn.nhom3.doan_laptrinhweb.model.Category;
import stu.edu.vn.nhom3.doan_laptrinhweb.model.Image;
import stu.edu.vn.nhom3.doan_laptrinhweb.model.Product;
import stu.edu.vn.nhom3.doan_laptrinhweb.model.User;
import stu.edu.vn.nhom3.doan_laptrinhweb.repository.UserRepository;
import stu.edu.vn.nhom3.doan_laptrinhweb.services.*;

import java.util.List;
import java.util.Map;
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

    @GetMapping(value = "getUserById/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") int id)
    {
        return ResponseEntity.ok(adminService.getUserById(id));
    }

    @PutMapping(value = "/disableUser")
    public ResponseEntity<User> disableUser(@Param("email") String email) {
        return adminService.disableUser(email);
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
    public ResponseEntity<Product> addProduct(@RequestBody ProductDTO productDTO, @RequestParam("image") MultipartFile image) {
        Logger logger = Logger.getLogger(this.getClass().getName());
        logger.info("Add Product");
        Product product = new Product();
        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());
        product.setTheme(productDTO.getTheme());
        product.setUnit(productDTO.getUnit());
        product.setCate_id(productDTO.getCategory_id());
        logger.info("Trước khi add product");
        Product result = productService.addProduct(product);
        if (image == null || image.isEmpty()) {
            logger.info("File upload is missing or empty");
            return ResponseEntity.badRequest().build();
        }
        try{
            logger.info("Đã vào try");
            String fileUrl = cloudinaryService.uploadFile(image);
            imageService.addNewImage(result.getId(), fileUrl);
            logger.info("Đã lưu image");
            return ResponseEntity.ok(result);
        }catch (Exception e){
            logger.info("LỖI, nhảy vào catch");
            return ResponseEntity.badRequest().build();
        }

//        try {
//            for (MultipartFile file : files) {
//                logger.info("Đã vào Try");
//                String fileUrl = cloudinaryService.uploadFile(file);
//                imageService.addNewImage(result.getId(), fileUrl);
//                logger.info("Đã lưu image");
//            }
//            return ResponseEntity.ok(result);
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().build();
//        }

    }
    @DeleteMapping(value = "/deleteProduct/{id}")
    public void deleteProduct(@PathVariable("id") int id) {
        productService.deleteProduct(id);
        ResponseEntity.ok().build();
    }

    @PutMapping(value = "/updateProduct/{id}")
    public void updateProduct(@PathVariable("id") int id, @RequestBody ProductDTO productDTO) {
        productService.updateProduct(id, productDTO);
        ResponseEntity.ok().build();
    }

    @GetMapping(value = "/getAllProduct")
    public ResponseEntity<List<ProductDTO>> getAllProduct()
    {
        return ResponseEntity.ok().body(adminService.getAllProduct());
    }

    @PostMapping(value = "/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            String fileUrl = cloudinaryService.uploadFile(file);
            return ResponseEntity.ok(fileUrl);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Upload failed: " + e.getMessage());
        }
    }
}
