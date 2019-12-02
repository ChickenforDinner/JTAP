package com.bjfu.jtap.teacher.controller;

import com.bjfu.jtap.common.vo.StudentQueryVo;
import com.bjfu.jtap.entity.Term;
import com.bjfu.jtap.entity.User;
import com.bjfu.jtap.teacher.service.StudentService;
import com.bjfu.jtap.teacher.service.TermService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedList;
import java.util.List;

/**
 * @Description:学生管理
 * @Author: Tianyu Xiao
 * @CreateDate: 2019/1/28  10:18
 */
@Controller
public class StudentController {
    @Autowired
    private StudentService studentService;
    @Autowired
    private TermService termService;


    /**
     * 条件查询学生列表
     * @param studentQueryVo
     * @return
     */
    @RequestMapping(value = "/teacher/student/list")
    public String studentList(StudentQueryVo studentQueryVo, HttpServletRequest request) {
        //1.查询学期列表
        List<Term> termList = termService.termList();
        request.setAttribute("termList",termList);
        //2.条件查询学生集合
        List<User> userList = studentService.selectStudentListByQueryVo(studentQueryVo);
        request.setAttribute("studentList",userList);
        return "/teacher/student/List";
    }

    /**
     * ajax返回学生列表
     * @param studentQueryVo
     * @param request
     * @param termId
     * @return
     */
    @GetMapping(value = "/teacher/student/ajax/list")
    @ResponseBody
    public List<User> studentAjaxList(StudentQueryVo studentQueryVo,HttpServletRequest request,Integer termId) {

        User teacher = (User)request.getSession().getAttribute("user");
        studentQueryVo.setTeacherId(teacher.getId());
        studentQueryVo.setTermId(termId);

        //条件查询学生集合
        List<User> userList = studentService.selectStudentListByQueryVo(studentQueryVo);
        if(userList == null || userList.size() ==0) {
            return new LinkedList<User>();
        }
        return userList;
    }

    /**
     * 去编辑页面
     * @param id
     * @return
     */
    @RequestMapping(value = "/teacher/student/toEdit/{id}")
    public String toEdit(@PathVariable int id,HttpServletRequest request) {
        User student = studentService.findUserById(id);
        request.setAttribute("student",student);
        return "/teacher/student/edit";
    }

    /**
     * 修改学生信息
     * @return
     */
    @PostMapping(value = "/teacher/student/edit")
    public String edit(User user) {
        studentService.updateUser(user);
        return "redirect:/teacher/student/list";
    }

    /**
     * 去添加页面
     * @return
     */
    @RequestMapping(value = "/teacher/student/toAdd")
    public String toAdd(HttpServletRequest request) {
        List<Term> termList = termService.termList();
        request.setAttribute("termList",termList);
        return "/teacher/student/add";
    }

    /**
     * 添加
     * @return
     */
    @PostMapping(value = "/teacher/student/add")
    public String add(User user,int termId,int teacherId) {
        studentService.addUser(user,termId,teacherId);
        return "redirect:/teacher/student/list";
    }

    /**
     * 删除学生
     * @param id
     * @return
     */
    @GetMapping(value = "/teacher/student/delete/{id}")
    public String deleteStudent(@PathVariable  Integer id) {
        studentService.deleteUser(id);
        return "redirect:/teacher/student/list";
    }
}
