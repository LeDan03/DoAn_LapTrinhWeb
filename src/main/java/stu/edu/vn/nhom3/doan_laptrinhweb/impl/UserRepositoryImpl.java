package stu.edu.vn.nhom3.doan_laptrinhweb.impl;

import org.springframework.stereotype.Repository;
import stu.edu.vn.nhom3.doan_laptrinhweb.model.User;
import stu.edu.vn.nhom3.doan_laptrinhweb.repository.UserRepository;

public abstract  class UserRepositoryImpl implements UserRepository {

    UserRepository userRepository;

    @Override
    public User getUserById(int id) {
        return null;
    }

    @Override
    public User getUserByEmail(String email) {
        return null;
    }

    @Override
    public User getUserByName(String name) {
        User user = new User();
        user=userRepository.getUserByName(name);
        return user;
    }

}
