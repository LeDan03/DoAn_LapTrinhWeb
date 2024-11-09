package stu.edu.vn.nhom3.doan_laptrinhweb;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import stu.edu.vn.nhom3.doan_laptrinhweb.repository.UserRepository;

@SpringBootApplication
@RequiredArgsConstructor
public class DoAnLapTrinhWebApplication {

    public static void main(String[] args) {

        ApplicationContext context= SpringApplication.run(DoAnLapTrinhWebApplication.class, args);
//        UserRepository userRepository= context.getBean(UserRepository.class);
//        userRepository.findAll().forEach(System.out::println);
    }

}
