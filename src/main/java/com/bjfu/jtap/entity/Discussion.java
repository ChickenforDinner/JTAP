package com.bjfu.jtap.entity;

import java.util.Date;

public class Discussion {
    private Integer id;

    private String topic;

    private Integer topicId;

    private Integer replyId;

    private Integer publishId;

    private Date time;

    private String content;

    private Integer teacherJoin;

    /**
     * 自定义属性
     */
    //作者
    private User author;

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic == null ? null : topic.trim();
    }

    public Integer getTopicId() {
        return topicId;
    }

    public void setTopicId(Integer topicId) {
        this.topicId = topicId;
    }

    public Integer getReplyId() {
        return replyId;
    }

    public void setReplyId(Integer replyId) {
        this.replyId = replyId;
    }

    public Integer getPublishId() {
        return publishId;
    }

    public void setPublishId(Integer publishId) {
        this.publishId = publishId;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public Integer getTeacherJoin() {
        return teacherJoin;
    }

    public void setTeacherJoin(Integer teacherJoin) {
        this.teacherJoin = teacherJoin;
    }
}