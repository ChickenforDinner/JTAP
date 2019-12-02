package com.bjfu.jtap.teacher.service.impl;

import com.bjfu.jtap.entity.Question;
import com.bjfu.jtap.entity.QuestionExample;
import com.bjfu.jtap.entity.QuestionWithBLOBs;
import com.bjfu.jtap.mapper.QuestionMapper;
import com.bjfu.jtap.teacher.service.QuestionService;
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
 * @CreateDate: 2019/1/28  23:33
 */
@Service
@Transactional
public class QuestionServiceImpl implements QuestionService{

    @Autowired
    private QuestionMapper questionMapper;

    /**
     * 添加题目
     * @param question
     */
    public void addQuestion(QuestionWithBLOBs question) {
        questionMapper.insert(question);
    }

    /**
     * 分页查询题库
     * @param page
     * @return
     */
    public PageResult<QuestionWithBLOBs> selectByPage(int page) {
        //开始分页,默认每页显示5条
        PageHelper.startPage(page,5);
        List<QuestionWithBLOBs> questionList = questionMapper.selectByExampleWithBLOBs(new QuestionExample());
        //将查询结果封装
        PageInfo<QuestionWithBLOBs> pageInfo = new PageInfo<>(questionList);
        PageResult<QuestionWithBLOBs> pageResult = new PageResult<>(pageInfo);

        return pageResult;
    }

    /**
     * 根据id查询
     * @param id
     * @return
     */
    public QuestionWithBLOBs selectQuestionById(Integer id) {
        return questionMapper.selectByPrimaryKey(id);
    }

    /**
     * 修改题目
     * @param question
     */
    public void updateQuestion(QuestionWithBLOBs question) {
        questionMapper.updateByPrimaryKeySelective(question);
    }


}
