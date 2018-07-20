/**
 * 
 */
package cn.linkmore.enterprise.controller.ent.response;

import java.util.Map;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 返回企业下停车场车位数量
 * @author luzhishen
 * @Date 2018年7月20日
 * @Version v1.0
 */
@ApiModel("企业车场车位统计")
public class ResEntStalls {
	
	@ApiModelProperty(value = "车区id",required = true)
	private Long preId;
	
	@ApiModelProperty(value = "车区名称",required = true)
	private String preName;
	
	@ApiModelProperty(value = "车区车位总数")
	private Map<String,Object> preStallSum;
	
	@ApiModelProperty(value = "车区类型车位总数")
	private Map<String,Object> typeSum;

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
		this.preName = preName;
	}

	public Map<String, Object> getPreStallSum() {
		return preStallSum;
	}

	public void setPreStallSum(Map<String, Object> preStallSum) {
		this.preStallSum = preStallSum;
	}

	public Map<String, Object> getTypeSum() {
		return typeSum;
	}

	public void setTypeSum(Map<String, Object> typeSum) {
		this.typeSum = typeSum;
	}
	
	

}
