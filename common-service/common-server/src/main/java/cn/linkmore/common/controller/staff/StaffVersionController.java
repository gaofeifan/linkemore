package cn.linkmore.common.controller.staff;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import cn.linkmore.bean.common.Constants;
import cn.linkmore.bean.common.ResponseEntity;
import cn.linkmore.common.controller.app.request.ReqVersion;
import cn.linkmore.common.controller.staff.response.ResStaffAppVersion;
import cn.linkmore.common.service.StaffVersionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RequestMapping(value="/staff/version")
@RestController
@Api(tags="Staff-Version",description="版本管理【管理版】")
@Validated
public class StaffVersionController {

	@Resource
	private StaffVersionService staffVersionService;
	/**
	 * @Description  当前版本 
	 * @Author   GFF 
	 * @Version  v2.0
	 * @param  source 请求来源 1 Android 2 IOS
	 */
	@RequestMapping(value="/current",method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "查询当前版本", notes = "来源必填 1 android 2 ios", consumes = "application/json")
	public ResponseEntity<ResStaffAppVersion> current(@RequestParam("source")@ApiParam(value="来源 1 android 2 ios",required=true) @NotNull(message="参数不能为空") Integer source,HttpServletRequest request){
		int appType = 0;
		if(Constants.ClientSource.ANDROID.source == source){
			appType = 1;
		}else if(2 == source){
			appType = 2;
		}
		ResStaffAppVersion app = this.staffVersionService.currentAppVersion(appType,null);
		return ResponseEntity.success(app, request);
	}
	
	/**
	 * @Description  上报用户版本
	 * @Author  	 GFF 
	 * @Version  	 v2.0
	 */
	@RequestMapping(value="/report",method = RequestMethod.POST)
	public ResponseEntity<Void> report(@RequestBody @Validated ReqVersion vrb,HttpServletRequest request){
		this.staffVersionService.report(vrb,null);
		return ResponseEntity.success(null, request);
	}
	
}
