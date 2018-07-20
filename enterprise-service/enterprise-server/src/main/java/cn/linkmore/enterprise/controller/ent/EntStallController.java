/**
 * 
 */
package cn.linkmore.enterprise.controller.ent;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import cn.linkmore.bean.common.Constants.RedisKey;
import cn.linkmore.bean.common.security.CacheUser;
import cn.linkmore.enterprise.controller.app.request.ReqEntStalls;
import cn.linkmore.enterprise.controller.ent.response.ResEntStalls;
import cn.linkmore.enterprise.service.EntStallService;
import cn.linkmore.redis.RedisService;
import cn.linkmore.util.TokenUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author luzhishen
 * @Date 2018年7月20日
 * @Version v1.0
 */

@Api(tags = "stall",description="企业车位", produces = "application/json")
@RestController
@RequestMapping("/stall")
public class EntStallController {
	
	@Autowired
	private RedisService redisService;
	
	@Autowired
	private EntStallService entStallService;
	
	@ApiOperation(value = "查询企业下停车场信息", notes = "查询企业下停车场信息", consumes = "application/json")
	@RequestMapping(value = "/select-ent-stalls",method = RequestMethod.POST)
	@ResponseBody
	public List<ResEntStalls> selectEntStalls(HttpServletRequest request){
		List<ResEntStalls> list = null;
		String key = TokenUtil.getKey(request);
		CacheUser ru = (CacheUser)this.redisService.get(RedisKey.STAFF_ENT_AUTH_USER.key+key);
		if(ru == null){
			list = new ArrayList<ResEntStalls>();
			return list;
		}
		list = entStallService.selectEntStalls(ru.getId());
		return null;
	}
	 

}
