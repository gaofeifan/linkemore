package cn.linkmore.common.response;

import java.util.List;

/**
 *  车辆二级厂商
 *  一汽大众
 *  	速腾
 *  	迈腾
 *  上汽大众
 *  	凌度
 *  	帕萨特
 * @author  GFF
 * @Date     2018年5月11日
 *
 */
public class ResCarFirmBean {
	
	private String id;
	private String name;
	private String parentid;
	//具体车辆
	private List<ResCarModelBean> carlist;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getParentid() {
		return parentid;
	}
	public void setParentid(String parentid) {
		this.parentid = parentid;
	}
	public List<ResCarModelBean> getCarlist() {
		return carlist;
	}
	public void setCarlist(List<ResCarModelBean> carlist) {
		this.carlist = carlist;
	}
	@Override
	public String toString() {
		return "Car2 [id=" + id + ", name=" + name + ", parentid=" + parentid + ", carlist=" + carlist + "]";
	}
	
	
}
