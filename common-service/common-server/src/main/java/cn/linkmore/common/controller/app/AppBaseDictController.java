package cn.linkmore.common.controller.app;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import cn.linkmore.bean.common.ResponseEntity;
import cn.linkmore.common.controller.app.response.ResDonwLockError;
import cn.linkmore.common.controller.app.response.ResRelationCode;
import cn.linkmore.common.service.BaseDictService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 数据字典
 * @author   GFF
 * @Date     2018年5月18日
 * @Version  v2.0
 */
@RestController
@Api(tags="Dict",description="数据字典")
@RequestMapping(value="/app/dicts")
public class AppBaseDictController {
	
	@Resource
	private BaseDictService baseDictService;
	
	/**
	 * @Description  查询降锁异常原因
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	@ApiOperation(value = "查询降锁异常原因", notes = "查询降锁异常原因", consumes = "application/json")
	@RequestMapping(value="/v2.0/cause/switch",method=RequestMethod.GET)
	public ResponseEntity<List<ResDonwLockError>> selectLockDownErrorCause(HttpServletRequest request) {
		List<ResDonwLockError> res = this.baseDictService.selectLockDownErrorCause();
		ResponseEntity<List<ResDonwLockError>> success = ResponseEntity.success(res, request);
		return success;
	}
	
	@ApiOperation(value = "查询故障原因", notes = "查询故障原因", consumes = "application/json")
	@RequestMapping(value="/v2.0/cause/fault",method=RequestMethod.GET)
	public ResponseEntity<List<ResDonwLockError>> findLockFaultCause(HttpServletRequest request) {
		List<ResDonwLockError> res = this.baseDictService.findLockFaultCause();
		ResponseEntity<List<ResDonwLockError>> success = ResponseEntity.success(res, request);
		return success;
	}
	
	@ApiOperation(value = "查询被授权人关系", notes = "查询被授权人关系", consumes = "application/json")
	@RequestMapping(value="/v2.0/relation/code",method=RequestMethod.GET)
	public ResponseEntity<List<ResRelationCode>> findRelationCode(HttpServletRequest request) {
		List<ResRelationCode> res = this.baseDictService.findRelationCode();
		ResponseEntity<List<ResRelationCode>> success = ResponseEntity.success(res, request);
		return success;
	}
	
	
}
