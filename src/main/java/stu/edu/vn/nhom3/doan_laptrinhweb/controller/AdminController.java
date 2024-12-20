package stu.edu.vn.nhom3.doan_laptrinhweb.controller;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import stu.edu.vn.nhom3.doan_laptrinhweb.dto.CategoryDTO;
import stu.edu.vn.nhom3.doan_laptrinhweb.dto.ProductDTO;
import stu.edu.vn.nhom3.doan_laptrinhweb.dto.RegisterUserDTO;
import stu.edu.vn.nhom3.doan_laptrinhweb.model.*;

import stu.edu.vn.nhom3.doan_laptrinhweb.repository.UserRepository;
import stu.edu.vn.nhom3.doan_laptrinhweb.response.DetailOrderResponse;
import stu.edu.vn.nhom3.doan_laptrinhweb.response.Response;
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
    private UserRepository userRepository;

    @Autowired
    private OrderService orderService;

    @Autowired
    private PaymentService paymentService;

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
                .role_id(user.getRole().getId())
                .build();
        return ResponseEntity.ok(registerUserDTO);
    }

    @PutMapping(value = "/disableOrAble/{user_id}")
    public ResponseEntity<String> disableUser(@PathVariable("user_id") int id, @RequestParam("status") boolean status)
    {
        User user = adminService.getUserById(id);
        if(user!=null)
        {
            user.setStatus(status);
            userRepository.save(user);
            if(!status)
                return ResponseEntity.ok("User is disabled");
            return ResponseEntity.ok("User is active");
        }
        return ResponseEntity.ok("User is not found");
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
    public ResponseEntity<String> updateCategory(@PathVariable("id") int id,@RequestBody CategoryDTO categoryDTO) {
        Response result = categoryService.updateCategory(id, categoryDTO);
        if (result.isSuccess()){
            return ResponseEntity.ok(result.getMessage());
        }else{
            return ResponseEntity.badRequest().body(result.getMessage());
        }
    }

    @DeleteMapping(value = "/deleteCategory/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable("id") int id) {
        Logger logger = Logger.getLogger(this.getClass().getName());
        logger.info("Delete Category");
        Response result = categoryService.deleteCategory(id);
        if(result.isSuccess())
            return ResponseEntity.ok(result.getMessage());
        else
            return ResponseEntity.badRequest().body(result.getMessage());
    }

    @GetMapping(value = "/getAllCategory")
    public ResponseEntity<List<CategoryDTO>> getAllCategory()
    {
        return ResponseEntity.ok().body(adminService.getAllCategory());
    }

    @PostMapping(value = "/addProduct")
    public ResponseEntity<Product> addProduct(
            @ModelAttribute ProductDTO productDTO,
            @RequestParam(value = "images", required = false) MultipartFile[] images) {
        if (!productService.isExistedProduct(productDTO.getName())) {
            Category category = categoryService.findById(productDTO.getCategory_id());
            Product product = new Product();
            product.setName(productDTO.getName());
            product.setDescription(productDTO.getDescription());
            product.setPrice(productDTO.getPrice());
            product.setCategory(category);
            product.setTheme(productDTO.getTheme());
            product.setUnit(productDTO.getUnit());

            try {
                Product savedProduct = productService.addProduct(product);
                if (images != null && images.length > 0) {
                    for (MultipartFile image : images) {
                        String fileUrl = cloudinaryService.uploadFile(image);
                        imageService.addNewImage(savedProduct.getId(), fileUrl);
                    }
                }
                return ResponseEntity.ok(savedProduct);
            } catch (Exception e) {
                return ResponseEntity.status(501).build();
            }
        }
        return ResponseEntity.badRequest().body(null);
    }


    @DeleteMapping(value = "/deleteProduct/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable("id") int id) {
        List<Image> images = imageService.getImagesByProductId(id);
        if(!images.isEmpty())
            for(Image image : images)
                cloudinaryService.deleteFile(image.getUrl());

        Response result = productService.deleteProduct(id);
            imageService.deleteAllImageFromProduct(id);
        if(result.isSuccess())
            return ResponseEntity.ok(result.getMessage());
        else
            return ResponseEntity.badRequest().body(result.getMessage());
    }

    @PutMapping(value = "/updateProduct/{id}")
    public Response updateProduct(@PathVariable("id") int id, @ModelAttribute ProductDTO productDTO
            , @RequestParam("images") List<MultipartFile> images) {
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
        return productService.updateProduct(id, productDTO);
    }

    @GetMapping(value = "/getAllProduct")
    public ResponseEntity<List<ProductDTO>> getAllProduct()
    {
        return ResponseEntity.ok().body(adminService.getAllProduct());
    }

    //code chưa chuẩn
    @GetMapping("/findProduct/{product_id}")
    public ResponseEntity<ProductDTO> findProductById(@PathVariable("product_id") int id) {

        Product product = productService.findById(id);
        if(product!=null)
        {
            List<Image> product_images = imageService.getImagesByProductId(id);
            Category category = categoryService.findById(product.getCategory().getId());

            ProductDTO productDTO = new ProductDTO();
            productDTO.setName(product.getName());
            productDTO.setDescription(product.getDescription());
            productDTO.setPrice(product.getPrice());
            productDTO.setUnit(product.getUnit());
            productDTO.setTheme(product.getTheme());
            productDTO.setId(product.getId());
            productDTO.setCategory_id(product.getCategory().getId());
            productDTO.setCategory_name(category.getName());
            productDTO.setListImage(imageService.getProductImagesLink(product_images));
            return ResponseEntity.ok(productDTO);
        }

        return ResponseEntity.badRequest().body(null);
    }

    @PostMapping(value = "/addNewPayment")
    public ResponseEntity<String> addNewPayment(@RequestParam("payment_name") String paymentName )
    {
        if(paymentService.isExistedPayment(paymentName))
            return ResponseEntity.badRequest().body("Payment existed!");
        Payment payment = new Payment();
        payment.setName(paymentName);
        paymentService.save(payment);
        return ResponseEntity.ok().body("Adding new payment " + paymentName+ " successfully");
    }

    @PutMapping(value = "/updateOrderStatus/{order_id}")
    public ResponseEntity<String> updateOrderStatus(@PathVariable("order_id") int id, @RequestParam("status") String status)
    {
        Order order = orderService.findById(id);
        String oldStatus = order.getStatus();
        order.setStatus(status);
        orderService.save(order);
        return ResponseEntity.ok().body("Update status successfully, new status: " + order.getStatus()+", old status: " + oldStatus);
    }

    @GetMapping(value = "/getAllOrders")
    public ResponseEntity<List<Order>> getAllOrderṣ̣̣̣()
    {
        List<Order> orders = orderService.findAll();
        return ResponseEntity.ok().body(orders);
    }

    @GetMapping(value = "/getDetailOrder/{order_id}")
    public ResponseEntity<DetailOrderResponse> getDetailOrder(@PathVariable("order_id") long order_id)
    {
        return ResponseEntity.ok().body(adminService.getDetailOrder(order_id));
    }
}
