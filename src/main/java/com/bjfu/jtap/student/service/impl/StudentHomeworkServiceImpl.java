package com.bjfu.jtap.student.service.impl;

import com.bjfu.jtap.entity.*;
import com.bjfu.jtap.mapper.HomeworkMapper;
import com.bjfu.jtap.mapper.HomeworkStudentMapper;
import com.bjfu.jtap.mapper.UserTermMapper;
import com.bjfu.jtap.student.service.StudentHomeworkService;
import com.bjfu.jtap.utils.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

/**
 * @Description:
 * @Author: Tianyu Xiao
 * @CreateDate: 2019/5/6  9:46
 */
@Service
@Transactional
public class StudentHomeworkServiceImpl implements StudentHomeworkService {

    @Autowired
    private HomeworkMapper homeworkMapper;
    @Autowired
    private UserTermMapper userTermMapper;
    @Autowired
    private HomeworkStudentMapper homeworkStudentMapper;

    //文件上传路径
    @Value("${FILE_LOCAL_PATH}")
    private String FILE_PATH;
    //文件服务器路径前缀
    @Value("${FILE_SERVER_PATH}")
    private String FILE_SERVER_PATH;

    /**
     * 作业列表
     * @param studentId
     * @return
     */
    public List<Homework> homeworkList(Integer studentId) {

        Date nowDate = new Date();

        //1.先查询学生关联的教师
        UserTermExample userTermExample = new UserTermExample();
        UserTermExample.Criteria userTermExampleCriteria = userTermExample.createCriteria();
        userTermExampleCriteria.andStudentidEqualTo(studentId);
        List<UserTerm> userTermList = userTermMapper.selectByExample(userTermExample);
        UserTerm userTerm = new UserTerm();
        if(userTermList != null && userTermList.size() > 0) {
            userTerm = userTermList.get(0);
        }else {
            return new LinkedList<>();
        }
        //2.查询作业列表
        HomeworkExample homeworkExample = new HomeworkExample();
        HomeworkExample.Criteria homeworkExampleCriteria = homeworkExample.createCriteria();
        homeworkExampleCriteria.andTeacherIdEqualTo(userTerm.getTeacherid());
        List<Homework> homeworkList = homeworkMapper.selectByExample(homeworkExample);

        //查询当前该学生作业的状态
        for(Homework homework : homeworkList) {
            HomeworkStudentExample homeworkStudentExample = new HomeworkStudentExample();
            HomeworkStudentExample.Criteria criteria = homeworkStudentExample.createCriteria();
            criteria.andStudentIdEqualTo(studentId);
            criteria.andHomeworkIdEqualTo(homework.getId());
            List<HomeworkStudent> homeworkStudentList = homeworkStudentMapper.selectByExample(homeworkStudentExample);
            HomeworkStudent homeworkStudent = null;
            if(homeworkStudentList != null && homeworkStudentList.size() > 0) {
                homeworkStudent = homeworkStudentList.get(0);
            }

            //状态     1：可以上传      2：已上传       3：已过截至时间
            if(TimeUtil.belongCalendar(nowDate,homework.getStartTime(),homework.getEndTime()) == true) {//作业未截至
                if(homeworkStudent == null) {//该学生没有上交作业
                    homework.setHomeworkStatus(1);
                }else {//已上交作业
                    homework.setHomeworkStatus(2);
                }
            }else {//作业已截至
                homework.setHomeworkStatus(3);
            }

        }
        return homeworkList;
    }

    /**
     * 文件上传
     * @param file
     * @param homeworkId
     * @param studentId
     */
    public void fileUpload(MultipartFile file, Integer homeworkId, Integer studentId) {
        //设置文件名称
        String fileName = file.getOriginalFilename();

        HomeworkStudent homeworkStudent = new HomeworkStudent();
        homeworkStudent.setHomeworkId(homeworkId);
        homeworkStudent.setStudentId(studentId);

        homeworkStudent.setPath(fileName);
        //获取文件扩展名
        String extName = fileName.substring(fileName.lastIndexOf("."));
        //设置服务器中的文件名称
        String serverFileName = UUID.randomUUID().toString() + extName;
        // String path = "E:\\fileDir\\";
        //上传
        try {
            System.out.println();
            file.transferTo(new File(FILE_PATH + serverFileName));
            //路径配置到数据库
            homeworkStudent.setPath(serverFileName);
            homeworkStudent.setStatus(1);
            homeworkStudentMapper.insert(homeworkStudent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 查询学生作业
     * @param studentId
     * @param homeworkId
     * @return
     */
    public HomeworkStudent selectById(Integer studentId, Integer homeworkId) {
        HomeworkStudentExample example = new HomeworkStudentExample();
        example.createCriteria().andStudentIdEqualTo(studentId).andHomeworkIdEqualTo(homeworkId);
        List<HomeworkStudent> homeworkStudents = homeworkStudentMapper.selectByExample(example);
        HomeworkStudent homeworkStudent = new HomeworkStudent();
        if(homeworkStudents!=null && homeworkStudents.size()>0) {
            homeworkStudent = homeworkStudents.get(0);
            homeworkStudent.setPath(FILE_SERVER_PATH + homeworkStudent.getPath());
        }
        return homeworkStudent;
    }


}
