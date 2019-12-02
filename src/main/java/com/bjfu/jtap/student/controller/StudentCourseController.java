package com.bjfu.jtap.student.controller;

import com.bjfu.jtap.entity.Course;
import com.bjfu.jtap.entity.QuestionWithBLOBs;
import com.bjfu.jtap.entity.Source;
import com.bjfu.jtap.entity.User;
import com.bjfu.jtap.student.service.StudentCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Description:
 * @Author: Tianyu Xiao
 * @CreateDate: 2019/1/29  19:59
 */
@Controller
public class StudentCourseController {
    @Autowired
    private StudentCourseService studentCourseService;

    /**
     * 展示所有章节
     * @param request
     * @return
     */
    @RequestMapping(value = "/student/course/courseList")
    public String courseList(HttpServletRequest request) {
       User user = (User) request.getSession().getAttribute("user");
        //查询章节集合
        //User user = new User();user.setId(2);
        List<Course> courseList = studentCourseService.courseList(user.getId());
        request.setAttribute("courseList",courseList);
        return "/student/course/list";
    }

    /**
     * 去在线视频播放页面
     * @param request
     * @param sourceId
     * @return
     */
    @RequestMapping("/student/course/toSeeVideo/{sourceId}")
    public String toSeeVideo(HttpServletRequest request, @PathVariable int sourceId) {
        Source source = studentCourseService.selectSourceById(sourceId);
        request.setAttribute("source",source);
        return "/student/course/seeVideo";
    }

    /**
     * 去做题页面
     * @param request
     * @param courseId
     * @return
     */
    @RequestMapping(value = "/student/course/toDoQuestion/{courseId}")
    public String toDoQuestion(HttpServletRequest request,@PathVariable int courseId) {
        List<QuestionWithBLOBs> questionList = studentCourseService.selectQuestionListByCourseId(courseId);
        //做题阶段设置答案为Null
        for(QuestionWithBLOBs question : questionList) {
            question.setAnswer(null);
            question.setDetail(null);
        }
        request.setAttribute("questionList",questionList);
        return "/student/course/question";
    }

    /**
     * 做题页面
     * @param request
     * @param courseId
     * @return
     */
    @RequestMapping(value = "/student/course/doQuestion/{courseId}")
    public String doQuestion(HttpServletRequest request,@PathVariable int courseId) {
        List<QuestionWithBLOBs> questionList = studentCourseService.selectQuestionListByCourseId(courseId);
        //做题阶段设置答案为Null
        request.setAttribute("questionList",questionList);
        //放置courseId
        request.setAttribute("courseId",courseId);
        return "/student/course/question";
    }

}
