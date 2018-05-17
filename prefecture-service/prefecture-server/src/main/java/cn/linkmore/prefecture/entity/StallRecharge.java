package cn.linkmore.prefecture.entity;

import java.util.Date;
/**
 * entity 车位指定
 * @author jiaohanbin
 * @version 2.0
 *
 */
public class StallRecharge {
    private Long id;

    private Long operatorId;

    private Long stallId;

    private Date createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(Long operatorId) {
        this.operatorId = operatorId;
    }

    public Long getStallId() {
        return stallId;
    }

    public void setStallId(Long stallId) {
        this.stallId = stallId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}