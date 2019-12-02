package com.bjfu.jtap.teacher.service;

import com.bjfu.jtap.entity.Homework;
import com.bjfu.jtap.entity.HomeworkStudent;

import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Author: Tianyu Xiao
 * @CreateDate: 2019/5/6  8:12
 */
public interface HomeworkService {
    /**
     * 添加作业
     * @param homework
     */
    void addHomeWork(Homework homework);

    /**
     * 根据教师id查询
     * @param teacherId
     * @return
     */
    List<Homework> selectAllByTeacherId(Integer teacherId);

    /**
     * 根据作业Id查询已提交学生的作业
     * @param homeworkId
     * @return
     */
    List<HomeworkStudent> homeworkStudentList(Integer homeworkId);

    /**
     * 获取统计列表
     * @param homeworkId
     * @param termId
     * @param teacherId
     * @return
     */
    Map<String,Number> homeworkStatistics(Integer termId,Integer homeworkId,Integer teacherId);
}
