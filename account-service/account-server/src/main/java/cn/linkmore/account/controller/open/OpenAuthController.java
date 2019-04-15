package cn.linkmore.account.controller.open;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import cn.linkmore.account.controller.open.requset.ReqOpenAuth;
import cn.linkmore.account.controller.open.response.ResOpenAuth;
import cn.linkmore.account.service.OpenAuthService;
import cn.linkmore.bean.common.ResponseEntity;
import cn.linkmore.bean.common.security.CacheUser;
import cn.linkmore.bean.exception.BusinessException;
import cn.linkmore.bean.exception.StatusEnum;
import cn.linkmore.redis.RedisService;
import cn.linkmore.user.factory.AppUserFactory;
import cn.linkmore.user.factory.UserFactory;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 第三方开放授权
 * 
 * @author changlei
 * @version 1.0
 */

@Api(tags = "Auth", description = "第三方开放授权")
@RestController
@RequestMapping("/open/auth")
public class OpenAuthController {

	@Resource
	private RedisService redisService;

	private UserFactory appUserFactory = AppUserFactory.getInstance();

	@Autowired
	private OpenAuthService openAuthService;

	@ApiOperation(value = "开放授权", notes = "token", consumes = "application/json")
	@RequestMapping(value = "/token", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<ResOpenAuth> token(@Validated @RequestBody ReqOpenAuth reqOpenAuth,
			HttpServletRequest request) {
		ResponseEntity<ResOpenAuth> response = null;
		try {
			ResOpenAuth resOpenAuth = openAuthService.getToken(reqOpenAuth);
			response = ResponseEntity.success(resOpenAuth, request);
		} catch (BusinessException e) {
			response = ResponseEntity.fail(e.getStatusEnum(), request);
		} catch (Exception e) {
			response = ResponseEntity.fail(StatusEnum.SERVER_EXCEPTION, request);
		}
		return response;
	}

	@RequestMapping(value = "/t", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<CacheUser> t(@RequestBody ReqOpenAuth reqOpenAuth, HttpServletRequest request) {
		String key = appUserFactory.createTokenRedisKey(request);
		CacheUser user = (CacheUser) this.redisService.get(key);
		return ResponseEntity.success(user, request);
	}

}
