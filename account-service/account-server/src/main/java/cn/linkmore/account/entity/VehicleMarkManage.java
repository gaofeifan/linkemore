package cn.linkmore.account.entity;

import java.util.Date;

import cn.linkmore.annotation.GColumn;
import cn.linkmore.annotation.GTable;
@GTable(vlaue="v_vehicle_mark_manage")
public class VehicleMarkManage {
	@GColumn
    private Long id;

	@GColumn
    private String vehUserId;

	@GColumn
    private String vehMark;

	@GColumn
    private Date createTime;

	@GColumn
    private Date updateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVehUserId() {
        return vehUserId;
    }

    public void setVehUserId(String vehUserId) {
        this.vehUserId = vehUserId == null ? null : vehUserId.trim();
    }

    public String getVehMark() {
        return vehMark;
    }

    public void setVehMark(String vehMark) {
        this.vehMark = vehMark == null ? null : vehMark.trim();
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
}