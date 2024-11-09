package stu.edu.vn.nhom3.doan_laptrinhweb.model;

import jakarta.persistence.*;

@Entity
@Table
public class Images {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int img_id;

    @Column
    private String img_link;

    @Column
    private int prod_id;

    @ManyToOne
    @JoinColumn(name = "prod_id",insertable = false, updatable = false)
    private Product product;

    public Images() {
    }

    public Images(String img_link, int prod_id) {
        this.img_link = img_link;
        this.prod_id = prod_id;
    }

    public int getImg_id() {
        return img_id;
    }

    public String getImg_link() {
        return img_link;
    }

    public void setImg_link(String img_link) {
        this.img_link = img_link;
    }

    public int getProd_id() {
        return prod_id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public void setProd_id(int prod_id) {
        this.prod_id = prod_id;
    }

}
