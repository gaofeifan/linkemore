package cn.linkmore.enterprise.response;

import java.util.List;
/**
 * 企业品牌车区
 * @author jiaohanbin
 * @version 2.0
 *
 */
public class ResBrandPreStall {
	//主键
    private Long id;
    //企业id
    private Long entId;
    //企业名称
    private String entName;
    //车区id
    private Long preId;
    //车区名称
    private String preName;
    //状态0未启用，1启用，2禁用
    private Short status;
    //车位列表
    private List<ResBrandStall> stallList;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getEntId() {
        return entId;
    }

    public void setEntId(Long entId) {
        this.entId = entId;
    }

    public String getEntName() {
        return entName;
    }

    public void setEntName(String entName) {
        this.entName = entName == null ? null : entName.trim();
    }

    public Long getPreId() {
        return preId;
    }

    public void setPreId(Long preId) {
        this.preId = preId;
    }

    public String getPreName() {
        return preName;
    }

    public void setPreName(String preName) {
        this.preName = preName == null ? null : preName.trim();
    }

	public Short getStatus() {
		return status;
	}

	public void setStatus(Short status) {
		this.status = status;
	}

	public List<ResBrandStall> getStallList() {
		return stallList;
	}

	public void setStallList(List<ResBrandStall> stallList) {
		this.stallList = stallList;
	}

}