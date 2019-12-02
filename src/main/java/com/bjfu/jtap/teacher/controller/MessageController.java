package com.bjfu.jtap.teacher.controller;

import com.bjfu.jtap.entity.Message;
import com.bjfu.jtap.entity.Term;
import com.bjfu.jtap.entity.User;
import com.bjfu.jtap.teacher.service.MessageService;
import com.bjfu.jtap.teacher.service.TermService;
import com.bjfu.jtap.utils.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Description:
 * @Author: Tianyu Xiao
 * @CreateDate: 2019/4/23  20:59
 */
@Controller
public class MessageController {
    @Autowired
    private TermService termService;
    @Autowired
    private MessageService messageService;


    /**
     * 去添加消息页面
     * @return
     */
    @GetMapping(value = "/teacher/message/add")
    public String toAddMessage(HttpServletRequest request) {
        //查询所有的学期
        List<Term> termList = termService.termList();
        request.setAttribute("termList",termList);

        return "teacher/message/message-add";
    }

    /**
     * 添加消息
     * @param receiveIds
     * @param content
     * @return
     */
    @PostMapping(value = "/teacher/message/add")
    public String addMessage(Integer []receiveIds,String content,HttpServletRequest request) {
        if(receiveIds == null || receiveIds.length == 0) {
            //查询所有的学期
            List<Term> termList = termService.termList();
            request.setAttribute("termList",termList);
            request.setAttribute("msg","请至少选择一个学生！");
            return "teacher/message/message-add";
        }
        User teacher = (User)request.getSession().getAttribute("user");
        Integer publishId = teacher.getId();
        messageService.addMessage(receiveIds,publishId,content);
        return "redirect:/teacher/message/list";
    }

    /**
     * 分页查询消息
     * @return
     */
    @GetMapping(value = "/teacher/message/list")
    public String messagePageQueryList(HttpServletRequest request,Integer page) {
        User teacher = (User)request.getSession().getAttribute("user");
        Integer publishId = teacher.getId();
        PageResult<Message> pageResult = messageService.messagePageQueryList(publishId,page);
        request.setAttribute("pageResult",pageResult);
        return "teacher/message/message-list";
    }

    /**
     * 删除消息
     * @param messageId
     * @return
     */
    @GetMapping(value = "/teacher/message/delete/{messageId}")
    public String deleteMessages(@PathVariable String messageId) {
        messageService.deleteMessageByMessageId(messageId);
        return "redirect:/teacher/message/list";
    }
}
