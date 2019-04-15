package cn.linkmore.enterprise.controller.app.response;
import java.util.List;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


@ApiModel("车区车位信息")
public class AuthRecordPre {
	
	@ApiModelProperty(value = "车区id")
	private Long preId;
	
	@ApiModelProperty(value = "车区名称")
	private String preName;
	
	@ApiModelProperty(value = "车位id")
	private Long stallId;
	
	@ApiModelProperty(value = "车位名称")
	private String stallName;
			
	@ApiModelProperty(value = "授权记录列表")
	private List<AuthRecordDetail> detailList;

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
		this.stallName = stallName;
	}

	public List<AuthRecordDetail> getDetailList() {
		return detailList;
	}

	public void setDetailList(List<AuthRecordDetail> detailList) {
		this.detailList = detailList;
	}
	
}
