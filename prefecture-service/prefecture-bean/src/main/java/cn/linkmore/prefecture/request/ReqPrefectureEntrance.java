package cn.linkmore.prefecture.request;

public class ReqPrefectureEntrance {
    private Long id;
    /**
     * 通道类型
     */
    private String entranceType;
    /**
     * 名称
     */
    private String name;
    /**
     * 车区id
     */
    private Long preId;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getEntranceType() {
		return entranceType;
	}
	public void setEntranceType(String entranceType) {
		this.entranceType = entranceType;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Long getPreId() {
		return preId;
	}
	public void setPreId(Long preId) {
		this.preId = preId;
	}
    
}