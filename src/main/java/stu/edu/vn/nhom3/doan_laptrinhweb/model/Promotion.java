package stu.edu.vn.nhom3.doan_laptrinhweb.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table
public class Promotion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int pro_id;

    @Column(columnDefinition = "varchar(200)", nullable = false)
    private String pro_name;

    @Column(columnDefinition = "text", nullable = false)
    private String pro_description;

    @Column(columnDefinition = "char(20)", nullable = false)
    private String pro_code;

    @Column(nullable = false)
    private float pro_value;

    @OneToMany(mappedBy = "promotion")
    private List<Orders> orders;

    public Promotion() {
    }

    public Promotion(String pro_name, String pro_description, String pro_code, float pro_value) {
        this.pro_name = pro_name;
        this.pro_description = pro_description;
        this.pro_code = pro_code;
        this.pro_value = pro_value;
    }

    public int getPro_id() {
        return pro_id;
    }

    public String getPro_name() {
        return pro_name;
    }

    public void setPro_name(String pro_name) {
        this.pro_name = pro_name;
    }

    public String getPro_description() {
        return pro_description;
    }

    public void setPro_description(String pro_description) {
        this.pro_description = pro_description;
    }

    public String getPro_code() {
        return pro_code;
    }

    public void setPro_code(String pro_code) {
        this.pro_code = pro_code;
    }

    public float getPro_value() {
        return pro_value;
    }

    public void setPro_value(float pro_value) {
        this.pro_value = pro_value;
    }
}
