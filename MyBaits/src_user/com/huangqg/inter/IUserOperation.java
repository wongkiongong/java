package com.huangqg.inter;

import java.util.List;

import com.huangqg.mybatis.model.User;

public interface IUserOperation {
    public User selectUserByID(int id);
    public List<User> selectUserName(String name);
    public void addUser(User user);
    public void updateUser(User user);
    public void deleteUser(int id);
}
