package stu.edu.vn.nhom3.doan_laptrinhweb.response;


import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ProductOrderResponse {

    private int stt;
    private String product_name;
    private  int quantity;
    private double product_price;
    private double total_price;
}
