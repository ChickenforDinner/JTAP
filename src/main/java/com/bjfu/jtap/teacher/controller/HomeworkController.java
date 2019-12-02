package com.bjfu.jtap.teacher.controller;

import com.bjfu.jtap.entity.Homework;
import com.bjfu.jtap.entity.HomeworkStudent;
import com.bjfu.jtap.entity.Term;
import com.bjfu.jtap.entity.User;
import com.bjfu.jtap.teacher.service.HomeworkService;
import com.bjfu.jtap.teacher.service.TermService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Author: Tianyu Xiao
 * @CreateDate: 2019/5/6  8:12
 */
@Controller
public class HomeworkController {

    @Autowired
    private HomeworkService homeworkService;
    @Autowired
    private TermService termService;

    /**
     * 去添加页面
     * @return
     */
    @GetMapping(value = "/teacher/homework/add")
    public String toAdd(HttpServletRequest request) {
        //1.查询学期列表
        List<Term> termList = termService.termList();
        request.setAttribute("termList",termList);
        return "teacher/homework/homework-add";
    }

    /**
     * 添加作业
     * @param homework
     * @return
     */
    @PostMapping(value = "/teacher/homework/add")
    public String add(Homework homework, HttpServletRequest request) {
        User teacher = (User)request.getSession().getAttribute("user");
        Integer teacherId = teacher.getId();
        homework.setTeacherId(teacherId);
        homeworkService.addHomeWork(homework);
        return "redirect:/teacher/homework/list";
    }

    /**
     * 作业列表
     * @param request
     * @return
     */
    @GetMapping(value = "/teacher/homework/list")
    public String list(HttpServletRequest request) {
        User teacher = (User)request.getSession().getAttribute("user");
        Integer teacherId = teacher.getId();
        List<Homework> homeworkList = homeworkService.selectAllByTeacherId(teacherId);
        request.setAttribute("homeworkList",homeworkList);
        return "teacher/homework/homework-list";
    }

    /**
     * 作业详情
     * @param homeworkId
     * @return
     */
    @GetMapping(value = "/teacher/homework/detail/{homeworkId}")
    public String homeworkDetail(@PathVariable Integer homeworkId,HttpServletRequest request) {
        List<HomeworkStudent> homeworkStudents = homeworkService.homeworkStudentList(homeworkId);
        request.setAttribute("sourceList",homeworkStudents);
        return "teacher/homework/homework-detail";
    }

    /**
     * 去统计页面
     * @return
     */
    @GetMapping(value = "/teacher/homework/statistics/{termId}/{homeworkId}")
    public String toHomeworkStatistics(@PathVariable Integer termId,@PathVariable Integer homeworkId,HttpServletRequest request) {
        request.setAttribute("homeworkId",homeworkId);
        request.setAttribute("termId",termId);
        return "teacher/homework/homework-statistics";
    }

    /**
     * 统计饼状图
     * @param termId
     * @param homeworkId
     * @param request
     * @return
     */
    @RequestMapping(value = "/teacher/homework/ajax/statistics/{termId}/{homeworkId}")
    @ResponseBody
    public Map<String, Number> homeworkStatistics(@PathVariable Integer termId,@PathVariable Integer homeworkId,HttpServletRequest request) {
        User teacher = (User)request.getSession().getAttribute("user");
        Integer teacherId = teacher.getId();
        Map<String, Number> map = homeworkService.homeworkStatistics(termId, homeworkId, teacherId);
        return map;
    }
}
