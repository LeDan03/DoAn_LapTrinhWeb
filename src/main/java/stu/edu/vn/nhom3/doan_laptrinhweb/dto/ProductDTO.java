package stu.edu.vn.nhom3.doan_laptrinhweb.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ProductDTO {

    private String name;
    private double price;
    private String unit;
    private String theme;
    private String description;
    private int category_id;
}
