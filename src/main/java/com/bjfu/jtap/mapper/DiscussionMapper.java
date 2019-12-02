package com.bjfu.jtap.mapper;

import com.bjfu.jtap.entity.Discussion;
import com.bjfu.jtap.entity.DiscussionExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface DiscussionMapper {
    int countByExample(DiscussionExample example);

    int deleteByExample(DiscussionExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Discussion record);

    int insertSelective(Discussion record);

    List<Discussion> selectByExample(DiscussionExample example);

    Discussion selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Discussion record, @Param("example") DiscussionExample example);

    int updateByExample(@Param("record") Discussion record, @Param("example") DiscussionExample example);

    int updateByPrimaryKeySelective(Discussion record);

    int updateByPrimaryKey(Discussion record);
}