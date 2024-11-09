package stu.edu.vn.nhom3.doan_laptrinhweb.model;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table
public class Orders {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long ord_id;

    @Column(nullable = false,columnDefinition = "varchar(10)")
    private String ord_phone;

    @Column(nullable = false,columnDefinition = "varchar(200)")
    private String ord_address;

    @Column(nullable = false)
    private boolean ord_pay;

    @Column(nullable = false)
    private double ord_feeShip;

    @Column(nullable = false)
    private Date ord_createDate;

    @Column(nullable = false)
    private int pmn_id;

    @Column(nullable = false)
    private int us_id;

    @ManyToOne
    @JoinColumn(name = "us_id",insertable = false, updatable = false)
    private User us;

    @Column(nullable = false)
    private int pro_id;

    @ManyToOne
    @JoinColumn(name = "pmt_id")
    private Payment pmt;

    @ManyToOne
    @JoinColumn(name = "pro_id",insertable = false, updatable = false)
    private Promotion promotion;

    @OneToOne(mappedBy = "order")
    private OrderDetail orderDetail;

    public Orders(String ord_phone, String ord_address, boolean ord_pay, double ord_feeShip, Date ord_createDate, int pmn_id, int us_id, User us, int pro_id) {
        this.ord_phone = ord_phone;
        this.ord_address = ord_address;
        this.ord_pay = ord_pay;
        this.ord_feeShip = ord_feeShip;
        this.ord_createDate = ord_createDate;
        this.pmn_id = pmn_id;
        this.us_id = us_id;
        this.us = us;
        this.pro_id = pro_id;
    }

    public Orders() {
    }

    public long getOrd_id() {
        return ord_id;
    }


    public String getOrd_phone() {
        return ord_phone;
    }

    public void setOrd_phone(String ord_phone) {
        this.ord_phone = ord_phone;
    }

    public String getOrd_address() {
        return ord_address;
    }

    public void setOrd_address(String ord_address) {
        this.ord_address = ord_address;
    }

    public boolean isOrd_pay() {
        return ord_pay;
    }

    public void setOrd_pay(boolean ord_pay) {
        this.ord_pay = ord_pay;
    }

    public double getOrd_feeShip() {
        return ord_feeShip;
    }

    public void setOrd_feeShip(double ord_feeShip) {
        this.ord_feeShip = ord_feeShip;
    }

    public Date getOrd_createDate() {
        return ord_createDate;
    }

    public void setOrd_createDate(Date ord_createDate) {
        this.ord_createDate = ord_createDate;
    }

    public int getPmn_id() {
        return pmn_id;
    }

    public void setPmn_id(int pmn_id) {
        this.pmn_id = pmn_id;
    }

    public User getUs() {
        return us;
    }

    public void setUs(User us) {
        this.us = us;
    }

    public int getPro_id() {
        return pro_id;
    }

    public void setPro_id(int pro_id) {
        this.pro_id = pro_id;
    }

    public int getUs_id() {
        return us_id;
    }

    public void setUs_id(int us_id) {
        this.us_id = us_id;
    }

    public Payment getPmt() {
        return pmt;
    }

    public void setPmt(Payment pmt) {
        this.pmt = pmt;
    }

    public Promotion getPromotion() {
        return promotion;
    }

    public void setPromotion(Promotion promotion) {
        this.promotion = promotion;
    }

    public OrderDetail getOrderDetail() {
        return orderDetail;
    }

    public void setOrderDetail(OrderDetail orderDetail) {
        this.orderDetail = orderDetail;
    }
}
