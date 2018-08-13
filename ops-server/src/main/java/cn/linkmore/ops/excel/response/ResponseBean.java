package cn.linkmore.ops.excel.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class ResponseBean {

  @ApiModelProperty(value="消息")
  private String msg;

  public ResponseBean() {
    
  }
  
  public ResponseBean(String msg) {
    this.msg = msg;
  }
  
  
  
  public String getMsg() {
    return msg;
  }

  public void setMsg(String msg) {
    this.msg = msg;
  }
  
  
  
  
}
