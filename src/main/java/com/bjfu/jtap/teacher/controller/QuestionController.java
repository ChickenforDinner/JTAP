package com.bjfu.jtap.teacher.controller;

import com.bjfu.jtap.entity.Question;
import com.bjfu.jtap.entity.QuestionWithBLOBs;
import com.bjfu.jtap.teacher.service.QuestionService;
import com.bjfu.jtap.utils.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * @Description:
 * @Author: Tianyu Xiao
 * @CreateDate: 2019/1/28  23:27
 */
@Controller
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    /**
     * 去添加页面
     * @return
     */
    @RequestMapping(value = "/teacher/question/toAdd")
    public String toAdd() {
        return "teacher/question/add";
    }

    /**
     * 添加题目
     * @return
     */
    @PostMapping(value = "/teacher/question/add")
    public String add(QuestionWithBLOBs question) {
        questionService.addQuestion(question);
        return "redirect:/teacher/question/list";
    }

    /**
     * 分页查询题库集合
     */
    @RequestMapping(value = "/teacher/question/list")
    public String list(HttpServletRequest request,Integer page) {
        if(page == null || page <=0) page = 1;
        PageResult<QuestionWithBLOBs> pageResult = questionService.selectByPage(page);
        request.setAttribute("pageResult",pageResult);
        return "teacher/question/List";
    }

    /**
     * 去编辑页面
     * @param id
     * @return
     */
    @GetMapping(value = "/teacher/question/toEdit/{id}")
    public String toEditQuestion(@PathVariable Integer id,HttpServletRequest request) {
        QuestionWithBLOBs question = questionService.selectQuestionById(id);
        request.setAttribute("question",question);
        return "teacher/question/edit";
    }

    /**
     * 编辑题目
     * @param questionWithBLOBs
     * @return
     */
    @PostMapping(value = "/teacher/question/edit")
    public String editQuestion(QuestionWithBLOBs questionWithBLOBs) {
        questionService.updateQuestion(questionWithBLOBs);
        return "redirect:/teacher/question/list";
    }

}
