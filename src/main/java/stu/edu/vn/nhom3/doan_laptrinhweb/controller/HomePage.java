package stu.edu.vn.nhom3.doan_laptrinhweb.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import stu.edu.vn.nhom3.doan_laptrinhweb.model.User;
import stu.edu.vn.nhom3.doan_laptrinhweb.services.UserService;

import java.util.Date;
import java.util.Map;

@Controller
public class HomePage {

    @Autowired
    UserService userService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @RequestMapping(value = "/login")
    public ModelAndView homepage(HttpServletRequest request) {

        //test nhận, gửi dữ liệu
        ModelAndView mav = new ModelAndView("index");
        return mav;
    }

    @RequestMapping(value = "/dangnhap", method = RequestMethod.GET)
    public ModelAndView loginPage()
    {
        ModelAndView mav = new ModelAndView("login");
        return mav;
    }
    @RequestMapping(value = "/dangnhap", method = RequestMethod.POST)
    public ModelAndView loginProcess(@RequestParam Map<String, Object> params)
    {
        ModelAndView mav = new ModelAndView("login");
        String username = params.get("username").toString();
        String password = params.get("password").toString();
        if(userService.isValidUserLogin(username,new BCryptPasswordEncoder().encode(password)))
            mav.setViewName("index");
        return mav;
    }

    @RequestMapping(value = "/dangky", method = RequestMethod.GET)
    public ModelAndView register() {
        ModelAndView mav = new ModelAndView("register");
        return mav;
    }

    @RequestMapping(value = "/dangky", method = RequestMethod.POST)
    public ModelAndView registerProcess(@RequestParam Map<String,Object> params)
    {
//        List<UserDTO> users = userService.getAllUsers();
//        userService.saveUser();
        ModelAndView mav = new ModelAndView("register");
        User user = new User();
        //Su dung HttpServletRequest
//        user.setName(request.getParameter("username"));
//        user.setUs_passwordHash(request.getParameter("password"));
//        user.setEmail(request.getParameter("email"));
        user.setName((String)params.get("username"));
        user.setEmail((String)params.get("email"));
        user.setPasswordHash(passwordEncoder.encode((String)params.get("password")));
        user.setCreateBy(new Date());
        user.setUpdateDate(new Date());
        user.setRole_id(2);
        userService.saveUser(user);
        return mav;
    }
}
