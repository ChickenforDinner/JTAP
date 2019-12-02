package com.bjfu.jtap.common.service;

import com.bjfu.jtap.entity.User;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Description:
 * @Author: Tianyu Xiao
 * @CreateDate: 2019/1/27  21:57
 */
public interface CommonService {

    //用户登陆
    User userLogin(User user);

    //修改用户信息
    void updateUser(User user, MultipartFile file);

    //根据id查询用户
    User selectUserById(int id);
}
