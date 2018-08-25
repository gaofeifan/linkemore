package cn.linkmore.enterprise.controller.app;

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
import cn.linkmore.enterprise.controller.app.request.ReqConStall;
import cn.linkmore.enterprise.controller.app.request.ReqWatchStatus;
import cn.linkmore.enterprise.controller.app.response.OwnerRes;
import cn.linkmore.enterprise.controller.ent.request.ReqTestTest;
import cn.linkmore.enterprise.service.OwnerStallService;
import cn.linkmore.redis.RedisService;
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
	private RedisService redisService;

	@Autowired
	private OwnerStallService ownerStallServicel;

	@ApiOperation(value = "获取车位列表", notes = "根据用户身份获取已拥有车位", consumes = "application/json")
	@RequestMapping(value = "/v2.0/list", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<OwnerRes> list(@Validated HttpServletRequest request) {
		OwnerRes res = ownerStallServicel.findStall(request);
		return ResponseEntity.success(res, request);
	}

	@ApiOperation(value = "长租用户操作车位锁", notes = "长租用户操作车位锁", consumes = "application/json")
	@RequestMapping(value = "/v2.0/control", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Boolean> controlLock(@Validated @RequestBody ReqConStall reqConStall,
			HttpServletRequest request) {
		try {
			Integer res = ownerStallServicel.control(reqConStall, request);
			if (res == -1) {
				return ResponseEntity.fail(StatusEnum.USER_APP_NO_LOGIN, request);
			} else if (res == -2) {
				return ResponseEntity.fail(StatusEnum.STALL_AlREADY_CONTROL, request);
			}
			return ResponseEntity.success(true, request);
		} catch (Exception e) {
			return ResponseEntity.fail(StatusEnum.SERVER_EXCEPTION, request);
		}
	}

	@ApiOperation(value = "查看操作结果", notes = "查看操作结果", consumes = "application/json")
	@RequestMapping(value = "/v2.0/watch", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Boolean> watch(@Validated @RequestBody ReqWatchStatus reqWatchStatus,
			HttpServletRequest request) {

		Integer res = ownerStallServicel.watch(reqWatchStatus.getStallId(), request);
		if (res == -1) {
			return ResponseEntity.fail(StatusEnum.USER_APP_NO_LOGIN, request);
		} else if (res == 2) {
			return ResponseEntity.success(false, request);
		}
		return ResponseEntity.success(true, request);

	}

	@ApiOperation(value = "是否位长租用户", notes = "查询用户是否有长租车位", consumes = "application/json")
	@RequestMapping(value = "/v2.0/owner", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<Boolean> owner(@Validated HttpServletRequest request) {
		Boolean is = ownerStallServicel.owner(request);
		return ResponseEntity.success(is, request);
	}

	@ApiOperation(value = "长租用户操作车位锁", notes = "长租用户操作车位锁", consumes = "application/json")
	@RequestMapping(value = "/testcontrol", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<String> testcontrolLock(@Validated ReqTestTest reqTestTest, HttpServletRequest request) {
		String rediskey = RedisKey.ACTION_STALL_DOING.key + reqTestTest.getKey();
		redisService.remove(rediskey);
		new Thread(new Runnable() {
			@Override
			public void run() {
				ResponseMessage<LockBean> res = null;
				// 1 降下 2 升起
				if (reqTestTest.getState() == 1) {
					res = lockFactory.lockDown(reqTestTest.getStallId());
				} else if (reqTestTest.getState() == 2) {
					res = lockFactory.lockUp(reqTestTest.getStallId());
				}
				System.out.println(res.getMsgCode() + "--" + res.getMsg());
			}
		}).start();
		return ResponseEntity.success("操作成功", request);
	}

}
