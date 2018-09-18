package cn.linkmore.common.controller.staff;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import cn.linkmore.bean.common.ResponseEntity;
import cn.linkmore.common.controller.app.response.ResDonwLockError;
import cn.linkmore.common.response.ResBaseDict;
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
@Api(tags="Dict-Staff",description="数据字典-管理版")
@RequestMapping(value="/staff/dicts")
public class StaffBaseDictController {
	
	@Resource
	private BaseDictService baseDictService;
	
	/**
	 * @Description  查询下线原因
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	@ApiOperation(value = "【通用】查询下线原因", notes = "【通用】查询下线原因", consumes = "application/json")
	@RequestMapping(value = "/down-cause",method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<List<ResBaseDict>> downCause(HttpServletRequest request){
		List<ResBaseDict> cause = this.baseDictService.downCause();
		return ResponseEntity.success(cause, request);
	}
	/**
	 * @Description  查询挂起原因
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	@ApiOperation(value = "【通用】查询挂起原因", notes = "【通用】查询挂起原因", consumes = "application/json")
	@RequestMapping(value = "/down-hang",method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<List<ResBaseDict>> causeHang(HttpServletRequest request){
		List<ResBaseDict> cause = this.baseDictService.causeHang();
		return ResponseEntity.success(cause, request);
	}

}
