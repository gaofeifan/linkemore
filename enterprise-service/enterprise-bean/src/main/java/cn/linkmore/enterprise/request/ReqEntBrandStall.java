package cn.linkmore.enterprise.request;

import java.util.Date;
/**
 * 企业品牌车位
 * @author jiaohanbin
 * @version 2.0
 *
 */
public class ReqEntBrandStall {
	//主键
    private Long id;
    //品牌车区id
    private Long brandPreId;
    //车位id
    private Long stallId;
    //车位名称
    private String stallName;
    //创建时间
    private Date createTime;
    //更新时间
    private Date updateTime;
    //0未启用，1启用，2禁用
    private Short status;
    
    private String stallIdJson;

    public String getStallIdJson() {
		return stallIdJson;
	}

	public void setStallIdJson(String stallIdJson) {
		this.stallIdJson = stallIdJson;
	}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBrandPreId() {
        return brandPreId;
    }

    public void setBrandPreId(Long brandPreId) {
        this.brandPreId = brandPreId;
    }

    public Long getStallId() {
        return stallId;
    }

    public void setStallId(Long stallId) {
        this.stallId = stallId;
    }

    public String getStallName() {
        return stallName;
    }

    public void setStallName(String stallName) {
        this.stallName = stallName == null ? null : stallName.trim();
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