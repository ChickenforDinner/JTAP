package com.bjfu.jtap.teacher.service;

import com.bjfu.jtap.common.vo.StudentQueryVo;
import com.bjfu.jtap.entity.User;

import java.util.List;

/**
 * @Description:学生管理业务层接口
 * @Author: Tianyu Xiao
 * @CreateDate: 2019/1/28  10:41
 */
public interface StudentService {

    /**
     * 条件查询学生列表
     * @param studentQueryVo
     * @return
     */
    List<User> selectStudentListByQueryVo(StudentQueryVo studentQueryVo);

    /**
     * 根据id查询
     * @param id
     * @return
     */
    User findUserById(int id);

    /**
     * 更新用户信息
     * @param user
     */
    void updateUser(User user);

    /**
     * 添加用户信息
     * @param user
     * @param termId
     * @param teacherId
     */
    void addUser(User user,int termId,int teacherId);

    /**
     * 删除用户
     * @param id
     */
    void deleteUser(Integer id);
}
