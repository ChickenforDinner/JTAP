package com.bjfu.jtap.student.service;

import com.bjfu.jtap.common.vo.MessageQueryVo;
import com.bjfu.jtap.entity.Message;
import com.bjfu.jtap.utils.PageResult;

/**
 * @Description:
 * @Author: Tianyu Xiao
 * @CreateDate: 2019/4/24  10:09
 */
public interface StudentMessageService {

    /**
     * 条件分页查询
     * @param messageQueryVo
     * @return
     */
    PageResult<Message> pageQueryList(MessageQueryVo messageQueryVo);

    /**
     * 修改为已读
     * @param id
     */
    void updateToRead(Integer id);
}
