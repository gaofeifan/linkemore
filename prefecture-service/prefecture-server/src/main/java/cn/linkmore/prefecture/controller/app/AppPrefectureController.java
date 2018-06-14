package cn.linkmore.prefecture.controller.app;

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
import cn.linkmore.prefecture.controller.app.request.ReqPrefecture;
import cn.linkmore.prefecture.controller.app.response.ResPreCity;
import cn.linkmore.prefecture.controller.app.response.ResPrefectureList;
import cn.linkmore.prefecture.controller.app.response.ResPrefectureStrategy;
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
 
	
	@ApiOperation(value = "车区计费详情", notes = "根据车区ID车区计费策略", consumes = "application/json")
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
			List<ResPrefectureList> list = this.prefectureService.getStallCount();
			response = ResponseEntity.success(list, request);
		}catch(BusinessException e){
			response = ResponseEntity.fail(e.getStatusEnum(), request);
		}catch(Exception e){
			response = ResponseEntity.fail(StatusEnum.SERVER_EXCEPTION, request);
		}
		return response;
	}
	
}
