package com.bjfu.jtap.student.controller;

import com.bjfu.jtap.common.vo.MessageQueryVo;
import com.bjfu.jtap.entity.Message;
import com.bjfu.jtap.entity.User;
import com.bjfu.jtap.student.service.StudentMessageService;
import com.bjfu.jtap.utils.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * @Description:
 * @Author: Tianyu Xiao
 * @CreateDate: 2019/4/24  10:03
 */
@Controller
public class StudentMessageController {

    @Autowired
    private StudentMessageService studentMessageService;

    /**
     * 分页条件查询消息
     * @param messageQueryVo
     * @param request
     * @return
     */
    @RequestMapping(value = "/student/message/list")
    public String pageQueryMessage(MessageQueryVo messageQueryVo, HttpServletRequest request) {
        //获取当前用户
        User student = (User)request.getSession().getAttribute("user");
        Integer id = student.getId();
        messageQueryVo.setReceiveId(id);
        PageResult<Message> pageResult = studentMessageService.pageQueryList(messageQueryVo);
        request.setAttribute("pageResult",pageResult);
        return "student/message/message-list";
    }

    /**
     * 修改消息状态为已读
     * @param id
     * @return
     */
    @RequestMapping(value = "/student/message/read/{id}")
    public String updateMessageToRead(@PathVariable Integer id,Integer page) {
        studentMessageService.updateToRead(id);
        if(page == null) {
            return "redirect:/student/message/list";
        }else {
            return "redirect:/student/message/list?page="+page;
        }

    }
}
