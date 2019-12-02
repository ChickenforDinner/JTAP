package com.bjfu.jtap.student.service;

import com.bjfu.jtap.entity.*;

import java.util.List;

/**
 * @Description:
 * @Author: Tianyu Xiao
 * @CreateDate: 2019/1/29  21:23
 */
public interface StudentCourseService {

    /**
     * 根据用户Id查询章节集合
     * @param userId
     * @return
     */
    List<Course> courseList(int userId);

    /**
     * 根据id查询
     * @param id
     * @return
     */
    Source selectSourceById(int id);

    /**
     * 根据章节id查询题目集合
     * @param courseId
     * @return
     */
    List<QuestionWithBLOBs> selectQuestionListByCourseId(int courseId);

}
