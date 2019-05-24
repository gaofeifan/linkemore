package cn.linkmore.prefecture.entity;

/**
 * entity 车区虚拟数据视图
 * @author jiaohanbin
 * @version 2.0
 *
 */
public class PrefectureView {
    private Long id;
    
    private Long preId;

    private String preName;
    
    private Integer freeStall;
    
    private String time;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPreName() {
		return preName;
	}

	public void setPreName(String preName) {
		this.preName = preName;
	}

	public Long getPreId() {
		return preId;
	}

	public void setPreId(Long preId) {
		this.preId = preId;
	}

	public Integer getFreeStall() {
		return freeStall;
	}

	public void setFreeStall(Integer freeStall) {
		this.freeStall = freeStall;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

}