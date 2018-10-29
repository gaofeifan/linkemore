package cn.linkmore.prefecture.response;

import java.util.Date;
import java.util.List;

public class ResStrategyGroup {
	
	//public static final Byte STATUS_CLOSE=1;//状态 关闭
	//public static final Byte STATUS_OPEN=2;//状态 开启
	
    private Long id;

    private String name;
    
    private Long prefectureId;
    
    private String prefectureName;
    
    private Integer parkingCount;

    private Long createUserId;

    private String createUserName;

    private Date createTime;

    private Long updateUserId;

    private String updateUserName;

    private Date updateTime;

    private Byte status;
    
    private List<ResStrategyGroupDetail> strategyGroupDetail;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getPrefectureId() {
		return prefectureId;
	}

	public void setPrefectureId(Long prefectureId) {
		this.prefectureId = prefectureId;
	}

	public String getPrefectureName() {
		return prefectureName;
	}

	public void setPrefectureName(String prefectureName) {
		this.prefectureName = prefectureName;
	}

	public Integer getParkingCount() {
		return parkingCount;
	}

	public void setParkingCount(Integer parkingCount) {
		this.parkingCount = parkingCount;
	}

	public Long getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(Long createUserId) {
		this.createUserId = createUserId;
	}

	public String getCreateUserName() {
		return createUserName;
	}

	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Long getUpdateUserId() {
		return updateUserId;
	}

	public void setUpdateUserId(Long updateUserId) {
		this.updateUserId = updateUserId;
	}

	public String getUpdateUserName() {
		return updateUserName;
	}

	public void setUpdateUserName(String updateUserName) {
		this.updateUserName = updateUserName;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Byte getStatus() {
		return status;
	}

	public void setStatus(Byte status) {
		this.status = status;
	}

	public List<ResStrategyGroupDetail> getStrategyGroupDetail() {
		return strategyGroupDetail;
	}

	public void setStrategyGroupDetail(List<ResStrategyGroupDetail> strategyGroupDetail) {
		this.strategyGroupDetail = strategyGroupDetail;
	}
    
    
    
}