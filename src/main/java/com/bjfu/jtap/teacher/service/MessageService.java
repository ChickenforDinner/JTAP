package com.bjfu.jtap.teacher.service;

import com.bjfu.jtap.entity.Message;
import com.bjfu.jtap.utils.PageResult;

/**
 * @Description:消息发布/接受
 * @Author: Tianyu Xiao
 * @CreateDate: 2019/4/23  21:57
 */
public interface MessageService {

    /**
     * 发布消息
     * @param receiveIds
     * @param publishId
     * @param content
     */
    void addMessage(Integer []receiveIds,Integer publishId,String content);

    /**
     * 分页查询发布消息
     * @param publishId
     * @return
     */
    PageResult<Message> messagePageQueryList(Integer publishId,Integer page);

    /**
     * 根据messageId删除
     * @param messageId
     */
    void deleteMessageByMessageId(String messageId);
}
