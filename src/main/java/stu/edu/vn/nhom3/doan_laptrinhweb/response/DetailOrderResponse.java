package stu.edu.vn.nhom3.doan_laptrinhweb.response;

import lombok.*;
import stu.edu.vn.nhom3.doan_laptrinhweb.model.ProductOrder;

import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class DetailOrderResponse {

    private long order_id;
    private String customer_name;
    private Date createdTime;
    private String address;
    private String payment_method;
    private String status;

    private List<ProductOrderResponse> productOrders;

    private double total;
}
