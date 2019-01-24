package cn.linkmore.account.controller.app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.linkmore.account.controller.app.request.ReqVehicleMark;
import cn.linkmore.account.response.ResVechicleMark;
import cn.linkmore.account.service.VehicleMarkManageService;
import cn.linkmore.bean.common.ResponseEntity;
import cn.linkmore.bean.exception.BusinessException;
import cn.linkmore.bean.exception.StatusEnum;
import cn.linkmore.util.ObjectUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
	
/**
 * 车牌号管理
 * @author   GFF 
 * @Date     2018年5月21日
 * @Version  v2.0
 */
@Api(tags="Plate Number",description="车牌号管理")
@RestController
@RequestMapping("/app/plate-numbers")
@Validated
public class AppVehicleMarkController{

	@Resource
	private VehicleMarkManageService vehicleMarkManageService;
	
	
	/**
	 * @Description  新增
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	@ApiOperation(value="新增",notes="车牌号必填,车牌号规则校验", consumes = "application/json")
	@RequestMapping(value = "/v2.0/save", method = RequestMethod.POST)
	public ResponseEntity<?> create( @RequestBody @Validated ReqVehicleMark bean,HttpServletRequest request) {
		ResponseEntity<?> response = null;
		try {
			Boolean flag = this.vehicleMarkManageService.save(bean,request);
			response = ResponseEntity.success(flag, request);
		} catch (BusinessException e) {
			response = ResponseEntity.fail(e.getStatusEnum(),  request);
		} catch (Exception e) { 
			response = ResponseEntity.fail(StatusEnum.SERVER_EXCEPTION, request);
		}
		return response;
	}
	
	/**
	 * @Description  删除
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	@ApiOperation(value="删除",notes="根据id删除", consumes = "application/json")
	@RequestMapping(value = "/v2.0/delete", method = RequestMethod.DELETE)
	public ResponseEntity<?> delete(@ApiParam(value="id",required=true) @NotNull(message="id不能为空") @Min(message="请输入整数",value=1)  @RequestParam("id") Long id,HttpServletRequest request){
		ResponseEntity<?> response = null;
		try {
			this.vehicleMarkManageService.deleteById(id,request);
			return ResponseEntity.success(null, request);
		} catch (BusinessException e) {
			response = ResponseEntity.fail(e.getStatusEnum(), request);
		} catch (Exception e) {
			return ResponseEntity.fail(StatusEnum.UNAUTHORIZED.code, "此账号下没有该车牌号", request);
		}
		return response;
	}
	
	/**
	 * @Description  列表
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	@ApiOperation(value="列表list",notes="查询该用户所有车牌", consumes = "application/json")
	@RequestMapping(value = "/v2.0/list", method = RequestMethod.GET)
	public ResponseEntity<List<cn.linkmore.account.controller.app.response.ResVechicleMark>> list(HttpServletRequest request){
		List<ResVechicleMark> list = vehicleMarkManageService.findResList(request);
		List<cn.linkmore.account.controller.app.response.ResVechicleMark> resultList = new ArrayList<>();
		for (ResVechicleMark resVechicleMark : list) {
			cn.linkmore.account.controller.app.response.ResVechicleMark mark = ObjectUtils.copyObject(resVechicleMark, new cn.linkmore.account.controller.app.response.ResVechicleMark());
			resultList.add(mark);
		}
		ResponseEntity<List<cn.linkmore.account.controller.app.response.ResVechicleMark>> success = ResponseEntity.success(resultList, request);
		return success;
	} 
	
}
