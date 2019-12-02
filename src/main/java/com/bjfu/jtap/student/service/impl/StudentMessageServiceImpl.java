package com.bjfu.jtap.student.service.impl;

import com.bjfu.jtap.common.vo.MessageQueryVo;
import com.bjfu.jtap.entity.Message;
import com.bjfu.jtap.entity.MessageExample;
import com.bjfu.jtap.mapper.MessageMapper;
import com.bjfu.jtap.student.service.StudentMessageService;
import com.bjfu.jtap.utils.PageResult;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Description:
 * @Author: Tianyu Xiao
 * @CreateDate: 2019/4/24  10:14
 */
@Service
@Transactional
public class StudentMessageServiceImpl implements StudentMessageService {

    private static final int PAGE_SIZE = 10;

    @Autowired
    private MessageMapper messageMapper;

    /**
     * 条件分页查询
     * @param messageQueryVo
     * @return
     */
    public PageResult<Message> pageQueryList(MessageQueryVo messageQueryVo) {
        if(messageQueryVo.getPage() == null || messageQueryVo.getPage() <= 0) {
            messageQueryVo.setPage(1);
        }
        MessageExample example = new MessageExample();
        example.setOrderByClause(" is_look asc,time desc ");
        MessageExample.Criteria criteria = example.createCriteria();
        criteria.andReceiveIdEqualTo(messageQueryVo.getReceiveId());
        if(messageQueryVo.getIsLook() != null) {
            criteria.andIsLookEqualTo(messageQueryVo.getIsLook());
        }
        PageHelper.startPage(messageQueryVo.getPage(),PAGE_SIZE);
        List<Message> list = messageMapper.selectByExample(example);

        PageInfo<Message> pageInfo = new PageInfo<>(list);
        PageResult<Message> pageResult = new PageResult<>(pageInfo);
        return pageResult;
    }

    /**
     * 修改为已读
     * @param id
     */
    public void updateToRead(Integer id) {
        Message message = messageMapper.selectByPrimaryKey(id);
        message.setIsLook(1);
        messageMapper.updateByPrimaryKeySelective(message);
    }


}
