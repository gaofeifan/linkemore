package cn.linkmore.account.controller.staff.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("管理版-个人中心信息响应Bean")
public class ResCenter {

	 private static final long serialVersionUID = 1L;

	    @ApiModelProperty(value = "账号")
	    private String username;

	    @ApiModelProperty(value = "姓名")
	    private String name;

	    @ApiModelProperty(value = "可用金额")
	    private Double money =0d;

	    @ApiModelProperty(value = "发劵总金额")
	    private Double sendTotal =0d;

	    public String getUsername() {
	        return username;
	    }

	    public void setUsername(String username) {
	        this.username = username;
	    }

	    public String getName() {
	        return name;
	    }

	    public void setName(String name) {
	        this.name = name;
	    }

	    public Double getMoney() {
	        return money;
	    }

	    public void setMoney(Double money) {
	        this.money = money;
	    }

	    public Double getSendTotal() {
	        return sendTotal;
	    }

	    public void setSendTotal(Double sendTotal) {
	        this.sendTotal = sendTotal;
	    }
}
