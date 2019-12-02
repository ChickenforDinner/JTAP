package com.bjfu.jtap.entity;

import java.util.Date;

public class Homework {
	
	 private Integer homeworkStatus;//状态     1：可以上传      2：已上传       3：已过截至时间
	
    private Integer id;

    private Integer teacherId;

    private String name;

    private Date startTime;

    private Date endTime;

    private String demo;

    private Integer termId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Integer teacherId) {
        this.teacherId = teacherId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getDemo() {
        return demo;
    }

    public void setDemo(String demo) {
        this.demo = demo == null ? null : demo.trim();
    }

    public Integer getTermId() {
        return termId;
    }

    public void setTermId(Integer termId) {
        this.termId = termId;
    }

	public Integer getHomeworkStatus() {
		return homeworkStatus;
	}

	public void setHomeworkStatus(Integer homeworkStatus) {
		this.homeworkStatus = homeworkStatus;
	}
    
}