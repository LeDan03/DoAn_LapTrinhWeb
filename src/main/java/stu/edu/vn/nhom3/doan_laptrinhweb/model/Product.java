package stu.edu.vn.nhom3.doan_laptrinhweb.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, length = 255)
    private String name;

    @Column(nullable = false)
    private double price;

    @Column(columnDefinition = "text")
    private String description;

    @Column
    private String theme;

    @Column
    private String unit;

    @Column
    private int cate_id;

    @ManyToOne
    @JoinColumn(name = "cate_id",insertable = false, updatable = false)
    private Category category;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<Image> images;
}
