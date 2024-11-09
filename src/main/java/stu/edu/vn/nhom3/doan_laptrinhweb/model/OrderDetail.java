package stu.edu.vn.nhom3.doan_laptrinhweb.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table
public class OrderDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long det_id;

    @Column(nullable = false)
    private double prod_price;

    @Column
    private int prod_id;

    @Column(columnDefinition = "varchar(100)")
    private String prod_name;

    @Column
    private long ord_id;

    @Column
    private int det_countProd;

    @Column
    private double det_total;

    @ManyToMany
    @JoinTable(name = "orderdetail_product", joinColumns = @JoinColumn(name="det_id"), inverseJoinColumns =@JoinColumn(name="prod_id") )
    private List<Product> products;

    @OneToOne
    @JoinColumn(name="det_id",insertable = false, updatable = false)
    private Orders order;

    public OrderDetail() {
    }

    public OrderDetail(double prod_price, int prod_id, String prod_name, long ord_id, int det_countProd, double det_total) {
        this.prod_price = prod_price;
        this.prod_id = prod_id;
        this.prod_name = prod_name;
        this.ord_id = ord_id;
        this.det_countProd = det_countProd;
        this.det_total = det_total;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public Orders getOrder() {
        return order;
    }

    public void setOrder(Orders order) {
        this.order = order;
    }

    public long getDet_id() {
        return det_id;
    }

    public double getProd_price() {
        return prod_price;
    }

    public void setProd_price(double prod_price) {
        this.prod_price = prod_price;
    }

    public int getProd_id() {
        return prod_id;
    }

    public void setProd_id(int prod_id) {
        this.prod_id = prod_id;
    }

    public String getProd_name() {
        return prod_name;
    }

    public void setProd_name(String prod_name) {
        this.prod_name = prod_name;
    }

    public long getOrd_id() {
        return ord_id;
    }

    public void setOrd_id(long ord_id) {
        this.ord_id = ord_id;
    }

    public int getDet_countProd() {
        return det_countProd;
    }

    public void setDet_countProd(int det_countProd) {
        this.det_countProd = det_countProd;
    }

    public double getDet_total() {
        return det_total;
    }

    public void setDet_total(double det_total) {
        this.det_total = det_total;
    }
}
