package com.bjfu.jtap.teacher.controller;

import com.bjfu.jtap.entity.Course;
import com.bjfu.jtap.entity.QuestionWithBLOBs;
import com.bjfu.jtap.entity.Source;
import com.bjfu.jtap.entity.Term;
import com.bjfu.jtap.teacher.service.CourseService;
import com.bjfu.jtap.teacher.service.QuestionService;
import com.bjfu.jtap.teacher.service.TermService;
import com.bjfu.jtap.utils.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Description:课程章节管理表现层
 * @Author: Tianyu Xiao
 * @CreateDate: 2019/1/28  20:46
 */
@Controller
public class CourseController {
    @Autowired
    private TermService termService;
    @Autowired
    private CourseService courseService;
    @Autowired
    private QuestionService questionService;

    /**
     * 去添加页面
     * @return
     */
    @RequestMapping(value = "/teacher/course/toAdd")
    public String toAdd(HttpServletRequest request) {
        List<Term> termList = termService.termList();
        request.setAttribute("termList",termList);
        return "/teacher/course/add";
    }

    /**
     * 添加
     * @param request
     * @return
     */
    @PostMapping(value = "/teacher/course/add")
    public String add(HttpServletRequest request,Course course) {
        try{
            courseService.addCourse(course);
            request.setAttribute("msg","添加成功!");
        }catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("msg","添加失败!");
        }
        List<Term> termList = termService.termList();
        request.setAttribute("termList",termList);
        return "/teacher/course/add";
    }

    /**
     * 课程章节列表
     * @param request
     * @param termid
     * @return
     */
    @RequestMapping(value = "/teacher/course/list")
    public String list(HttpServletRequest request,Integer termid) {
        List<Term> termList = termService.termList();
        request.setAttribute("termList",termList);
        //条件查询
        List<Course> courseList = courseService.courseList(termid);
        request.setAttribute("courseList",courseList);
        return "/teacher/course/List";
    }

    /**
     * 课程章节列表:添加资源/题库
     * @param request
     * @param termid
     * @return
     */
    @RequestMapping(value = "/teacher/course/list-source")
    public String listForSource(HttpServletRequest request,Integer termid) {
        List<Term> termList = termService.termList();
        request.setAttribute("termList",termList);
        //条件查询
        List<Course> courseList = courseService.courseList(termid);
        request.setAttribute("courseList",courseList);
        return "/teacher/course/List-source";
    }

    /**
     * 去编辑页面
     * @param request
     * @param id
     * @return
     */
    @RequestMapping(value = "/teacher/course/toEdit/{id}")
    public String toEdit(HttpServletRequest request, @PathVariable int id) {
        Course course = courseService.selectByCourseId(id);
        request.setAttribute("course",course);
        return "/teacher/course/edit";
    }

    /**
     * 编辑课程章节信息
     * @param course
     * @return
     */
    @PostMapping(value = "/teacher/course/edit")
    public String edit(Course course) {
        courseService.updateCourse(course);
        return "redirect:/teacher/course/list";
    }

    /**
     * 去添加题库页面
     * @param request
     * @param page
     * @return
     */
    @RequestMapping(value = "/teacher/course/toAddQuestion")
    public String toAddQuestion(HttpServletRequest request,Integer page,Integer courseId) {
        //根据id查询
        Course course = courseService.selectByCourseId(courseId);
        request.setAttribute("course",course);
        //处理分页时courseId消失
        request.setAttribute("courseId",courseId);
        if(page == null || page <=0) page = 1;
        //将当前页放置request域
        request.setAttribute("page",page);
        PageResult<QuestionWithBLOBs> pageResult = questionService.selectByPage(page);
        request.setAttribute("pageResult",pageResult);
        return "/teacher/question/List-course";
    }

    /**
     * 给章节添加习题
     * @param qIds
     * @param courseId
     * @param page
     * @return
     */
    @PostMapping(value = "/teacher/course/addQuestion")
    public String addQuestion(int[] qIds,int courseId,int page) {
        courseService.addCourseQuestion(courseId,qIds);
        return "redirect:/teacher/course/toAddQuestion?page="+page+"&courseId="+courseId;
    }

    /**
     * 查询某章节的习题
     * @param request
     * @param courseId
     * @return
     */
    @RequestMapping(value = "/teacher/course/courseQuestion/{courseId}")
    public String courseQuestionList(HttpServletRequest request,@PathVariable int courseId) {
        //页面章节信息回显
        Course course = courseService.selectByCourseId(courseId);
        request.setAttribute("course",course);
        //查询所有习题信息
        List<QuestionWithBLOBs> questionList = courseService.selectQuestionByCourseId(courseId);
        request.setAttribute("questionList",questionList);
        //查询所有课件资源信息
        List<Source> sourceList = courseService.selectSourceByCourseId(courseId);
        request.setAttribute("sourceList",sourceList);
        return "/teacher/course/List-question";
    }

    /**
     * 删除某一单元的某个习题
     * @return
     */
    @GetMapping(value = "/teacher/course/courseQuestion/delete/{courseId}/{questionId}")
    public String courseQuestionDelete(@PathVariable Integer courseId,@PathVariable Integer questionId) {
        //System.out.println("单元id:" + courseId);
        //System.out.println("题目id:" + questionId);
        courseService.deleteQuestionByCourseAndQuestion(courseId,questionId);
        return "redirect:/teacher/course/courseQuestion/"+courseId;
    }

    /**
     * 删除某一单元的某个资源
     * @return
     */
    @GetMapping(value = "/teacher/course/courseSource/delete/{courseId}/{sourceId}")
    public String courseSourceDelete(@PathVariable Integer courseId,@PathVariable Integer sourceId) {
        //System.out.println("单元id:" + courseId);
        //System.out.println("资源id:" + sourceId);
        courseService.deleteSourceByCourseAndSource(courseId,sourceId);
        return "redirect:/teacher/course/courseQuestion/"+courseId;
    }

    /**
     * 去添加文件页面
     * @return
     */
    @RequestMapping(value = "/teacher/course/toAddSource/{courseId}")
    public String toAddSource(HttpServletRequest request,@PathVariable int courseId) {
        //根据id查询
        Course course = courseService.selectByCourseId(courseId);
        request.setAttribute("course",course);
        return "/teacher/course/add-file";
    }

    /**
     *资源文件上传
     * @param file
     * @return
     */
    @PostMapping(value = "/teacher/course/addSource")
    public String addSource(MultipartFile file, Source source,HttpServletRequest request,Integer courseId) {
        List<Term> termList = termService.termList();
        request.setAttribute("termList",termList);
        //调用service接口上传文件
        courseService.addSource(file,source,courseId);
        return "redirect:/teacher/course/list-source";
    }
}
