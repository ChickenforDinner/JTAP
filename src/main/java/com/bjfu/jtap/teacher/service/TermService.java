package com.bjfu.jtap.teacher.service;

import com.bjfu.jtap.entity.Term;

import java.util.List;

/**
 * @Description:学期业务接口
 * @Author: Tianyu Xiao
 * @CreateDate: 2019/1/28  0:40
 */
public interface TermService {
    /**
     * 查询所有学期列表
     * @return
     */
    List<Term> termList();

    /**
     * 添加一个学期
     */
    void addTerm(Term term);

    /**
     * 根据id查询
     * @param id
     * @return
     */
    Term selectTermById(int id);

    /**
     * 修改学期信息
     * @param term
     */
    void updateTerm(Term term);
}
