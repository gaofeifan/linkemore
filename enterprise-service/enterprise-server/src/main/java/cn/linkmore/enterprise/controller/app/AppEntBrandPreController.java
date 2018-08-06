package cn.linkmore.enterprise.controller.app;

import java.util.List;
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
import cn.linkmore.enterprise.controller.app.request.ReqBrandPre;
import cn.linkmore.enterprise.controller.app.response.ResEntBrandPreCity;
import cn.linkmore.enterprise.controller.app.response.ResEntBrandPreLeisure;
import cn.linkmore.enterprise.controller.app.response.ResEntBrandPreStrategy;
import cn.linkmore.enterprise.service.EntBrandPreService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * Controller - 专区
 * @author liwenlong
 * @version 2.0
 *
 */
@Api(tags="BrandPre",description="品牌车区信息")
@RestController
@RequestMapping("/app/brand-pre")
public class AppEntBrandPreController {
	@Autowired
	private EntBrandPreService entBrandPreService;
	
	@ApiOperation(value = "地图车区列表", notes = "根据城市ID及经伟度得到周边车区列表[因车区数量少，现为全部车区]", consumes = "application/json")
	@RequestMapping(value = "/v2.0/map/list", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<List<ResEntBrandPreCity>> list(@Validated @RequestBody ReqBrandPre rp, HttpServletRequest request) {
		ResponseEntity<List<ResEntBrandPreCity>> response = null;
		try { 
			List<ResEntBrandPreCity> list = this.entBrandPreService.list(rp, request);
			response = ResponseEntity.success(list, request);
		} catch (BusinessException e) {
			response = ResponseEntity.fail( e.getStatusEnum(),  request);
		} catch (Exception e) { 
			response = ResponseEntity.fail(StatusEnum.SERVER_EXCEPTION, request);
		}
		return response;
	} 
 
	
	@ApiOperation(value = "品牌车区计费详情", notes = "根据品牌车区ID查看品牌车区计费策略详情", consumes = "application/json")
	@RequestMapping(value = "/v2.0/strategy", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<ResEntBrandPreStrategy> findStrategyById(@Validated @RequestParam(value="brandPreId", required=true) Long brandPreId, HttpServletRequest request) {
		ResponseEntity<ResEntBrandPreStrategy> response = null;
		try { 
			ResEntBrandPreStrategy strategy = this.entBrandPreService.findPreStrategy(brandPreId);
			response = ResponseEntity.success(strategy, request);
		} catch (BusinessException e) {
			response = ResponseEntity.fail( e.getStatusEnum(),  request);
		} catch (Exception e) { 
			response = ResponseEntity.fail(StatusEnum.SERVER_EXCEPTION, request);
		}
		return response;
	} 
	
	/**
	 * 查询车区空闲车位
	 * 
	 * @param preId Long
	 * @return
	 */
	@ApiOperation(value = "空闲车位数", notes = "刷新所有车区空闲车位数", consumes = "application/json")
	@RequestMapping(value = "/v2.0/free/list", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<List<ResEntBrandPreLeisure>> refreshFreeStall(HttpServletRequest request) {
		ResponseEntity<List<ResEntBrandPreLeisure>> response = null ;  
		try {
			List<ResEntBrandPreLeisure> list = this.entBrandPreService.getStallCount(request);
			response = ResponseEntity.success(list, request);
		}catch(BusinessException e){
			response = ResponseEntity.fail(e.getStatusEnum(), request);
		}catch(Exception e){
			response = ResponseEntity.fail(StatusEnum.SERVER_EXCEPTION, request);
		}
		return response;
	}
	
}
