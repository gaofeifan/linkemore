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
	
	@ApiModelProperty(value = "车区id")
	private Long preId;
	
	@ApiModelProperty(value = "车区名称")
	private String preName;
	
	@ApiModelProperty(value = "车区车位总数")
	private int preStalls ;
	
	@ApiModelProperty(value = "车区车位使用总数")
	private int preUseStalls;
	
	@ApiModelProperty(value = "车区车位类型使用总数")
	private Map<String,ResEntTypeStalls> typeStalls;
	

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

	public int getPreStalls() {
		return preStalls;
	}

	public void setPreStalls(int preStalls) {
		this.preStalls = preStalls;
	}

	public int getPreUseStalls() {
		return preUseStalls;
	}

	public void setPreUseStalls(int preUseStalls) {
		this.preUseStalls = preUseStalls;
	}

	public Map<String, ResEntTypeStalls> getTypeStalls() {
		return typeStalls;
	}

	public void setTypeStalls(Map<String, ResEntTypeStalls> typeStalls) {
		this.typeStalls = typeStalls;
	}

}
