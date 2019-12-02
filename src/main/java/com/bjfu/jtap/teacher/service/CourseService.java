package com.bjfu.jtap.teacher.service;

import com.bjfu.jtap.entity.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @Description:课程章节管理
 * @Author: Tianyu Xiao
 * @CreateDate: 2019/1/28  20:16
 */

public interface CourseService {

    /**
     * 添加课程章节信息
     * @param course
     */
    void addCourse(Course course);

    /**
     * 根据学期id查询所有课程列表
     * @param termId
     * @return
     */
    List<Course> courseList(Integer termId);

    /**
     * 根据id查询
     * @param courseId
     * @return
     */
    Course selectByCourseId(int courseId);

    /**
     * 修改课程章节信息
     * @param course
     */
    void updateCourse(Course course);

    /**
     * 为章节添加习题
     * @param courseId:章节id
     * @param qIds:题目Id数组
     */
    void addCourseQuestion(int courseId,int[] qIds);

    /**
     * 根据章节id查询题目
     * @param courseId
     * @return
     */
    List<QuestionWithBLOBs> selectQuestionByCourseId(int courseId);

    /**
     * 根据章节id查询资源文件
     * @param courseId
     * @return
     */
    List<Source> selectSourceByCourseId(int courseId);

    /**
     * 资源文件上传
     * @param file
     * @param source
     */
    void addSource(MultipartFile file, Source source,int courseId);

    /**
     * 根据单元和题目Id删除
     * @param courseId
     * @param questionId
     */
    void deleteQuestionByCourseAndQuestion(Integer courseId,Integer questionId);

    /**
     * 根据单元和资源id删除
     * @param courseId
     * @param sourceId
     */
    void deleteSourceByCourseAndSource(Integer courseId,Integer sourceId);
}
