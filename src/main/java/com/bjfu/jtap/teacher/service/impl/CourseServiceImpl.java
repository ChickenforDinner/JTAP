package com.bjfu.jtap.teacher.service.impl;

import com.bjfu.jtap.entity.*;
import com.bjfu.jtap.mapper.*;
import com.bjfu.jtap.teacher.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

/**
 * @Description:
 * @Author: Tianyu Xiao
 * @CreateDate: 2019/1/28  20:50
 */
@Service
@Transactional
public class CourseServiceImpl implements CourseService{
    @Autowired
    private CourseMapper courseMapper;
    @Autowired
    private CourseQuestionMapper courseQuestionMapper;
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private SourceMapper sourceMapper;
    @Autowired
    private CourseSourceMapper courseSourceMapper;
    //文件上传路径
    @Value("${FILE_LOCAL_PATH}")
    private String FILE_PATH;
    //文件服务器路径前缀
    @Value("${FILE_SERVER_PATH}")
    private String FILE_SERVER_PATH;

    /**
     * 添加课程章节信息
     * @param course
     */
    public void addCourse(Course course) {
        courseMapper.insert(course);
    }

    /**
     * 根据学期id查询所有课程列表
     * @param termId
     * @return
     */
    public List<Course> courseList(Integer termId) {
        if(termId == null || termId == 0) termId=1;//TODO return null;
        CourseExample courseExample = new CourseExample();
        CourseExample.Criteria courseExampleCriteria = courseExample.createCriteria();
        courseExampleCriteria.andTermidEqualTo(termId);
        List<Course> courseList = courseMapper.selectByExample(courseExample);
        return courseList;
    }

    /**
     * 根据id查询
     * @param courseId
     * @return
     */
    public Course selectByCourseId(int courseId) {
        Course course = courseMapper.selectByPrimaryKey(courseId);
        return course;
    }

    /**
     * 修改course信息
     * @param course
     */
    public void updateCourse(Course course) {
        courseMapper.updateByPrimaryKeySelective(course);
    }

    /**
     * 为章节添加习题
     * @param courseId:章节id
     * @param qIds:题目Id数组
     */
    public void addCourseQuestion(int courseId, int[] qIds) {
        if(qIds == null || qIds.length == 0) {
            return ;
        }
        //插入数据库
        for(int id : qIds) {
            CourseQuestion cq = new CourseQuestion();
            cq.setCourseid(courseId);
            cq.setQuestionid(id);
            courseQuestionMapper.insert(cq);
        }
    }

    /**
     * 根据章节id查询题目
     * @param courseId
     * @return
     */
    public List<QuestionWithBLOBs> selectQuestionByCourseId(int courseId) {
        CourseQuestionExample courseQuestionExample = new CourseQuestionExample();
        CourseQuestionExample.Criteria courseQuestionExampleCriteria = courseQuestionExample.createCriteria();
        //根据courseId查询中间表
        courseQuestionExampleCriteria.andCourseidEqualTo(courseId);
        List<CourseQuestion> courseQuestionList = courseQuestionMapper.selectByExample(courseQuestionExample);
        if(courseQuestionList == null || courseQuestionList.size() == 0) return null;
        //遍历查询中间表集合
        //根据questionId查询题库信息，将对象封装到集合中
        List<QuestionWithBLOBs> resultList = new LinkedList<>();
        for(CourseQuestion cq : courseQuestionList) {
            QuestionWithBLOBs questionWithBLOBs = questionMapper.selectByPrimaryKey(cq.getQuestionid());
            resultList.add(questionWithBLOBs);
        }
        return resultList;
    }

    /**
     * 根据章节id查询资源文件
     * @param courseId
     * @return
     */
    public List<Source> selectSourceByCourseId(int courseId) {
        //根据courseid查询章节-资源中间表
        CourseSourceExample curseSourceExample = new CourseSourceExample();
        CourseSourceExample.Criteria curseSourceExampleCriteria = curseSourceExample.createCriteria();
        curseSourceExampleCriteria.andCourseidEqualTo(courseId);
        List<CourseSource> courseSourceList = courseSourceMapper.selectByExample(curseSourceExample);
        if(courseSourceList == null || courseSourceList.size() == 0) return null;
        //将结果封装返回
        List<Source> resultList = new LinkedList<>();
        for(CourseSource cs : courseSourceList) {
            //查询资源表，根据id查询
            Source source = sourceMapper.selectByPrimaryKey(cs.getSourceid());
            source.setPath(FILE_SERVER_PATH + source.getPath());
            resultList.add(source);
        }
        return resultList;
    }

    /**
     * 资源文件上传
     * @param file
     * @param source
     */
    public void addSource(MultipartFile file, Source source,int courseId) {
        //设置文件名称
        String fileName = file.getOriginalFilename();
        source.setName(fileName);
        //获取文件扩展名
        String extName = fileName.substring(fileName.lastIndexOf("."));
        //设置服务器中的文件名称
        String serverFileName = UUID.randomUUID().toString() + extName;
       // String path = "E:\\fileDir\\";
        //上传
        try {
            file.transferTo(new File(FILE_PATH + serverFileName));
            //路径配置到数据库
            source.setPath(serverFileName);
            sourceMapper.insert(source);
            CourseSource courseSource = new CourseSource();
            courseSource.setCourseid(courseId);
            courseSource.setSourceid(source.getId());
            courseSourceMapper.insert(courseSource);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 根据单元和题目Id删除
     * @param courseId
     * @param questionId
     */
    public void deleteQuestionByCourseAndQuestion(Integer courseId, Integer questionId) {

        CourseQuestionExample example = new CourseQuestionExample();
        CourseQuestionExample.Criteria criteria = example.createCriteria();
        criteria.andCourseidEqualTo(courseId);
        criteria.andQuestionidEqualTo(questionId);
        courseQuestionMapper.deleteByExample(example);

    }

    /**
     * 根据单元和资源id删除
     * @param courseId
     * @param sourceId
     */
    public void deleteSourceByCourseAndSource(Integer courseId, Integer sourceId) {
        CourseSourceExample example = new CourseSourceExample();
        CourseSourceExample.Criteria criteria = example.createCriteria();
        criteria.andCourseidEqualTo(courseId);
        criteria.andSourceidEqualTo(sourceId);
        courseSourceMapper.deleteByExample(example);
    }
}
