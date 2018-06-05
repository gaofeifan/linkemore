package cn.linkmore.account.response;

import java.util.Date;

/**
 * banner管理--请求bean
 * 
 * @author GFF
 * @Date 2018年5月31日
 * @Version v2.0
 */
public class ResWalletBanner {

	private Long id;

	private String image;

	private String path;

	private Short status;

	private Short orderIndex;

	private Date createTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image == null ? null : image.trim();
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path == null ? null : path.trim();
	}

	public Short getStatus() {
		return status;
	}

	public void setStatus(Short status) {
		this.status = status;
	}

	public Short getOrderIndex() {
		return orderIndex;
	}

	public void setOrderIndex(Short orderIndex) {
		this.orderIndex = orderIndex;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}
