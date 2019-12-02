package com.bjfu.jtap.autoconfig;


/**
 * @Description:第二步：创建一个功能的Bean
 * @Author: Tianyu Xiao
 * @CreateDate: 2019/5/26  21:07
 */
public class MessageAutoComponent {
    private String message = null;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getAutoMessage() {
        String msg = "hello " +message;
        return msg;
    }
}
