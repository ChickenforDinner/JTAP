package com.bjfu.jtap.teacher.service.impl;

import com.bjfu.jtap.entity.Message;
import com.bjfu.jtap.entity.MessageExample;
import com.bjfu.jtap.mapper.MessageCustomerMapper;
import com.bjfu.jtap.mapper.MessageMapper;
import com.bjfu.jtap.teacher.service.MessageService;
import com.bjfu.jtap.utils.PageResult;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

/**
 * @Description:
 * @Author: Tianyu Xiao
 * @CreateDate: 2019/4/23  22:03
 */
@Service
@Transactional
public class MessageServiceImpl implements MessageService{
    @Autowired
    private MessageMapper messageMapper;
    @Autowired
    private MessageCustomerMapper messageCustomerMapper;

    private static final int PAGE_SIZE = 10;

    /**
     * 添加消息
     * @param receiveIds
     * @param publishId
     * @param content
     */
    public void addMessage(Integer[] receiveIds, Integer publishId, String content) {
        String messageUUID = UUID.randomUUID().toString().replace("-","");
        Date time = new Date();
        for(Integer receiveId : receiveIds) {
            Message message = new Message();
            message.setMessageId(messageUUID);
            message.setTime(time);
            message.setPublishId(publishId);
            message.setReceiveId(receiveId);
            message.setContent(content);
            message.setIsLook(0);
            messageMapper.insert(message);
        }

    }



    /**
     * 分页查询
     * @param publishId
     * @return
     */
    public PageResult<Message> messagePageQueryList(Integer publishId,Integer page) {
        if(page == null || page <= 0) {
            page = 1;
        }
        PageHelper.startPage(page,PAGE_SIZE);
        List<String> messageIdList = messageCustomerMapper.selectDistinctMessageId(publishId);

        PageInfo<String> pageInfo = new PageInfo<>(messageIdList);
        PageResult pageResult = new PageResult<>(pageInfo);

        List<Message> resultList = new LinkedList<>();

        for(Object messageId : pageResult.getList()) {
            MessageExample messageExample = new MessageExample();
            messageExample.createCriteria().andMessageIdEqualTo(messageId+"");
            List<Message> messageList = messageMapper.selectByExample(messageExample);
            if(messageList == null || messageList.size() == 0) {
                resultList.add(new Message());
            }else {
                Message message = messageList.get(0);
                message.setReceiveId(null);
                resultList.add(message);
            }
        }
        pageResult.setList(resultList);

        return pageResult;
    }

    /**
     * 根据messageId删除
     * @param messageId
     */
    public void deleteMessageByMessageId(String messageId) {
        MessageExample example = new MessageExample();
        example.createCriteria().andMessageIdEqualTo(messageId);
        messageMapper.deleteByExample(example);
    }
}
