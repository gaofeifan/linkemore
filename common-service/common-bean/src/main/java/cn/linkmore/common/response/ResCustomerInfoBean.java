package cn.linkmore.common.response;

import java.io.Serializable;

/**
 * 顾客信息数据源[数据字典]
 * @Version 2.0
 * @author  GFF
 * @Date     2018年5月10日
 */
public class ResCustomerInfoBean implements Serializable {

    private static final long serialVersionUID = 6333509164676661331L;
    
	private Long id;
	
	private String code;
	
	private String title;

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

