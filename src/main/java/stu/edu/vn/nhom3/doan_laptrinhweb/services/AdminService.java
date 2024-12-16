package stu.edu.vn.nhom3.doan_laptrinhweb.services;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;
import stu.edu.vn.nhom3.doan_laptrinhweb.dto.CategoryDTO;
import stu.edu.vn.nhom3.doan_laptrinhweb.dto.ProductDTO;
import stu.edu.vn.nhom3.doan_laptrinhweb.dto.RegisterUserDTO;
import stu.edu.vn.nhom3.doan_laptrinhweb.model.*;
import stu.edu.vn.nhom3.doan_laptrinhweb.repository.*;
import stu.edu.vn.nhom3.doan_laptrinhweb.response.DetailOrderResponse;
import stu.edu.vn.nhom3.doan_laptrinhweb.response.ProductOrderResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class AdminService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ImageRepository imageRepository;

    @Autowired
    OrderService orderService;

    public List<RegisterUserDTO> getAllUser(){
        List<User> users=new ArrayList<>();
        users=userRepository.findAll();
        List<RegisterUserDTO> userDTOs=new ArrayList<>();
        userDTOs=users.stream().map(user -> {
            return RegisterUserDTO.builder()
                    .id(user.getId())
                    .email(user.getEmail())
                    .fullName(user.getFullName())
                    .role_id(user.getRole().getId())
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
                    .id(category.getId())
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
            List<String> images = new ArrayList<>();
            imageRepository.findAll().forEach(image -> {
                if (image.getProduct_id() == product.getId()){
                    images.add(image.getUrl());
                }
            });
            return ProductDTO.builder()
                    .id(product.getId())
                    .name(product.getName())
                    .price(product.getPrice())
                    .theme(product.getTheme())
                    .unit(product.getUnit())
                    .category_id(product.getCate_id())
                    .category_name(product.getCategory().getName())
                    .description(product.getDescription())
                    .listImage(images)
                    .build();
        }).collect(Collectors.toList());
        return productDTOS;
    }

    public User getUserById(int id) {
        return userRepository.findById(id).orElseThrow(()->new EntityNotFoundException("User not found"));
    }

    public boolean updateOrderStatus(String status, long id) {
        Order order = orderService.findById(id);
        if(order != null) {
            order.setStatus(status);
            orderService.save(order);
            return true;
        }
        return false;
    }

    public DetailOrderResponse getDetailOrder(long id) {
        Order order = orderService.findById(id);
        List<ProductOrder> productOrderList = order.getProductOrders();
        User user = order.getCustomer();
        Payment payment = order.getPayment();
        List<ProductOrderResponse> productOrderResponses = new ArrayList<>();

        int stt = 1;

        for(ProductOrder productOrder : productOrderList) {
            ProductOrderResponse productOrderResponse =  new ProductOrderResponse();
            productOrderResponse = ProductOrderResponse.builder()
                    .product_name(productOrder.getProduct().getName())
                    .stt(stt)
                    .quantity(productOrder.getQuantity())
                    .product_price(productOrder.getProduct().getPrice())
                    .total_price(productOrder.getProduct().getPrice()*productOrder.getQuantity())
                    .build();
            stt++;
            productOrderResponses.add(productOrderResponse);
        }

        return putInfoInDetailOrderResponse(user,payment,order,productOrderResponses);
    }

    public DetailOrderResponse putInfoInDetailOrderResponse(User user, Payment payment, Order order, List<ProductOrderResponse> productOrderResponses) {
        DetailOrderResponse detailOrderResponse = new DetailOrderResponse();

        detailOrderResponse.setOrder_id(order.getId());
        detailOrderResponse.setCustomer_name(user.getFullName());
        detailOrderResponse.setPayment_method(payment.getName());
        detailOrderResponse.setCreatedTime(order.getCreateTime());
        detailOrderResponse.setAddress(order.getAddress());
        detailOrderResponse.setStatus(order.getStatus());
        detailOrderResponse.setProductOrders(productOrderResponses);
        detailOrderResponse.setTotal(order.getTotal());

        return detailOrderResponse;
    }
}
