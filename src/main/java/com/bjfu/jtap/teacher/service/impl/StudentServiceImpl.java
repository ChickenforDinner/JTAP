package com.bjfu.jtap.teacher.service.impl;

import com.bjfu.jtap.common.vo.StudentQueryVo;
import com.bjfu.jtap.entity.*;
import com.bjfu.jtap.mapper.CourseSourceMapper;
import com.bjfu.jtap.mapper.DiscussionMapper;
import com.bjfu.jtap.mapper.UserMapper;
import com.bjfu.jtap.mapper.UserTermMapper;
import com.bjfu.jtap.teacher.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.LinkedList;
import java.util.List;

/**
 * @Description:
 * @Author: Tianyu Xiao
 * @CreateDate: 2019/1/28  10:43
 */
@Service
@Transactional
public class StudentServiceImpl implements StudentService{
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserTermMapper userTermMapper;
    @Autowired
    private CourseSourceMapper courseSourceMapper;
    @Autowired
    private DiscussionMapper discussionMapper;

    /**
     * 条件查询学生列表
     * @param studentQueryVo
     * @return
     */
    public List<User> selectStudentListByQueryVo(StudentQueryVo studentQueryVo) {
        //如果前端多条件查询数据为空
        if(StringUtils.isEmpty(studentQueryVo.getUsername()) && studentQueryVo.getTermId()==0) {
            return null;
        }
        //拼接查询条件
        UserExample userExample = new UserExample();
        UserExample.Criteria userExampleCriteria = userExample.createCriteria();

        //角色是学生
        userExampleCriteria.andRoleEqualTo(2);

        //1.学院
        /*if(!StringUtils.isEmpty(studentQueryVo.getCollege())) {
            userExampleCriteria.andCollegeLike(studentQueryVo.getCollege());
        }*/
        //2.用户名
        if(!StringUtils.isEmpty(studentQueryVo.getUsername())) {
            userExampleCriteria.andUsernameLike("%"+studentQueryVo.getUsername()+"%");
        }
        //3.学期
        if(studentQueryVo.getTermId()!=0) {
            UserTermExample userTermExample = new UserTermExample();
            UserTermExample.Criteria userTermExampleCriteria = userTermExample.createCriteria();
            userTermExampleCriteria.andTeacheridEqualTo(studentQueryVo.getTeacherId());
            userTermExampleCriteria.andTermidEqualTo(studentQueryVo.getTermId());
            List<UserTerm> userTermList = userTermMapper.selectByExample(userTermExample);
            if(userTermList != null && userTermList.size() > 0) {
                List<Integer> idList = new LinkedList<>();
                for(UserTerm ut : userTermList) {
                    idList.add(ut.getId());
                }
                userExampleCriteria.andIdIn(idList);
            }else {
                return null;
            }

        }

        //查询
        List<User> userList = userMapper.selectByExample(userExample);
        return userList;
    }

    /**
     * 根据id查询学生信息
     * @param id
     * @return
     */
    public User findUserById(int id) {
        return userMapper.selectByPrimaryKey(id);
    }

    /**
     * 更新用户信息
     * @param user
     */
    public void updateUser(User user) {
        userMapper.updateByPrimaryKeySelective(user);
    }

    /**
     * 添加用户信息
     * @param user
     */
    public void addUser(User user,int termId,int teacherId) {
        //1.保存用户基本信息
        //封装前端没有的参数
        user.setRole(2);
        user.setIddelete(0);
        //默认密码
        user.setPassword("123456");
        userMapper.insert(user);
        //2.保存学期-教师-学生关系中间表
        //设定自增主键返回
        UserTerm userTerm = new UserTerm();
        userTerm.setTermid(termId);
        userTerm.setTeacherid(teacherId);
        userTerm.setStudentid(user.getId());
        userTermMapper.insert(userTerm);
    }

    /**
     * 删除用户
     * @param id
     */
    public void deleteUser(Integer id) {
        //1删除用户表
        userMapper.deleteByPrimaryKey(id);
        //2删除用户-学期表
        UserTermExample userTermExample = new UserTermExample();
        UserTermExample.Criteria userTermExampleCriteria = userTermExample.createCriteria();
        userTermExampleCriteria.andStudentidEqualTo(id);
        userTermMapper.deleteByExample(userTermExample);

        //3.删除讨论表
        DiscussionExample discussionExample1 = new DiscussionExample();
        DiscussionExample.Criteria discussionCriteria1 = discussionExample1.createCriteria();
        discussionCriteria1.andPublishIdEqualTo(id);
        discussionMapper.deleteByExample(discussionExample1);

        DiscussionExample discussionExample2 = new DiscussionExample();
        DiscussionExample.Criteria discussionCriteria2 = discussionExample2.createCriteria();
        discussionCriteria2.andPublishIdEqualTo(id);
        discussionMapper.deleteByExample(discussionExample2);


    }
}
