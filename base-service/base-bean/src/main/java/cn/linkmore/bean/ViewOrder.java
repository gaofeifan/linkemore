package cn.linkmore.bean;

import java.io.Serializable;
 
/**
 * 多排序条件封装
 * @author liwenlong
 * @version 1.0
 *
 */
public class ViewOrder implements Serializable {

	/**
	 * serial id
	 */
	private static final long serialVersionUID = -7391527148120393019L;
	
	private String property;
	private Direction direction = DEFAULT_DIRECTION;
	/** 
	 * 默认方向 
	 **/
	private static final Direction DEFAULT_DIRECTION = Direction.desc;
	
	/**
	 * 初始化一个新创建的Order对象
	 */
	public ViewOrder() {
	}

	/**
	 * @param property
	 *            属性
	 * @param direction
	 *            方向
	 */
	public ViewOrder(String property, Direction direction) {
		this.property = property;
		this.direction = direction;
	}

	/**
	 * 返回递增排序
	 *
	 * @param property
	 *            属性
	 * @return 递增排序
	 */
	public static ViewOrder asc(String property) {
		return new ViewOrder(property, Direction.asc);
	}

	/**
	 * 返回递减排序
	 *
	 * @param property
	 *            属性
	 * @return 递减排序
	 */
	public static ViewOrder desc(String property) {
		return new ViewOrder(property, Direction.desc);
	}

	
	public String getProperty() {
		return property;
	}
	public void setProperty(String property) {
		this.property = property;
	}
	public Direction getDirection() {
		return direction;
	}
	public void setDirection(Direction direction) {
		this.direction = direction;
	}
	
	public enum Direction { 
		asc, 
		desc; 
		public static Direction fromString(String value) {
			return Direction.valueOf(value.toLowerCase());
		}
	}

}
