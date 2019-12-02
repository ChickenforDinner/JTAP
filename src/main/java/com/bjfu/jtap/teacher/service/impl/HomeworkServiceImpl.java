package com.bjfu.jtap.teacher.service.impl;

import com.bjfu.jtap.entity.*;
import com.bjfu.jtap.mapper.HomeworkMapper;
import com.bjfu.jtap.mapper.HomeworkStudentMapper;
import com.bjfu.jtap.mapper.UserMapper;
import com.bjfu.jtap.mapper.UserTermMapper;
import com.bjfu.jtap.teacher.service.HomeworkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Author: Tianyu Xiao
 * @CreateDate: 2019/5/6  8:13
 */
@Service
@Transactional
public class HomeworkServiceImpl implements HomeworkService {
    @Autowired
    private HomeworkMapper homeworkMapper;
    @Autowired
    private HomeworkStudentMapper homeworkStudentMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserTermMapper userTermMapper;
    //文件上传路径
    @Value("${FILE_LOCAL_PATH}")
    private String FILE_PATH;
    //文件服务器路径前缀
    @Value("${FILE_SERVER_PATH}")
    private String FILE_SERVER_PATH;

    /**
     * 添加作业
     * @param homework
     */
    public void addHomeWork(Homework homework) {
        homeworkMapper.insertSelective(homework);
    }

    /**
     * 根据教师id查询
     * @param teacherId
     * @return
     */
    public List<Homework> selectAllByTeacherId(Integer teacherId) {
        HomeworkExample example = new HomeworkExample();
        example.createCriteria().andTeacherIdEqualTo(teacherId);
        return homeworkMapper.selectByExample(example);
    }

    /**
     * 根据作业Id查询已提交学生的作业
     * @param homeworkId
     * @return
     */
    public List<HomeworkStudent> homeworkStudentList(Integer homeworkId) {
        HomeworkStudentExample homeworkStudentExample = new HomeworkStudentExample();
        homeworkStudentExample.createCriteria().andHomeworkIdEqualTo(homeworkId);
        List<HomeworkStudent> homeworkStudentList = homeworkStudentMapper.selectByExample(homeworkStudentExample);
        for(HomeworkStudent homeworkStudent : homeworkStudentList) {
            User user = userMapper.selectByPrimaryKey(homeworkStudent.getStudentId());
            homeworkStudent.setStudent(user);

            homeworkStudent.setPath(FILE_SERVER_PATH + homeworkStudent.getPath());
        }
        return homeworkStudentList;
    }

    /**
     * 获取统计列表
     * @param homeworkId
     * @param termId
     * @param teacherId
     * @return
     */
    public Map<String, Number> homeworkStatistics(Integer termId,Integer homeworkId,Integer teacherId) {

        //初始化一个返回map
        Map<String,Number> resultMap = new HashMap<>();

        //1.查询该学期所有的人数
        UserTermExample userTermExample = new UserTermExample();
        userTermExample.createCriteria().andTermidEqualTo(termId).andTeacheridEqualTo(teacherId);
        List<UserTerm> userTermList = userTermMapper.selectByExample(userTermExample);
        if(userTermList == null) {
            userTermList = new ArrayList<>();
        }
        //2.查询已提交的学生人数
        HomeworkStudentExample homeworkExample = new HomeworkStudentExample();
        homeworkExample.createCriteria().andHomeworkIdEqualTo(homeworkId);
        List<HomeworkStudent> homeworkStudentList = homeworkStudentMapper.selectByExample(homeworkExample);
        if(homeworkStudentList == null) {
            homeworkStudentList = new ArrayList<>();
        }
        //3.计算已提交的学生人数和未提交的学生人数，保存到map中
        Integer yitijiao = homeworkStudentList.size();
        Integer weitijiao = userTermList.size() - homeworkStudentList.size();
        resultMap.put("yitijiao",yitijiao);
        resultMap.put("weitijiao",weitijiao);

        return resultMap;
    }


}
