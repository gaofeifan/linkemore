package cn.linkmore.user.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import cn.linkmore.bean.common.ResponseEntity;
import cn.linkmore.bean.exception.BusinessException;
import cn.linkmore.bean.exception.StatusEnum;
import cn.linkmore.user.request.ReqPrefecture;
import cn.linkmore.user.response.ResPreCity;
import cn.linkmore.user.response.ResPrefectureStrategy;
import cn.linkmore.user.service.PrefectureService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * Controller - 专区
 * @author liwenlong
 * @version 2.0
 *
 */
@Api(tags="Prefecture",description="专区信息")
@RestController
@RequestMapping("/prefectures")
public class PrefectureController {
	@Autowired
	private PrefectureService prefectureService;
	
	@ApiOperation(value = "地图列表", notes = "根据城市ID及经伟度得到周边车区列表[因车区数量少，现为全部车区]", consumes = "application/json")
	@RequestMapping(value = "/v2.0/map", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<List<ResPreCity>> list(@RequestBody ReqPrefecture rp, HttpServletRequest request) {
		ResponseEntity<List<ResPreCity>> response = null;
		try { 
			List<ResPreCity> list = this.prefectureService.list(rp, request);
			response = ResponseEntity.success(list, request);
		} catch (BusinessException e) {
			response = ResponseEntity.fail( e.getStatusEnum(),  request);
		} catch (Exception e) { 
			response = ResponseEntity.fail(StatusEnum.SERVER_EXCEPTION, request);
		}
		return response;
	} 
 
	
	@ApiOperation(value = "车区计费详情", notes = "根据车区ID车区计费策略", consumes = "application/json")
	@RequestMapping(value = "/v2.0/strategy", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<ResPrefectureStrategy> findStrategyById(@RequestParam(value="preId", required=true) Long preId, HttpServletRequest request) {
		ResponseEntity<ResPrefectureStrategy> response = null;
		try { 
			ResPrefectureStrategy strategy = this.prefectureService.findStrategyById(preId, request);
			response = ResponseEntity.success(strategy, request);
		} catch (BusinessException e) {
			response = ResponseEntity.fail( e.getStatusEnum(),  request);
		} catch (Exception e) { 
			response = ResponseEntity.fail(StatusEnum.SERVER_EXCEPTION, request);
		}
		return response;
	} 
	
	/**
	 * 根据车区id查询车区空闲车位
	 * 
	 * @param preId Long
	 * @return
	 */
	@ApiOperation(value = "空闲车位数", notes = "根据车区ID获取空闲车位数", consumes = "application/json")
	@RequestMapping(value = "/v2.0/free_count", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<Integer> findFreeStallCount(@RequestParam(value="preId", required=true) Long preId, HttpServletRequest request) {
		ResponseEntity<Integer> response = null ;  
		try {
			Integer count = this.prefectureService.findFreeStallCount(preId);
			response = ResponseEntity.success(count, request);
		}catch(BusinessException e){
			response = ResponseEntity.fail(e.getStatusEnum(), request);
		}catch(Exception e){
			response = ResponseEntity.fail(StatusEnum.SERVER_EXCEPTION, request);
		}
		return response;
	}
	
}
