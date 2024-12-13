package stu.edu.vn.nhom3.doan_laptrinhweb.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ProductOrderDTO {

    private int product_id;
    private long order_id;
    private int quantity;
}
