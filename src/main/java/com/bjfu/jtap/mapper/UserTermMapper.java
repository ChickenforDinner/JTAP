package com.bjfu.jtap.mapper;

import com.bjfu.jtap.entity.UserTerm;
import com.bjfu.jtap.entity.UserTermExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserTermMapper {
    int countByExample(UserTermExample example);

    int deleteByExample(UserTermExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(UserTerm record);

    int insertSelective(UserTerm record);

    List<UserTerm> selectByExample(UserTermExample example);

    UserTerm selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") UserTerm record, @Param("example") UserTermExample example);

    int updateByExample(@Param("record") UserTerm record, @Param("example") UserTermExample example);

    int updateByPrimaryKeySelective(UserTerm record);

    int updateByPrimaryKey(UserTerm record);
}