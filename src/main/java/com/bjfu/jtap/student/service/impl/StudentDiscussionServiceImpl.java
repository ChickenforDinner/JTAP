package com.bjfu.jtap.student.service.impl;

import com.bjfu.jtap.entity.Discussion;
import com.bjfu.jtap.entity.DiscussionExample;
import com.bjfu.jtap.entity.User;
import com.bjfu.jtap.mapper.DiscussionMapper;
import com.bjfu.jtap.mapper.UserMapper;
import com.bjfu.jtap.student.service.StudentDiscussionService;
import com.bjfu.jtap.utils.PageResult;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @Description:
 * @Author: Tianyu Xiao
 * @CreateDate: 2019/1/30  20:21
 */
@Service
@Transactional
public class StudentDiscussionServiceImpl implements StudentDiscussionService{
    @Autowired
    private DiscussionMapper discussionMapper;
    @Autowired
    private UserMapper userMapper;
    @Value("${FILE_SERVER_PATH}")
    private String FILE_SERVER_PATH;

    /**
     * 分页查询主题
     * @return
     */
    public PageResult<Discussion> topicList(int page) {
        //开始分页
        PageHelper.startPage(page,10);
        //如果topicId==0,为主题,根据时间排序
        DiscussionExample discussionExample = new DiscussionExample();
        //时间降序
        discussionExample.setOrderByClause(" time DESC ");
        DiscussionExample.Criteria discussionExampleCriteria = discussionExample.createCriteria();
        discussionExampleCriteria.andTopicIdEqualTo(0);

        //结果集
        List<Discussion> discussionList = discussionMapper.selectByExample(discussionExample);
        //将作者对象封装
        for(Discussion discussion : discussionList) {
            User user = userMapper.selectByPrimaryKey(discussion.getPublishId());
            user.setImage(FILE_SERVER_PATH + user.getImage());
            discussion.setAuthor(user);
        }
        //将分页信息包装
        PageInfo<Discussion> pageInfo = new PageInfo<>(discussionList);
        PageResult<Discussion> pageResult = new PageResult<>(pageInfo);
        return pageResult;
    }

    /**
     * 发布主题
     * @param discussion
     */
    public void publicTopic(Discussion discussion) {
        //设置帖子时间
        Date currentTime = new Date();
        discussion.setTime(currentTime);
        //教师是否参与,首次发布帖子默认不参与
        discussion.setTeacherJoin(0);
        //首次发帖,是主题帖:0
        discussion.setTopicId(0);
        //插入数据
        discussionMapper.insertSelective(discussion);
    }

    /**
     * 根据id查询
     * @param id
     * @return
     */
    public Discussion selectById(int id) {
        Discussion discussion = discussionMapper.selectByPrimaryKey(id);
        //查询用户
        User user = userMapper.selectByPrimaryKey(discussion.getPublishId());
        user.setImage(FILE_SERVER_PATH + user.getImage());
        discussion.setAuthor(user);
        return discussion;
    }

    /**
     * 保存回复信息
     * @param discussion
     */
    public void insertReply(Discussion discussion) {
        //设置时间
        discussion.setTime(new Date());
        //默认老师不参与
        discussion.setTeacherJoin(0);
        discussionMapper.insertSelective(discussion);
    }

    /**
     * 根据topicId查询帖子集合
     * @param topicId
     * @return
     */
    public List<Discussion> selectDiscussionListByTopicId(int topicId) {
        DiscussionExample discussionExample = new DiscussionExample();
        DiscussionExample.Criteria discussionExampleCriteria = discussionExample.createCriteria();
        discussionExampleCriteria.andTopicIdEqualTo(topicId);
        List<Discussion> discussionList = discussionMapper.selectByExample(discussionExample);
        if(discussionList == null || discussionList.size() == 0) {
            return null;
        }
        //遍历集合,将用户信息封装
        for(Discussion discussion : discussionList) {
            User user = userMapper.selectByPrimaryKey(discussion.getPublishId());
            user.setImage(FILE_SERVER_PATH + user.getImage());
            discussion.setAuthor(user);
        }
        return discussionList;
    }
}
