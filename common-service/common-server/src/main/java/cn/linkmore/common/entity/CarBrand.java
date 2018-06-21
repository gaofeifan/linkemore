package cn.linkmore.common.entity;

/**
 *  车辆一级商标
 * 	奥迪
 * 	大众
 * 
 * @author zhaoyufei
 */
public class CarBrand {
	
	private String id;
	private String name;
	//首字母 A、B
	private String initial;
	private String parentid;
	//车辆品牌图标，地址为阿里提供
//	private String logo;
	//层次深度--暂时为用到
//	private String depth;
	
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
	public String getInitial() {
		return initial;
	}
	public void setInitial(String initial) {
		this.initial = initial;
	}
	public String getParentid() {
		return parentid;
	}
	public void setParentid(String parentid) {
		this.parentid = parentid;
	}
	
	@Override
	public String toString() {
		return "Car [id=" + id + ", name=" + name + ", initial=" + initial + ", parentid=" + parentid + "]";
	}
	
}
