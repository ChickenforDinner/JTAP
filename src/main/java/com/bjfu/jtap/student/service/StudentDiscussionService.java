package com.bjfu.jtap.student.service;

import com.bjfu.jtap.entity.Discussion;
import com.bjfu.jtap.utils.PageResult;

import java.util.List;

/**
 * @Description:讨论区业务层接口
 * @Author: Tianyu Xiao
 * @CreateDate: 2019/1/30  20:19
 */
public interface StudentDiscussionService {

    /**
     * 分页查询主题
     * @return
     */
    PageResult<Discussion> topicList(int page);

    /**
     * 发布主题
     * @param discussion
     */
    void publicTopic(Discussion discussion);

    /**
     * 根据Id查询
     * @param id
     * @return
     */
    Discussion selectById(int id);

    /**
     * 保存回复信息
     * @param discussion
     */
    void insertReply(Discussion discussion);

    /**
     * 根据topicId查询帖子集合
     * @param topicId
     * @return
     */
    List<Discussion> selectDiscussionListByTopicId(int topicId);
}
