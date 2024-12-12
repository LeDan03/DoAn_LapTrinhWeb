package stu.edu.vn.nhom3.doan_laptrinhweb.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ProductDTO {
    private int id;
    private String name;
    private double price;
    private String unit;
    private String theme;
    private String description;
    private int category_id;
    private String category_name;
    private List<String> listImage;
}
