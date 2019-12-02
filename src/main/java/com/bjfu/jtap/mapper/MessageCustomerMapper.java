package com.bjfu.jtap.mapper;

import java.util.List;

/**
 * @Description:
 * @Author: Tianyu Xiao
 * @CreateDate: 2019/4/23  22:59
 */
public interface MessageCustomerMapper {
    //查询唯一messageId
    List<String> selectDistinctMessageId(Integer publishId);
}
