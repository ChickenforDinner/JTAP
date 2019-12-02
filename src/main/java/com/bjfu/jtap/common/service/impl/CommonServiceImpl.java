package com.bjfu.jtap.common.service.impl;

import com.bjfu.jtap.common.service.CommonService;
import com.bjfu.jtap.entity.User;
import com.bjfu.jtap.entity.UserExample;
import com.bjfu.jtap.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

/**
 * @Description:
 * @Author: Tianyu Xiao
 * @CreateDate: 2019/1/27  21:58
 */
@Service
@Transactional
public class CommonServiceImpl implements CommonService{
    @Autowired
    private UserMapper userMapper;
    @Value("${FILE_SERVER_PATH}")
    private String FILE_SERVER_PATH;
    @Value("${FILE_LOCAL_PATH}")
    private String FILE_LOCAL_PATH;

    /**
     * 用户登陆
     * @param user
     * @return
     */
    public User userLogin(User user) {
        UserExample userExample = new UserExample();
        UserExample.Criteria userExampleCriteria = userExample.createCriteria();
        userExampleCriteria.andUsernameEqualTo(user.getUsername());
        userExampleCriteria.andPasswordEqualTo(user.getPassword());
        userExampleCriteria.andRoleEqualTo(user.getRole());
        List<User> userList = userMapper.selectByExample(userExample);
        if(userList == null || userList.size() ==0) {
            return null;
        }
        user = userList.get(0);
        return user;
    }

    /**
     * 修改用户信息
     * @param user
     */
    public void updateUser(User user, MultipartFile file) {
        //文件名称
        String fileName = file.getOriginalFilename();
        if(fileName.equals("")) {
            userMapper.updateByPrimaryKeySelective(user);
        return ;
    }
    //获取文件扩展名
    String extName = fileName.substring(fileName.lastIndexOf("."));
    //设置服务器中的文件名称
    String serverFileName = UUID.randomUUID().toString() + extName;
        user.setImage(serverFileName);
        userMapper.updateByPrimaryKeySelective(user);
    //上传
        try {
            file.transferTo(new File(FILE_LOCAL_PATH + serverFileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据id查询用户
     * @param id
     * @return
     */
    public User selectUserById(int id) {
        User user = userMapper.selectByPrimaryKey(id);
        user.setImage(FILE_SERVER_PATH+user.getImage());
        return user;
    }
}
