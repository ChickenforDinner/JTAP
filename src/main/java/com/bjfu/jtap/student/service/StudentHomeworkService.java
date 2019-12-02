package com.bjfu.jtap.student.service;

import com.bjfu.jtap.entity.Homework;
import com.bjfu.jtap.entity.HomeworkStudent;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @Description:
 * @Author: Tianyu Xiao
 * @CreateDate: 2019/5/6  9:43
 */
public interface StudentHomeworkService {

    /**
     * 作业列表
     * @param studentId
     * @return
     */
    List<Homework> homeworkList(Integer studentId);

    /**
     * 文件上传
     * @param file
     */
    void fileUpload(MultipartFile file,Integer homeworkId,Integer studentId);

    /**
     * 查询学生作业
     * @param homeworkId
     * @param studentId
     * @return
     */
    HomeworkStudent selectById(Integer studentId, Integer homeworkId);
}
