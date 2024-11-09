package stu.edu.vn.nhom3.doan_laptrinhweb.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import stu.edu.vn.nhom3.doan_laptrinhweb.model.User;
import stu.edu.vn.nhom3.doan_laptrinhweb.repository.UserRepository;
import stu.edu.vn.nhom3.doan_laptrinhweb.services.UserService;

import java.util.Date;
import java.util.List;

@Controller
public class HomePage {

    @RequestMapping(value = "/login")
    public ModelAndView homepage(HttpServletRequest request) {

        //test nhận, gửi dữ liệu
        ModelAndView mav = new ModelAndView("index");
        Date date=new Date();
        date.setTime(date.getTime());
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        User user = new User(username,"",password,date,date,true,1);
        mav.addObject("user",user);
        return mav;
    }
    @RequestMapping(value = "/showUser")
    public ModelAndView showUser()
    {
        ModelAndView mav = new ModelAndView("showUser");

        return mav;
    }
}
