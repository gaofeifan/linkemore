/**
 * 
 */
package cn.linkmore.enterprise.controller.ent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import cn.linkmore.bean.common.Constants.RedisKey;
import cn.linkmore.bean.common.security.CacheUser;
import cn.linkmore.enterprise.controller.ent.request.ReqOperatStall;
import cn.linkmore.enterprise.controller.ent.request.ReqPreStall;
import cn.linkmore.enterprise.controller.ent.response.ResDetailStall;
import cn.linkmore.enterprise.controller.ent.response.ResEntStalls;
import cn.linkmore.enterprise.service.EntStallService;
import cn.linkmore.prefecture.response.ResStall;
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
	@RequestMapping(value = "/select-pre-stalls",method = RequestMethod.POST)
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
		return list;
	}
	
	@ApiOperation(value = "查询停车场车位列表", notes = "查询停车场车位列表", consumes = "application/json")
	@RequestMapping(value = "/select-stalls",method = RequestMethod.POST)
	@ResponseBody
	public List<ResStall> selectEntStalls(@RequestBody ReqPreStall reqPreStall ,HttpServletRequest request){
		List<ResStall> list = null;
		String key = TokenUtil.getKey(request);
		CacheUser ru = (CacheUser)this.redisService.get(RedisKey.STAFF_ENT_AUTH_USER.key+key);
		if(ru == null){
			list = new ArrayList<>();
			return list;
		}
		if(reqPreStall == null){
			list = new ArrayList<>();
			return list;
		}
		list = entStallService.selectStalls(ru.getId(),reqPreStall.getPreId(),reqPreStall.getType());
		return list;
	}
	 
	@ApiOperation(value = "查询车位详细信息", notes = "查询车位详细信息", consumes = "application/json")
	@RequestMapping(value = "/select-detail-stalls",method = RequestMethod.POST)
	@ResponseBody
	public ResDetailStall selectEntDetailStalls(Long stall_id ,HttpServletRequest request){
		if(stall_id == null){
			return null;
		}
		ResDetailStall detailStall = this.entStallService.selectEntDetailStalls(stall_id);
		return detailStall;
	}
	
	@ApiOperation(value = "操作车位", notes = "操作车位", consumes = "application/json")
	@RequestMapping(value = "/operate-stall",method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> operatStalls(@RequestBody ReqOperatStall reqOperatStall,HttpServletRequest request){
		String key = TokenUtil.getKey(request);
		CacheUser ru = (CacheUser)this.redisService.get(RedisKey.STAFF_ENT_AUTH_USER.key+key);
		if(ru == null){
			return null;
		}
		Map<String,Object> message  = this.entStallService.operatStalls(ru.getId(),reqOperatStall.getStallId(),reqOperatStall.getState());
		return message;
	}
	
	@ApiOperation(value = "车位上线", notes = "车位上线", consumes = "application/json")
	@RequestMapping(value = "/change-up",method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> changeUp(Long stall_id,HttpServletRequest request){
		String key = TokenUtil.getKey(request);
		CacheUser ru = (CacheUser)this.redisService.get(RedisKey.STAFF_ENT_AUTH_USER.key+key);
		if(ru == null){
			return null;
		}
		Map<String,Object> message  = this.entStallService.change(ru.getId(),stall_id,1);
		return message;
	}
	
	@ApiOperation(value = "车位下线", notes = "车位下线", consumes = "application/json")
	@RequestMapping(value = "/change-down",method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> changeDown(Long stall_id,HttpServletRequest request){
		String key = TokenUtil.getKey(request);
		CacheUser ru = (CacheUser)this.redisService.get(RedisKey.STAFF_ENT_AUTH_USER.key+key);
		if(ru == null){
			return null;
		}
		Map<String,Object> message  = this.entStallService.change(ru.getId(),stall_id,2);
		return message;
	}
	
	
}
