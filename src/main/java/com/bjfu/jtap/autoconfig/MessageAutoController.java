package com.bjfu.jtap.autoconfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description:第四步：根据自动配置创建一个测试类
 * @Author: Tianyu Xiao
 * @CreateDate: 2019/5/26  20:46
 */
@RestController
public class MessageAutoController {

    @Autowired
    private MessageAutoComponent component;

    @GetMapping(value = "/auto/config/message")
    public String getAutoMessage() {
        return component.getAutoMessage();
    }


}
