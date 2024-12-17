package stu.edu.vn.nhom3.doan_laptrinhweb.response;

import lombok.*;
import stu.edu.vn.nhom3.doan_laptrinhweb.model.Image;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponse {
    private int id;
    private String name;
    private double price;
    private String description;
    private String theme;
    private String unit;
    private int cate_id;
    private List<Image> images;
}
