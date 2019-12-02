package com.bjfu.jtap.common.vo;

/**
 * @Description:学生多条件查询封装对象
 * @Author: Tianyu Xiao
 * @CreateDate: 2019/1/28  10:29
 */
public class StudentQueryVo {
    private String username;
    private String college;
    private int termId;
    private int teacherId;

    public int getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(int teacherId) {
        this.teacherId = teacherId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public int getTermId() {
        return termId;
    }

    public void setTermId(int termId) {
        this.termId = termId;
    }
}
