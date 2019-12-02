package com.bjfu.jtap.mapper;

import com.bjfu.jtap.entity.Term;
import com.bjfu.jtap.entity.TermExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TermMapper {
    int countByExample(TermExample example);

    int deleteByExample(TermExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Term record);

    int insertSelective(Term record);

    List<Term> selectByExample(TermExample example);

    Term selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Term record, @Param("example") TermExample example);

    int updateByExample(@Param("record") Term record, @Param("example") TermExample example);

    int updateByPrimaryKeySelective(Term record);

    int updateByPrimaryKey(Term record);
}