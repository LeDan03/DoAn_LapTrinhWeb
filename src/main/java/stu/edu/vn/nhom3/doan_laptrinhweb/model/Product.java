package stu.edu.vn.nhom3.doan_laptrinhweb.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int prod_id;

    @Column(columnDefinition = "varchar(100)")
    private String prod_name;

    @Column
    private double prod_price;

    @Column(columnDefinition = "text")
    private String prod_description;

    @Column(columnDefinition = "varchar(100)")
    private String prod_theme;

    @Column
    private int cate_id;

    @OneToMany(mappedBy = "product")
    private List<Comment> comments;

    @ManyToOne
    @JoinColumn(name = "cate_id",insertable = false, updatable = false)
    private Category category;

    @OneToMany(mappedBy = "product")
    private List<Images> images;

    @ManyToMany(mappedBy = "products")
    private List<OrderDetail> orderDetails;

    public Product() {
    }

    public Product(String prod_name, double prod_price, String prod_description, String prod_theme, int cate_id) {
        this.prod_name = prod_name;
        this.prod_price = prod_price;
        this.prod_description = prod_description;
        this.prod_theme = prod_theme;
        this.cate_id = cate_id;
    }

    public int getProd_id() {
        return prod_id;
    }

    public String getProd_name() {
        return prod_name;
    }

    public void setProd_name(String prod_name) {
        this.prod_name = prod_name;
    }

    public double getProd_price() {
        return prod_price;
    }

    public void setProd_price(double prod_price) {
        this.prod_price = prod_price;
    }

    public String getProd_description() {
        return prod_description;
    }

    public void setProd_description(String prod_description) {
        this.prod_description = prod_description;
    }

    public String getProd_theme() {
        return prod_theme;
    }

    public void setProd_theme(String prod_theme) {
        this.prod_theme = prod_theme;
    }

    public int getCate_id() {
        return cate_id;
    }

    public void setCate_id(int cate_id) {
        this.cate_id = cate_id;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public List<Images> getImages() {
        return images;
    }

    public void setImages(List<Images> images) {
        this.images = images;
    }

    public List<OrderDetail> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(List<OrderDetail> orderDetails) {
        this.orderDetails = orderDetails;
    }
}
