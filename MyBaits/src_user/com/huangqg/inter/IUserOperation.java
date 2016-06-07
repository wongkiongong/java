package com.huangqg.inter;

import com.huangqg.mybatis.model.User;

public interface IUserOperation {
    public User selectUserByID(int id);
}
