package com.bjfu.jtap.teacher.service;

import com.bjfu.jtap.entity.Question;
import com.bjfu.jtap.entity.QuestionWithBLOBs;
import com.bjfu.jtap.utils.PageResult;

import java.util.List;

/**
 * @Description:题库管理业务层接口
 * @Author: Tianyu Xiao
 * @CreateDate: 2019/1/28  23:33
 */
public interface QuestionService {

    /**
     * 添加题目
     * @param question
     */
    void addQuestion(QuestionWithBLOBs question);

    /**
     * 分页查询题库
     * @param page
     * @return
     */
    PageResult<QuestionWithBLOBs> selectByPage(int page);

    /**
     * 根据id查询
     * @param id
     * @return
     */
    QuestionWithBLOBs selectQuestionById(Integer id);

    /**
     * 修改题目
     * @param question
     */
    void updateQuestion(QuestionWithBLOBs question);
}
