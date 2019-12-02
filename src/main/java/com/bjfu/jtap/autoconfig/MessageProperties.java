package com.bjfu.jtap.autoconfig;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @Description:    第一步：创建一个配置属性类
 * 需求：自定义配置一个字符串，如果配置了，浏览器打印配置的字符串，否则打印默认字符串。hello world!
 * @Author: Tianyu Xiao
 * @CreateDate: 2019/5/26  20:38
 */
@ConfigurationProperties(prefix = "xty-message")
public class MessageProperties {

    private String message = "world!";

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
