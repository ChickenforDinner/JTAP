package com.bjfu.jtap.teacher.controller;

import com.bjfu.jtap.entity.Term;
import com.bjfu.jtap.teacher.service.TermService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @Description:
 * @Author: Tianyu Xiao
 * @CreateDate: 2019/1/28  0:45
 */
@Controller
public class TermController {
    @Autowired
    private TermService termService;

    /**
     * 学期列表
     * @param request
     * @return
     */
    @RequestMapping(value = "/teacher/term/list")
    public String termList(HttpServletRequest request) {
        List<Term> termList = termService.termList();
        request.setAttribute("termList",termList);
        return "/teacher/term/List";
    }

    /**
     * 去添加页面
     */
    @RequestMapping(value = "/teacher/term/toAdd")
    public String toAdd() {
        return "/teacher/term/add";
    }

    /**
     * 添加学期
     */
    @PostMapping(value = "/teacher/term/add")
    public String add(String name,String begintime,String endtime) throws ParseException {
        //转换前端数据，将数据封装
        Term term = new Term();
        term.setName(name);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        term.setBegintime(sdf.parse(begintime));
        term.setEndtime(sdf.parse(endtime));
        termService.addTerm(term);
        return "redirect:/teacher/term/list";
    }

    /**
     * 去添加页面
     * @return
     */
    @GetMapping(value = "/teacher/term/toEdit/{id}")
    public String toEdit(@PathVariable int id,HttpServletRequest request) {
        Term term = termService.selectTermById(id);
        request.setAttribute("term",term);
        return "/teacher/term/edit";
    }

    /**
     * 修改学期信息
     * @param name
     * @param begintime
     * @param endtime
     * @return
     * @throws ParseException
     */
    @PostMapping(value = "/teacher/term/edit")
    public String edit(int id,String name,String begintime,String endtime) throws ParseException {
        //转换前端数据，将数据封装
        Term term = new Term();
        term.setName(name);
        term.setId(id);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        term.setBegintime(sdf.parse(begintime));
        term.setEndtime(sdf.parse(endtime));
        term.setIsdelete(0);
        termService.updateTerm(term);
        return "redirect:/teacher/term/list";
    }

}
