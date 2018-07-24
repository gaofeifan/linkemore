package cn.linkmore.enterprise.controller.app;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import cn.linkmore.bean.common.ResponseEntity;
import cn.linkmore.bean.exception.BusinessException;
import cn.linkmore.bean.exception.StatusEnum;
import cn.linkmore.enterprise.controller.app.request.ReqBrandApplicant;
import cn.linkmore.enterprise.controller.app.response.ResEntBrandAd;
import cn.linkmore.enterprise.service.EntBrandAdService;
import cn.linkmore.enterprise.service.EntBrandApplicantService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * Controller
 * @author jiaohanbin
 * @version 2.0
 *
 */
@RestController
@Api(tags="Brand",description="品牌广告")
@RequestMapping(value="/app/brands")
public class AppEntBrandAdController {
	
	@Autowired
	private EntBrandAdService entBrandAdService;
	
	@Autowired
	private EntBrandApplicantService entBrandApplicantService;
	
	@ApiOperation(value = "开屏广告详情", notes = "开机是否展示开屏广告", consumes = "application/json")
	@RequestMapping(value = "/v2.0/brand_ad", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<ResEntBrandAd> findBrandAdScreen(@Validated @RequestParam(value="cityId", required=true) Long cityId, HttpServletRequest request) {
		ResponseEntity<ResEntBrandAd> response = null;
		try {
			ResEntBrandAd resEntBrandAd = this.entBrandAdService.findBrandAdScreen(cityId,request);
			response = ResponseEntity.success(resEntBrandAd, request);
		} catch (BusinessException e) {
			response = ResponseEntity.fail( e.getStatusEnum(),  request);
		} catch (Exception e) { 
			response = ResponseEntity.fail(StatusEnum.SERVER_EXCEPTION, request);
		}
		return response;
	} 
	
	@ApiOperation(value = "品牌申请人", notes = "非品牌授权用户申请品牌权限", consumes = "application/json")
	@RequestMapping(value = "/v2.0/brand_apply", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Boolean> brandApply(@Validated @RequestBody ReqBrandApplicant reqBrandApplicant, HttpServletRequest request) {
		ResponseEntity<Boolean> response = null;
		try {
			Boolean flag = this.entBrandApplicantService.brandApplicant(reqBrandApplicant, request);
			response = ResponseEntity.success(flag, request);
		} catch (BusinessException e) {
			response = ResponseEntity.fail( e.getStatusEnum(),  request);
		} catch (Exception e) { 
			response = ResponseEntity.fail(StatusEnum.SERVER_EXCEPTION, request);
		}
		return response;
	}
	
	@ApiOperation(value = "发送优惠券", notes = "品牌用户发送优惠券", consumes = "application/json")
	@RequestMapping(value = "/v2.0/send", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> send(@Validated @RequestParam(value="entId", required=true) Long entId, HttpServletRequest request) {
		ResponseEntity<?> response = null;
		try {
			Boolean flag = this.entBrandAdService.send(entId, request);
			response = ResponseEntity.success(flag, request);
		} catch (BusinessException e) {
			response = ResponseEntity.fail( e.getStatusEnum(),  request);
		} catch (Exception e) { 
			response = ResponseEntity.fail(StatusEnum.SERVER_EXCEPTION, request);
		}
		return response;
	} 
	
	@ApiOperation(value = "品牌车区广告详情", notes = "品牌车区广告详情", consumes = "application/json")
	@RequestMapping(value = "/v2.0/brand_pre_ad", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<ResEntBrandAd> findBrandPreAd(@Validated @RequestParam(value="id", required=true) Long id, HttpServletRequest request) {
		ResponseEntity<ResEntBrandAd> response = null;
		try {
			ResEntBrandAd resEntBrandAd = this.entBrandAdService.findBrandPreAd(id,request);
			response = ResponseEntity.success(resEntBrandAd, request);
		} catch (BusinessException e) {
			response = ResponseEntity.fail( e.getStatusEnum(),  request);
		} catch (Exception e) { 
			response = ResponseEntity.fail(StatusEnum.SERVER_EXCEPTION, request);
		}
		return response;
	} 
	
	
}