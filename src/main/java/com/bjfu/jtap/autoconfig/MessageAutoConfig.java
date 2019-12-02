package com.bjfu.jtap.autoconfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Description:第三步：创建自动配置类，自动配置代码
 * @Author: Tianyu Xiao
 * @CreateDate: 2019/5/26  20:44
 */
@Configuration
@EnableConfigurationProperties(MessageProperties.class)//这个注解读入我们的配置对象类
@ConditionalOnClass(MessageAutoComponent.class)//当类路径存在这个类时才会加载这个配置类，否则跳过,这个很有用比如不同jar包间类依赖，依赖的类不存在直接跳过，不会报错
public class MessageAutoConfig {

    @Autowired
    private MessageProperties messageProperties;

     @Bean
     @ConditionalOnMissingBean(MessageAutoComponent.class)
     public MessageAutoComponent getMessageProperties() {
         MessageAutoComponent component = new MessageAutoComponent();
         component.setMessage(messageProperties.getMessage());
         return component;
     }
}
