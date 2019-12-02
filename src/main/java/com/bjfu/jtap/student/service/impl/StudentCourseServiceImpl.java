package com.bjfu.jtap.student.service.impl;

import com.bjfu.jtap.entity.*;
import com.bjfu.jtap.mapper.*;
import com.bjfu.jtap.student.service.StudentCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;

/**
 * @Description:
 * @Author: Tianyu Xiao
 * @CreateDate: 2019/1/29  21:23
 */
@Service
@Transactional
public class StudentCourseServiceImpl implements StudentCourseService{
    @Autowired
    private UserTermMapper userTermMapper;
    @Autowired
    private CourseMapper courseMapper;
    @Autowired
    private CourseSourceMapper courseSourceMapper;
    @Autowired
    private SourceMapper sourceMapper;
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private CourseQuestionMapper courseQuestionMapper;

    //服务器路径前缀
    @Value("${FILE_SERVER_PATH}")
    private String FILE_SERVER_PATH;

    /**
     * 根据用户Id查询章节集合
     * @param userId
     * @return
     */
    public List<Course> courseList(int userId) {
        //如果用户未登陆，返回Null
        if(userId == 0) {
            return null;
        }
        //根据studentId查询用户-学期中间表
        UserTermExample userTermExample = new UserTermExample();
        UserTermExample.Criteria userTermExampleCriteria = userTermExample.createCriteria();
        userTermExampleCriteria.andStudentidEqualTo(userId);
        List<UserTerm> userTermList = userTermMapper.selectByExample(userTermExample);
        if(userTermList == null || userTermList.size() == 0) {
            return null;
        }
        UserTerm userTerm = userTermList.get(0);
        //根据学期id查询章节集合
        CourseExample courseExample = new CourseExample();
        CourseExample.Criteria courseExampleCriteria = courseExample.createCriteria();
        courseExampleCriteria.andTermidEqualTo(userTerm.getTermid());
        List<Course> courseList = courseMapper.selectByExample(courseExample);
        for(Course course : courseList) {
            //查询章节-资源中间表，遍历中间表集合，根据sourceid查询，对象封装
            CourseSourceExample courseSourceExample = new CourseSourceExample();
            CourseSourceExample.Criteria courseSourceExampleCriteria = courseSourceExample.createCriteria();
            courseSourceExampleCriteria.andCourseidEqualTo(course.getId());
            List<CourseSource> courseSourceList = courseSourceMapper.selectByExample(courseSourceExample);
            //将四种类型的数据封装
            //课件1，实验作业2，视频3，辅助材料4
            List<Source> sourceList1 = new LinkedList<>();
            List<Source> sourceList2 = new LinkedList<>();
            List<Source> sourceList3 = new LinkedList<>();
            List<Source> sourceList4 = new LinkedList<>();
            for(CourseSource cs : courseSourceList) {
                //查询资源信息
                Source source = sourceMapper.selectByPrimaryKey(cs.getSourceid());
                //设置资源路径
                source.setPath(FILE_SERVER_PATH + source.getPath());
                if(source.getType().trim().equals("1")) {
                    sourceList1.add(source);
                }
                if(source.getType().trim().equals("2")) {
                    sourceList2.add(source);
                }
                if(source.getType().trim().equals("3")) {
                    sourceList3.add(source);
                }
                if(source.getType().trim().equals("4")) {
                    sourceList4.add(source);
                }
            }//for
            course.setSource1List(sourceList1);
            course.setSource2List(sourceList2);
            course.setSource3List(sourceList3);
            course.setSource4List(sourceList4);
        }//for
        return courseList;
    }

    /**
     * 根据id查询
     * @param id
     * @return
     */
    public Source selectSourceById(int id) {
        Source source = sourceMapper.selectByPrimaryKey(id);
        source.setPath(FILE_SERVER_PATH + source.getPath());
        return source;
    }

    /**
     * 根据章节id查询题目集合
     * @param courseId
     * @return
     */
    public List<QuestionWithBLOBs> selectQuestionListByCourseId(int courseId) {
        //查找中间表
        CourseQuestionExample courseQuestionExample = new CourseQuestionExample();
        CourseQuestionExample.Criteria courseQuestionExampleCriteria = courseQuestionExample.createCriteria();
        courseQuestionExampleCriteria.andCourseidEqualTo(courseId);
        List<CourseQuestion> courseQuestionList = courseQuestionMapper.selectByExample(courseQuestionExample);
        //根据中间表题目id查询题目
        List<QuestionWithBLOBs> resultList = new LinkedList<>();
        for(CourseQuestion cq : courseQuestionList) {
            QuestionWithBLOBs questionWithBLOBs = questionMapper.selectByPrimaryKey(cq.getQuestionid());
            resultList.add(questionWithBLOBs);
        }
        return resultList;
    }


}
