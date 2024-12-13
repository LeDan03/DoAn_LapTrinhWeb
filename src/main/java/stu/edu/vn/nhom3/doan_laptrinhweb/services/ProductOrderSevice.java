package stu.edu.vn.nhom3.doan_laptrinhweb.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import stu.edu.vn.nhom3.doan_laptrinhweb.model.ProductOrder;
import stu.edu.vn.nhom3.doan_laptrinhweb.model.ProductOrderId;
import stu.edu.vn.nhom3.doan_laptrinhweb.repository.ProductOrderRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductOrderSevice {

    @Autowired
    private ProductOrderRepository productOrderRepository;

     public List<ProductOrder> findAll() {
         return productOrderRepository.findAll();
     }
     public ProductOrder findById(ProductOrderId productOrderId) {
        return productOrderRepository.findById(productOrderId).orElse(null);
     }
     public ProductOrder save(ProductOrder productOrder) {
         return productOrderRepository.save(productOrder);
     }

     public void saveAll(List<ProductOrder> productOrders) {
         productOrderRepository.saveAll(productOrders);
     }

     public List<ProductOrder> getAllProductOrdersById(ProductOrderId productOrderId) {
         List<ProductOrder> productOrders = new ArrayList<>();
         for (ProductOrder productOrder : findAll()) {
             if(productOrder.getOrderId() == productOrderId.getOrderId() &&
             productOrder.getProductId() == productOrderId.getProductId()) {
                 productOrders.add(productOrder);
             }
         }
         return productOrders;
     }
}
