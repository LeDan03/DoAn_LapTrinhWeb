package stu.edu.vn.nhom3.doan_laptrinhweb.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import stu.edu.vn.nhom3.doan_laptrinhweb.model.Payment;
import stu.edu.vn.nhom3.doan_laptrinhweb.repository.PaymentRepository;

import java.util.List;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    public List<Payment> findAll() {
        return paymentRepository.findAll();
    }
    public Payment findById(int id) {
        return paymentRepository.findById(id).orElse(null);
    }
    public Payment save(Payment payment) {
        return paymentRepository.save(payment);
    }
    public boolean isExistedPayment(String name)
    {
        for (Payment payment : findAll())
            if (payment.getName().equals(name))
                return true;
        return false;
    }
}
