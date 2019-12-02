package com.bjfu.jtap.mapper;

import com.bjfu.jtap.entity.HomeworkStudent;
import com.bjfu.jtap.entity.HomeworkStudentExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface HomeworkStudentMapper {
    int countByExample(HomeworkStudentExample example);

    int deleteByExample(HomeworkStudentExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(HomeworkStudent record);

    int insertSelective(HomeworkStudent record);

    List<HomeworkStudent> selectByExample(HomeworkStudentExample example);

    HomeworkStudent selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") HomeworkStudent record, @Param("example") HomeworkStudentExample example);

    int updateByExample(@Param("record") HomeworkStudent record, @Param("example") HomeworkStudentExample example);

    int updateByPrimaryKeySelective(HomeworkStudent record);

    int updateByPrimaryKey(HomeworkStudent record);
}