package cn.linkmore.prefecture.controller.app;

import java.util.List;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import cn.linkmore.prefecture.controller.app.request.ReqBooking;
import cn.linkmore.prefecture.controller.app.request.ReqNearPrefecture;
import cn.linkmore.prefecture.controller.app.request.ReqPrefecture;
import cn.linkmore.prefecture.controller.app.response.ResPreCity;
import cn.linkmore.prefecture.controller.app.response.ResPrefectureDetail;
import cn.linkmore.prefecture.controller.app.response.ResPrefectureList;
import cn.linkmore.prefecture.controller.app.response.ResPrefectureStrategy;
import cn.linkmore.prefecture.controller.app.response.ResStallInfo;
import cn.linkmore.prefecture.service.BluetoothDataService;
import cn.linkmore.prefecture.service.PrefectureService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * Controller - 专区
 * @author liwenlong
 * @version 2.0
 *
 */
@Api(tags="Prefecture",description="车区信息")
@RestController
@RequestMapping("/app/prefectures")
public class AppPrefectureController {
	@Autowired
	private PrefectureService prefectureService;
	
	@Autowired
	private BluetoothDataService bluetoothDataService;
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@ApiOperation(value = "地图车区列表", notes = "根据城市ID及经伟度得到周边车区列表[因车区数量少，现为全部车区]", consumes = "application/json")
	@RequestMapping(value = "/v2.0/map/list", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<List<ResPreCity>> list(@Validated @RequestBody ReqPrefecture rp, HttpServletRequest request) {
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
 
	
	@ApiOperation(value = "车区计费详情", notes = "根据车区ID查看车区计费策略详情", consumes = "application/json")
	@RequestMapping(value = "/v2.0/strategy", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<ResPrefectureStrategy> findStrategyById(@Validated @RequestParam(value="preId", required=true) Long preId, HttpServletRequest request) {
		ResponseEntity<ResPrefectureStrategy> response = null;
		try { 
			ResPrefectureStrategy strategy = this.prefectureService.findPreStrategy(preId);
			response = ResponseEntity.success(strategy, request);
		} catch (BusinessException e) {
			response = ResponseEntity.fail( e.getStatusEnum(),  request);
		} catch (Exception e) { 
			response = ResponseEntity.fail(StatusEnum.SERVER_EXCEPTION, request);
		}
		return response;
	} 
	
	@ApiOperation(value = "车区详情", notes = "根据车区ID查看车区详情", consumes = "application/json")
	@RequestMapping(value = "/v2.0/detail", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<ResPrefectureDetail> findById(@Validated @RequestParam(value="preId", required=true) Long preId, HttpServletRequest request) {
		ResponseEntity<ResPrefectureDetail> response = null;
		try {
			ResPrefectureDetail preDetail = this.prefectureService.findPreDetailById(preId, request);
			response = ResponseEntity.success(preDetail, request);
		} catch (BusinessException e) {
			response = ResponseEntity.fail( e.getStatusEnum(),  request);
		} catch (Exception e) { 
			log.info("message = {}", e.getMessage());
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
	public ResponseEntity<List<ResPrefectureList>> refreshFreeStall(HttpServletRequest request) {
		ResponseEntity<List<ResPrefectureList>> response = null ;  
		try {
			List<ResPrefectureList> list = this.prefectureService.getStallCount(request);
			response = ResponseEntity.success(list, request);
		}catch(BusinessException e){
			response = ResponseEntity.fail(e.getStatusEnum(), request);
		}catch(Exception e){
			response = ResponseEntity.fail(StatusEnum.SERVER_EXCEPTION, request);
		}
		return response;
	}
	
	@ApiOperation(value = "空闲车位列表详情", notes = "根据车区ID查看空闲车位列表", consumes = "application/json")
	@RequestMapping(value = "/v2.0/stall-list", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<ResStallInfo> stallList(@Validated @RequestBody ReqBooking reqBooking, HttpServletRequest request) {
		ResponseEntity<ResStallInfo> response = null;
		try { 
			ResStallInfo stallInfo = this.prefectureService.findStallList(reqBooking);
			response = ResponseEntity.success(stallInfo, request);
		} catch (BusinessException e) {
			response = ResponseEntity.fail( e.getStatusEnum(),  request);
		} catch (Exception e) { 
			response = ResponseEntity.fail(StatusEnum.SERVER_EXCEPTION, request);
		}
		return response;
	} 
	
	@ApiOperation(value = "增加数据埋点", notes = "蓝牙降锁增加数据埋点", consumes = "application/json")
	@RequestMapping(value = "/v2.0/bluetooth", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> bluetooth(@Validated @RequestParam(value="param", required=true) String param, HttpServletRequest request) {
		ResponseEntity<?> response = null;
		try {
			Boolean flag = this.bluetoothDataService.saveData(param, request);
			response = ResponseEntity.success(flag, request);
		} catch (BusinessException e) {
			response = ResponseEntity.fail( e.getStatusEnum(),  request);
		} catch (Exception e) { 
			response = ResponseEntity.fail(StatusEnum.SERVER_EXCEPTION, request);
		}
		return response;
	} 
	
	@ApiOperation(value = "当前经纬度附近地图车区列表", notes = "根据经伟度得到周边车区列表[因车区数量少，现为全部车区]", consumes = "application/json")
	@RequestMapping(value = "/v2.0/map/near-list", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<List<cn.linkmore.prefecture.controller.app.response.ResPrefecture>> nearList(@Validated @RequestBody ReqNearPrefecture rp, HttpServletRequest request) {
		ResponseEntity<List<cn.linkmore.prefecture.controller.app.response.ResPrefecture>> response = null;
		try { 
			List<cn.linkmore.prefecture.controller.app.response.ResPrefecture> list = this.prefectureService.nearList(rp, request);
			response = ResponseEntity.success(list, request);
		} catch (BusinessException e) {
			response = ResponseEntity.fail( e.getStatusEnum(),  request);
		} catch (Exception e) { 
			response = ResponseEntity.fail(StatusEnum.SERVER_EXCEPTION, request);
		}
		return response;
	} 
	
}
