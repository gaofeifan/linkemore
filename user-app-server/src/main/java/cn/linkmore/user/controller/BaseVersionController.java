package cn.linkmore.user.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import cn.linkmore.bean.common.ResponseEntity;
import cn.linkmore.common.request.ReqVersion;
import cn.linkmore.common.response.ResVersionBean;
import cn.linkmore.user.service.BeanVersionService;
import cn.linkmore.util.ObjectUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 版本管理
 * @author   GFF
 * @Date     2018年5月17日
 * @Version  v2.0
 */
@Api(tags="Versions",description="版本管理")
@RestController
@RequestMapping("/versions")
public class BaseVersionController {
	
	@Resource
	private BeanVersionService beanVersionService;
	
	/**
	 * @Description  当前版本 
	 * @Author   GFF 
	 * @Version  v2.0
	 * @param  source 请求来源 1 Android 2 IOS
	 */
	@RequestMapping(value="/current",method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "查询当前版本", notes = "来源必填 1 android 2 ios", consumes = "application/json")
	public ResponseEntity<cn.linkmore.user.response.ResVersionBean> current(@ApiParam(value="来源" ,required=true) @NotBlank(message="来源不能为空") @RequestParam("source")Integer source,HttpServletRequest request){
		ResVersionBean res = this.beanVersionService.current(source);
		cn.linkmore.user.response.ResVersionBean bean = ObjectUtils.copyObject(res, new cn.linkmore.user.response.ResVersionBean());
		ResponseEntity<cn.linkmore.user.response.ResVersionBean> responseEntity = ResponseEntity.success(bean, request);
		return responseEntity;
	}
	
	/**
	 * @Description  上报用户版本
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	@RequestMapping(value="/report",method = RequestMethod.POST)
	@ApiOperation(value = "上报用户版本", notes = "上报用户版本", consumes = "application/json")
	public ResponseEntity<?> report(@RequestBody ReqVersion vrb,HttpServletRequest request){
		this.beanVersionService.report(vrb);
		return ResponseEntity.success(null, request);
	}
}
