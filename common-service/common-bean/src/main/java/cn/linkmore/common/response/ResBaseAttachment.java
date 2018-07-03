package cn.linkmore.common.response;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ResBaseAttachment {
	public static final String[] DIR = new String[]{"image","file"};
	
	public static final Short SOURCE_SERVER = 0;
	
	public static final Integer SOURCE_APP = 1;
	
	public static final Short TYPE_IMAGE = 0;
	
	public static final Short TYPE_FILE = 1; 
	
	private Long id;

	private String name;

	private String suffix;

	private Short type;

	private Long size;

	private Short source;

	private Date createTime;

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
		this.name = name == null ? null : name.trim();
	}

	public String getSuffix() {
		return suffix;
	}

	public void setSuffix(String suffix) {
		this.suffix = suffix == null ? null : suffix.trim();
	}

	public Short getType() {
		return type;
	}

	public void setType(Short type) {
		this.type = type;
	}

	public Long getSize() {
		return size;
	}

	public void setSize(Long size) {
		this.size = size;
	}

	public Short getSource() {
		return source;
	}

	public void setSource(Short source) {
		this.source = source;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * OSS文件路径
	 * 
	 * @return
	 */
	public String getFileUrl() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd/");
		String date = sdf.format(this.getCreateTime());
		StringBuffer url = new StringBuffer();
		url.append("file/").append(date).append(this.getId()).append(this.getSuffix());
		return url.toString();
	}

	/**
	 * OSS压缩图片URL
	 * 
	 * @return
	 */
	public String getCompressUrl() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd/");
		String date = sdf.format(this.getCreateTime());
		StringBuffer url = new StringBuffer();
		url.append("image/").append(date).append(this.getId()).append("_min").append(this.getSuffix());
		return url.toString();
	}

	/**
	 * OSS原图URL(未压缩)
	 * 
	 * @return
	 */
	public String getOriginalUrl() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd/");
		String date = sdf.format(this.getCreateTime());
		StringBuffer url = new StringBuffer();
		url.append("image/").append(date).append(this.getId()).append("_max").append(this.getSuffix());
		return url.toString();
	}

}