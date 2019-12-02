package com.bjfu.jtap.teacher.service.impl;

import com.bjfu.jtap.entity.Term;
import com.bjfu.jtap.entity.TermExample;
import com.bjfu.jtap.mapper.TermMapper;
import com.bjfu.jtap.teacher.service.TermService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Description:
 * @Author: Tianyu Xiao
 * @CreateDate: 2019/1/28  0:43
 */
@Service
@Transactional
public class TermServiceImpl implements TermService{
    @Autowired
    private TermMapper termMapper;

    /**
     * 查询所有学期列表
     */

    public List<Term> termList() {
        List<Term> termList = termMapper.selectByExample(new TermExample());
        return termList;
    }

    /**
     * 添加一个学期
     */
    public void addTerm(Term term) {
        termMapper.insert(term);
    }

    /**
     * 根据id查询
     * @param id
     * @return
     */
    public Term selectTermById(int id) {
        Term term = termMapper.selectByPrimaryKey(id);
        return term;
    }

    /**
     * 修改学期信息
     * @param term
     */
    public void updateTerm(Term term) {
        termMapper.updateByPrimaryKey(term);
    }
}
