package com.bjfu.jtap.student.controller;

import com.bjfu.jtap.entity.Homework;
import com.bjfu.jtap.entity.HomeworkStudent;
import com.bjfu.jtap.entity.User;
import com.bjfu.jtap.student.service.StudentHomeworkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Description:
 * @Author: Tianyu Xiao
 * @CreateDate: 2019/5/6  9:41
 */
@Controller
public class StudentHomeworkController {

    @Autowired
    private StudentHomeworkService studentHomeworkService;

    /**
     * 作业列表
     * @param request
     * @return
     */
    @GetMapping("/student/homework/list")
    public String homeworkList(HttpServletRequest request) {
        User user = (User)request.getSession().getAttribute("user");
        Integer studentId = user.getId();
        List<Homework> homeworkList = studentHomeworkService.homeworkList(studentId);
        request.setAttribute("homeworkList",homeworkList);
        return "student/homework/homework-list";
    }

    /**
     * 去上传作业页面
     * @return
     */
    @GetMapping(value = "/student/homework/toUpload/{homeworkId}")
    public String toUpload(@PathVariable Integer homeworkId,HttpServletRequest request) {
        request.setAttribute("homeworkId",homeworkId);
        return "student/homework/homework-upload";
    }

    /**
     * 上传作业
     * @param file
     * @param request
     * @return
     */
    @PostMapping(value = "/student/homework/upload")
    public String upload(MultipartFile file,HttpServletRequest request) {
        String homeworkId = request.getParameter("homeworkId");
        User user = (User)request.getSession().getAttribute("user");
        Integer studentId = user.getId();
        studentHomeworkService.fileUpload(file,Integer.valueOf(homeworkId),studentId);
        return "student/homework/homework-upload";
    }

    /**
     * 下载(查看)作业
     * @param homeworkId
     * @return
     */
    @GetMapping(value = "/student/homework/download/{homeworkId}")
    public String downLoadHomwork(@PathVariable Integer homeworkId,HttpServletRequest request) {
        User user = (User)request.getSession().getAttribute("user");
        Integer studentId = user.getId();
        HomeworkStudent homeworkStudent = studentHomeworkService.selectById(studentId, homeworkId);
        request.setAttribute("homeworkStudent",homeworkStudent);
        return "student/homework/homework-download";
    }
}
