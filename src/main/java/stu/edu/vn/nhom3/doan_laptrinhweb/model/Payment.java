package stu.edu.vn.nhom3.doan_laptrinhweb.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int pmt_id;

    @Column(columnDefinition = "varchar(50)")
    private String pmt_name;

    @OneToMany(mappedBy = "pmt")
    private List<Orders> orders;
    public Payment() {
    }

    public Payment(String pmt_name) {
        this.pmt_name = pmt_name;
    }

    public int getPmt_id() {
        return pmt_id;
    }


    public String getPmt_name() {
        return pmt_name;
    }

    public void setPmt_name(String pmt_name) {
        this.pmt_name = pmt_name;
    }

    public List<Orders> getOrders() {
        return orders;
    }

    public void setOrders(List<Orders> orders) {
        this.orders = orders;
    }
}
