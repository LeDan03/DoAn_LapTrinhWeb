package stu.edu.vn.nhom3.doan_laptrinhweb.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "orders") //order trùng đối tượng của sql nên không khởi tạo bảng được
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String address;
    private String status;
    private Date createTime;
    private String phone;
    private double total;
    private String code;

    //@JsonManagedReference giúp khi serialize Order,
    // nó sẽ hiển thị các thuộc tính có liên quan (như danh sách các đơn hàng của người dùng).

    @OneToMany(mappedBy = "order",fetch = FetchType.EAGER)
    @JsonManagedReference("order-productOrder")
    private List<ProductOrder> productOrders;

    @ManyToOne
    @JsonBackReference("order-user")
    @JoinColumn(name = "customer_id")
    private User customer;

    @ManyToOne
    @JsonBackReference("order-payment")
    @JoinColumn(name = "payment_id")
    private Payment payment;
}
