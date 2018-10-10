package cn.linkmore.enterprise.controller.staff.response;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 顾客信息数据源[数据字典]
 * 
 * @author liwl
 * @version 1.0
 *
 */
@ApiModel("客户属性")
public class DictBean implements Serializable {
	/**
	 * serial id
	 */
	private static final long serialVersionUID = 8593137186758105275L;
	@ApiModelProperty(value = "主键ID")
	private Long id;
	@ApiModelProperty(value = "编号")
	private String code;
	@ApiModelProperty(value = "标题")
	private String title;
	@ApiModelProperty(value = "true为选中")
	private boolean check = false;

	public boolean isCheck() {
		return check;
	}

	public void setCheck(boolean check) {
		this.check = check;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}
