package cn.linkmore.common.controller.app.response;

import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 响应分页消息的bean
 * @author   GFF
 * @Date     2018年6月9日
 * @Version  v2.0
 */
@ApiModel("响应分页消息的bean")
public class ResPageNotice {
	
	private static final Long READ_STATUS = 0l; //默认未阅读

    /**
     *  消息的ID
     */ 
    @ApiModelProperty(value = "消息的ID")
    private Long id;

    /**
     *  标题
     */ 
    @ApiModelProperty(value = "标题")
    private String title;
    /**
     *  通知时间
     */ 
    @ApiModelProperty(value = "通知时间")
    private Date pushTime;

    /**
     *  分类：0 文本，1 H5页面
     */ 
    @ApiModelProperty(value = "分类：0 文本，1 H5页面")
    private Long type;

    /**
     *  内容描述
     */ 
    @ApiModelProperty(value = "内容描述")
    private String description;

    /**
     *  阅读状态
     */ 
    @ApiModelProperty(value = "阅读状态")
    private Long read_status =READ_STATUS;

    public Long getRead_status() {
        return read_status;
    }

    public void setRead_status(Long read_status) {
        this.read_status = read_status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getPushTime() {
        return pushTime;
    }

    public void setPushTime(Date pushTime) {
        this.pushTime = pushTime;
    }

    public Long getType() {
        return type;
    }

    public void setType(Long type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
