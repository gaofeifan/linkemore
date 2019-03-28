package cn.linkmore.enterprise.controller.app;

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
import com.alibaba.fastjson.JSON;
import cn.linkmore.bean.common.ResponseEntity;
import cn.linkmore.bean.exception.BusinessException;
import cn.linkmore.bean.exception.StatusEnum;
import cn.linkmore.enterprise.controller.app.request.ReqAuthRecord;
import cn.linkmore.enterprise.controller.app.request.ReqAuthRecordUpdate;
import cn.linkmore.enterprise.controller.app.response.AuthRecordPre;
import cn.linkmore.enterprise.service.AuthRecordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 授权记录
 * @author jhb
 *
 */
@Api(tags = "AuthRecord", description = "长租授权记录")
@RestController
@RequestMapping("/app/auth-record")
public class AppAuthRecordController {

	@Autowired
	private AuthRecordService authRecordService;
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@ApiOperation(value = "授权人授权记录列表", notes = "授权人授权记录列表", consumes = "application/json")
	@RequestMapping(value = "/v2.0/list", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<List<AuthRecordPre>> list(HttpServletRequest request) {
		ResponseEntity<List<AuthRecordPre>> response = null;
		 try {
			 List<AuthRecordPre> authRecordPre = authRecordService.findRecordList(request);
			 response = ResponseEntity.success(authRecordPre, request);
		}  catch (BusinessException e) {
			response = ResponseEntity.fail( e.getStatusEnum(),  request);
		} catch (Exception e) { 
			response = ResponseEntity.fail(StatusEnum.SERVER_EXCEPTION, request);
		}
		 return response;
	}
	
	@ApiOperation(value = "添加授权记录",notes = "添加授权记录", consumes = "application/json")
	@RequestMapping(value = "/v2.0/save", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Boolean> save(@Validated @RequestBody ReqAuthRecord reqAuthRecord,HttpServletRequest request) {
		try {
			Boolean control = authRecordService.save(reqAuthRecord, request);
			return ResponseEntity.success(control, request);
		} catch (BusinessException e) {
			return ResponseEntity.fail( e.getStatusEnum(),  request);
		} catch (Exception e) { 
			log.info("e={}",JSON.toJSON(e.getMessage()));
			return ResponseEntity.fail(StatusEnum.SERVER_EXCEPTION, request);
		}
	}
	
	@ApiOperation(value = "修改授权记录",notes = "修改授权记录", consumes = "application/json")
	@RequestMapping(value = "/v2.0/update", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Boolean> update(@Validated @RequestBody ReqAuthRecordUpdate reqAuthRecord,HttpServletRequest request) {
		try {
			Boolean control = authRecordService.update(reqAuthRecord, request);
			return ResponseEntity.success(control, request);
		} catch (BusinessException e) {
			return ResponseEntity.fail( e.getStatusEnum(),  request);
		} catch (Exception e) { 
			log.info("e={}",JSON.toJSON(e.getMessage()));
			return ResponseEntity.fail(StatusEnum.SERVER_EXCEPTION, request);
		}
	}
	

	@ApiOperation(value = "取消授权",notes = "取消授权", consumes = "application/json")
	@RequestMapping(value = "/v2.0/cancel", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Boolean> update(@Validated @RequestParam(value="id", required=true) Long id ,HttpServletRequest request) {
		try {
			Boolean control = authRecordService.cancalAuth(id, request);
			return ResponseEntity.success(control, request);
		} catch (BusinessException e) {
			return ResponseEntity.fail( e.getStatusEnum(),  request);
		} catch (Exception e) { 
			log.info("e={}",JSON.toJSON(e.getMessage()));
			return ResponseEntity.fail(StatusEnum.SERVER_EXCEPTION, request);
		}
	}
}
