package cn.linkmore.account.entity;

import java.util.Date;

/**
 * 专区车位授权	
 * @author   GFF
 * @Date     2018年5月18日
 * @Version  v2.0
 */
public class AdminAuth {
    private Long id;

    private String name;

    private Date createTime;

    private Date updateTime;

    private Short status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }
}