package com.bjfu.jtap.mapper;

import com.bjfu.jtap.entity.CourseQuestion;
import com.bjfu.jtap.entity.CourseQuestionExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CourseQuestionMapper {
    int countByExample(CourseQuestionExample example);

    int deleteByExample(CourseQuestionExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CourseQuestion record);

    int insertSelective(CourseQuestion record);

    List<CourseQuestion> selectByExample(CourseQuestionExample example);

    CourseQuestion selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CourseQuestion record, @Param("example") CourseQuestionExample example);

    int updateByExample(@Param("record") CourseQuestion record, @Param("example") CourseQuestionExample example);

    int updateByPrimaryKeySelective(CourseQuestion record);

    int updateByPrimaryKey(CourseQuestion record);
}