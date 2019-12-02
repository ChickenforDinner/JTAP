package com.bjfu.jtap.student.controller;

import com.bjfu.jtap.entity.Discussion;
import com.bjfu.jtap.student.service.StudentDiscussionService;
import com.bjfu.jtap.utils.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Description:讨论区
 * @Author: Tianyu Xiao
 * @CreateDate: 2019/1/30  19:01
 */
@Controller
public class StudentDiscussionController {
    @Autowired
    private StudentDiscussionService studentDiscussionService;

    /**
     * 查询主题列表
     * @return
     */
    @RequestMapping(value = "/student/discussion/topicList")
    public String topicList(HttpServletRequest request,Integer page) {
        //如果当前页不存在,置为1
        if(page == null || page <= 0)   page = 1;
        PageResult<Discussion> pageResult = studentDiscussionService.topicList(page);
        request.setAttribute("pageResult",pageResult);
        return "/student/discussion/List-topic";
    }

    /**
     * 进入帖子
     * @param request
     * @return
     */
    @RequestMapping(value = "/student/discussion/discussionList/{discussionId}")
    public String discussionList(HttpServletRequest request, @PathVariable int discussionId) {
        //查询主题
        Discussion discussion = studentDiscussionService.selectById(discussionId);
        //查询子帖子
        List<Discussion> discussionList = studentDiscussionService.selectDiscussionListByTopicId(discussionId);
        request.setAttribute("discussion",discussion);
        request.setAttribute("discussionList",discussionList);
        return "/student/discussion/List-discussion";
    }

    /**
     * 去编辑讨论页面
     * @return
     */
    @RequestMapping(value = "/student/discussion/toEdit")
    public String toEdit() {
        return "/student/discussion/edit-discussion";
    }

    /**
     * 发布主题
     */
    @PostMapping(value = "/student/discussion/publish")
    public String publicTopic(Discussion discussion) {
        if(discussion.getPublishId() == 0) {
            return null;//未登录
        }
        studentDiscussionService.publicTopic(discussion);
        return "redirect:/student/discussion/topicList";
    }

    /**
     * 回复帖子
     * @return
     */
    @PostMapping(value = "/student/discussion/reply")
    public String replyToTopic(Discussion discussion) {
        if(discussion.getPublishId() == 0) {
            return null;//用户未登陆
        }
        studentDiscussionService.insertReply(discussion);
        return "redirect:/student/discussion/discussionList/"+discussion.getTopicId();
    }
}
