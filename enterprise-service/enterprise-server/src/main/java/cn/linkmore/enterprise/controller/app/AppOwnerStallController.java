package cn.linkmore.enterprise.controller.app;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.linkmore.lock.bean.LockBean;
import com.linkmore.lock.factory.LockFactory;
import com.linkmore.lock.response.ResponseMessage;

import cn.linkmore.bean.common.ResponseEntity;
import cn.linkmore.bean.common.Constants.RedisKey;
import cn.linkmore.bean.exception.StatusEnum;
import cn.linkmore.enterprise.controller.app.response.OwnerPre;
import cn.linkmore.enterprise.controller.ent.request.ReqOperatStall;
import cn.linkmore.enterprise.service.OwnerStallService;
import cn.linkmore.prefecture.client.StallClient;
import cn.linkmore.prefecture.request.ReqControlLock;
import cn.linkmore.util.JsonUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * Controller - 长租车位
 * 
 * @author changlei
 * @version 1.0
 *
 */
@Api(tags = "OwnerStall", description = "企业与个人长租车位")
@RestController
@RequestMapping("/app/owner-stall")
public class AppOwnerStallController {

	@Autowired
	private LockFactory lockFactory;
	
	@Autowired
	private StallClient stallClient;
	
	@Autowired
	private OwnerStallService ownerStallServicel;

	@ApiOperation(value = "获取车位列表", notes = "根据用户身份获取已拥有车位", consumes = "application/json")
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<List<OwnerPre>> list(@Validated HttpServletRequest request) {
			List<OwnerPre>   res = ownerStallServicel.findStall(request);
		return ResponseEntity.success(res, request);
	}
	
	@ApiOperation(value = "长租用户操作车位锁", notes = "长租用户操作车位锁", consumes = "application/json")
	@RequestMapping(value = "/control", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<String> controlLock(@Validated  @RequestBody  ReqOperatStall reqOperatStall, HttpServletRequest request) {	
		try {
			Boolean  bl = ownerStallServicel.control(reqOperatStall, request);
			if(bl) {
				return ResponseEntity.success("操作成功", request);
			}else {
				return ResponseEntity.success("操作失败", request);
			}
		} catch (RuntimeException e) {
		   return	 ResponseEntity.fail( StatusEnum.USER_APP_NO_LOGIN, request);
		} catch (Exception e ) {
		   return ResponseEntity.success("操作失败", request);
		}
	}
	
	@ApiOperation(value = "长租用户操作车位锁", notes = "长租用户操作车位锁", consumes = "application/json")
	@RequestMapping(value = "/testcontrol", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<String> testcontrolLock(@Validated  ReqOperatStall reqOperatStall, HttpServletRequest request) {
		ReqControlLock reqc = new ReqControlLock();
		stallClient.close(111L);
		stallClient.controllock(reqc);
		/*new Thread(new Runnable() {
	        @Override
	        public void run() {
	        	ResponseMessage<LockBean> res = null;
	        	//1 降下 2 升起
				if(reqOperatStall.getState()==1){
				 res = 	lockFactory.lockDown("FCAEE5D0E27E");
				}else if(reqOperatStall.getState()==2){
			     res = lockFactory.lockUp("FCAEE5D0E27E");
				}
				System.out.println(res.getMsgCode()+"--"+ res.getMsg() );
	        }
	    }).start();*/
		
		return ResponseEntity.success("操作成功", request);
	}
	

}
