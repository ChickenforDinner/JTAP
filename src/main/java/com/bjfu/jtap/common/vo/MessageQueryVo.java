package com.bjfu.jtap.common.vo;

/**
 * @Description:
 * @Author: Tianyu Xiao
 * @CreateDate: 2019/4/24  10:07
 */
public class MessageQueryVo {
    private Integer page;
    private Integer isLook;
    private Integer receiveId;

    public Integer getReceiveId() {
        return receiveId;
    }

    public void setReceiveId(Integer receiveId) {
        this.receiveId = receiveId;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getIsLook() {
        return isLook;
    }

    public void setIsLook(Integer isLook) {
        this.isLook = isLook;
    }
}
