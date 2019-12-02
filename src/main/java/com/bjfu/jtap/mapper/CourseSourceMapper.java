package com.bjfu.jtap.mapper;

import com.bjfu.jtap.entity.CourseSource;
import com.bjfu.jtap.entity.CourseSourceExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CourseSourceMapper {
    int countByExample(CourseSourceExample example);

    int deleteByExample(CourseSourceExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CourseSource record);

    int insertSelective(CourseSource record);

    List<CourseSource> selectByExample(CourseSourceExample example);

    CourseSource selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CourseSource record, @Param("example") CourseSourceExample example);

    int updateByExample(@Param("record") CourseSource record, @Param("example") CourseSourceExample example);

    int updateByPrimaryKeySelective(CourseSource record);

    int updateByPrimaryKey(CourseSource record);
}