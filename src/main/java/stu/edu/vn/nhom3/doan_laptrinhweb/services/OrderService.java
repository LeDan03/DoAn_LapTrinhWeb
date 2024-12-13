package stu.edu.vn.nhom3.doan_laptrinhweb.services;


import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import stu.edu.vn.nhom3.doan_laptrinhweb.dto.OrderDTO;
import stu.edu.vn.nhom3.doan_laptrinhweb.model.Order;
import stu.edu.vn.nhom3.doan_laptrinhweb.model.Payment;
import stu.edu.vn.nhom3.doan_laptrinhweb.model.User;
import stu.edu.vn.nhom3.doan_laptrinhweb.repository.OrderRepository;
import stu.edu.vn.nhom3.doan_laptrinhweb.repository.PaymentRepository;
import stu.edu.vn.nhom3.doan_laptrinhweb.repository.UserRepository;


import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private UserRepository userRepository;

    @PersistenceContext
    private EntityManager entityManager;

    public List<Order> findAll() {
        return orderRepository.findAll();
    }
    public Order findById(long id) {
        return orderRepository.findById(id);
    }
    public Order save(Order order) {
        return orderRepository.save(order);
    }

    @Transactional
    public Order createOrder(OrderDTO orderDTO) {
        Order order = null;

        Payment payment = paymentRepository.findById(orderDTO.getPaymentId()).orElse(null);
        if(payment == null)
            return null;
        User user = userRepository.findById(orderDTO.getCustomerId()).orElse(null);
        if(user!=null) {
            order = new Order();
            order.setAddress(orderDTO.getAddress());
            order.setPhone(orderDTO.getPhone());
            order.setCreateTime(orderDTO.getCreateTime());
            order.setStatus(orderDTO.getStatus());
            order.setTotal(orderDTO.getTotal());
            order.setCode(orderDTO.getCode());
            order.setPayment(payment);

            order.setCustomer(user);

            order.setProductOrders(orderDTO.getProductOrders());
        }
        return order;
    }

    public List<Order> getAllUserOrders(int customerId) {
        List<Order> orders = null;
        User customer = userService.findUserById(customerId);
        if(customer!=null) {
            orders = new ArrayList<>();
            for (Order order :findAll()) {
                if(order.getCustomer().getId()==(customer.getId()))
                    orders.add(order);
            }
        }
        return orders;
    }
}
