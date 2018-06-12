package cn.linkmore.account.controller.app.request;

import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
@ApiModel("更新用户微信请求")
public class ReqUserAppfans {

	@ApiModelProperty(value="主键",required=false)
	private String id;

	@ApiModelProperty(value="昵称",required=false)
    private String nickname;

	@ApiModelProperty(value="头像",required=false)
    private String headurl;

	@ApiModelProperty(value="用户id 非必填",required=false)
    private Long userId;

	@ApiModelProperty(value="状态",required=false)
    private Short status;

	@ApiModelProperty(value="创建时间",required=false)
    private Date createTime;

	@ApiModelProperty(value="注册状态",required=false)
    private Short registerStatus;

	@ApiModelProperty(value="统一主键",required=true)
    private String unionid;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getHeadurl() {
		return headurl;
	}

	public void setHeadurl(String headurl) {
		this.headurl = headurl;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Short getStatus() {
		return status;
	}

	public void setStatus(Short status) {
		this.status = status;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Short getRegisterStatus() {
		return registerStatus;
	}

	public void setRegisterStatus(Short registerStatus) {
		this.registerStatus = registerStatus;
	}

	public String getUnionid() {
		return unionid;
	}

	public void setUnionid(String unionid) {
		this.unionid = unionid;
	}
}
