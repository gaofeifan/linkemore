package cn.linkmore.user.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.linkmore.account.response.ResVechicleMark;
import cn.linkmore.bean.common.ResponseEntity;
import cn.linkmore.user.service.VehicleMarkManageService;
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
@Api(tags="Plate number",description="车牌号管理")
@RestController
@RequestMapping("/plate-numbers")
public class VehicleMarkController{

	@Resource
	private VehicleMarkManageService vehicleMarkManageService;
	
	
	/**
	 * @Description  新增
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	@ApiOperation(value="新增",notes="车牌号必填,车牌号规则校验", consumes = "application/json")
	@RequestMapping(value = "/v2.0/save", method = RequestMethod.POST)
	public ResponseEntity<?> create( @RequestBody cn.linkmore.user.request.ReqVehicleMark bean,HttpServletRequest request) {
		this.vehicleMarkManageService.save(bean,request);
		return ResponseEntity.success(null, request);
	}
	
	/**
	 * @Description  删除
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	@ApiOperation(value="删除",notes="根据id删除", consumes = "application/json")
	@RequestMapping(value = "/v2.0/delete", method = RequestMethod.DELETE)
	public ResponseEntity<?> delete(@ApiParam(value="id",required=true) @NotBlank(message="id不能为空")  @RequestParam("id") Long id,HttpServletRequest request){
		this.vehicleMarkManageService.deleteById(id);
		return ResponseEntity.success(null, request);
	}
	
	/**
	 * @Description  列表
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	@ApiOperation(value="列表list",notes="查询该用户所有车牌", consumes = "application/json")
	@RequestMapping(value = "/v2.0/list", method = RequestMethod.GET)
	public ResponseEntity<List<cn.linkmore.user.response.ResVechicleMark>> list(HttpServletRequest request){
		List<ResVechicleMark> list = vehicleMarkManageService.selectResList(request);
		List<cn.linkmore.user.response.ResVechicleMark> resultList = new ArrayList<>();
		for (ResVechicleMark resVechicleMark : list) {
			cn.linkmore.user.response.ResVechicleMark mark = ObjectUtils.copyObject(resVechicleMark, new cn.linkmore.user.response.ResVechicleMark());
			resultList.add(mark);
		}
		ResponseEntity<List<cn.linkmore.user.response.ResVechicleMark>> success = ResponseEntity.success(resultList, request);
		return success;
	} 
	
}
