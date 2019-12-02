package com.bjfu.jtap.entity;

import java.util.List;

public class Course {
    /**
     * 添加实体属性
     * 课件1，实验作业2，视频3，辅助材料4
     */
    private List<Source> source1List;
    private List<Source> source2List;
    private List<Source> source3List;
    private List<Source> source4List;
    private List<QuestionWithBLOBs> questionList;

    private Integer id;

    private String name;

    private Integer unit;

    private Integer termid;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getUnit() {
        return unit;
    }

    public void setUnit(Integer unit) {
        this.unit = unit;
    }

    public Integer getTermid() {
        return termid;
    }

    public void setTermid(Integer termid) {
        this.termid = termid;
    }

    public List<Source> getSource1List() {
        return source1List;
    }

    public void setSource1List(List<Source> source1List) {
        this.source1List = source1List;
    }

    public List<Source> getSource2List() {
        return source2List;
    }

    public void setSource2List(List<Source> source2List) {
        this.source2List = source2List;
    }

    public List<Source> getSource3List() {
        return source3List;
    }

    public void setSource3List(List<Source> source3List) {
        this.source3List = source3List;
    }

    public List<Source> getSource4List() {
        return source4List;
    }

    public void setSource4List(List<Source> source4List) {
        this.source4List = source4List;
    }

    public List<QuestionWithBLOBs> getQuestionList() {
        return questionList;
    }

    public void setQuestionList(List<QuestionWithBLOBs> questionList) {
        this.questionList = questionList;
    }
}