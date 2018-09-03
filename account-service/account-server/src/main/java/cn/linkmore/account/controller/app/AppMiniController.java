package cn.linkmore.account.controller.app;

import java.nio.charset.StandardCharsets;
import java.security.AlgorithmParameters;
import java.util.Map;

import javax.annotation.Resource;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.apache.commons.codec.binary.Base64;
import org.hibernate.validator.constraints.NotBlank;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import cn.linkmore.account.controller.app.request.ReqMiniMobile;
import cn.linkmore.account.controller.app.request.ReqMobileBind;
import cn.linkmore.account.controller.app.response.ResUser;
import cn.linkmore.account.service.UserService;
import cn.linkmore.bean.common.Constants.RedisKey;
import cn.linkmore.bean.common.ResponseEntity;
import cn.linkmore.bean.common.security.CacheUser;
import cn.linkmore.redis.RedisService;
import cn.linkmore.util.TokenUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.sf.json.JSONObject;

@Api(tags = "Mini", description = "小程序")
@RestController
@RequestMapping("/app/mini")
@Validated
public class AppMiniController {
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	@Resource
	private UserService userService;
	@Resource
	private RedisService redisService;

	@ApiOperation(value = "登录", notes = "微信小程序登录", consumes = "application/json")
	@RequestMapping(value = "/v2.0/login", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<ResUser> mini(
			@RequestParam(value = "code") @NotBlank(message = "授权码不能为空") @Size(min = 32, max = 36, message = "授权码为无效") String code,
			HttpServletRequest request) {
		ResUser urb = this.userService.mini(code, request);
		return ResponseEntity.success(urb, request);
	}
	
	@ApiOperation(value = "登录", notes = "微信小程序登录Plus", consumes = "application/json")
	@RequestMapping(value = "/v3.0/login", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<ResUser> miniPlus(
			@RequestParam(value = "code") @NotBlank(message = "授权码不能为空") @Size(min = 32, max = 36, message = "授权码为无效") String code,
			@RequestParam(value = "alias") @NotBlank(message = "识别名不能为空(个人版:1001,物业版:1002,管理版1003)") Integer alias,
			HttpServletRequest request) {
		ResUser urb = this.userService.miniPlus(code, alias, request);
		return ResponseEntity.success(null, request);
	}

	@ApiOperation(value = "绑定授权手机号", notes = "手机号不能为空,短信验证码不能为空", consumes = "application/json")
	@RequestMapping(value = "/v2.0/bind", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<ResUser> bindMobile(@RequestParam(value = "mobile", required = true) String mobile,
			HttpServletRequest request) {
		ResUser user = this.userService.bindWechatMobile(mobile, request);
		return ResponseEntity.success(user, request);
	}

	@ApiOperation(value = "绑定普通手机号", notes = "手机号不能为空,短信验证码不能为空", consumes = "application/json")
	@RequestMapping(value = "/v2.0/bind", method = RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<ResUser> bind(@RequestBody @Validated ReqMobileBind rmb, HttpServletRequest request) {
		ResUser user = this.userService.bindNormalMobile(rmb, request);
		return ResponseEntity.success(user, request);
	}

	@ApiOperation(value = "发短信验证码", notes = "手机号不能为空", consumes = "application/json")
	@RequestMapping(value = "/v2.0/sms", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> sms(
			@NotBlank(message = "手机号不能为空") @Pattern(regexp = "^(((13[0-9]{1})|(14[0-9]{1})|(15[0-9]{1})|(16[0-9]{1})|(17[0-9]{1})|(18[0-9]{1})|(19[0-9]{1}))+\\d{8})$", message = "无效手机号") @RequestParam(value = "mobile", required = true) String mobile,
			HttpServletRequest request) {
		ResponseEntity<?> response = null;
		this.userService.send(mobile, request);
		response = ResponseEntity.success(null, request);
		return response;
	}

	@ApiOperation(value = "解密授权手机号", notes = "iv与data不能为空", consumes = "application/json")
	@RequestMapping(value = "/v2.0/decrypt/mobile", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<String> bindMobile(@RequestBody ReqMiniMobile rmm, HttpServletRequest request) {
		CacheUser cu = (CacheUser) this.redisService.get(RedisKey.USER_APP_AUTH_USER.key + TokenUtil.getKey(request));
		String json = null; 
		log.info("se:{},data:{},iv:{}", cu.getSession(), rmm.getData(), rmm.getIv());
		try {
			json = this.decrypt(cu.getSession(), rmm.getData(), rmm.getIv());
		} catch (Exception e) {

		}
		log.info("json:{}", json);
		String mobile = null;
		ResponseEntity<String> response = null;
		if (json != null) {
			Map<String, ?> map = (Map<String, ?>) JSONObject.fromObject(json);
			if (map != null) {
				if (map.get("purePhoneNumber") != null) {
					mobile = map.get("purePhoneNumber").toString();
				}
			}
		}
		if (mobile != null) {
			response = ResponseEntity.success(mobile, request);
		} else {
			response = ResponseEntity.fail(null, request);
		}
		return response;
	}

	private  String decrypt(String sessionKey, String encryptedData, String ivStr) {
		try {
			AlgorithmParameters params = AlgorithmParameters.getInstance("AES");
			params.init(new IvParameterSpec(Base64.decodeBase64(ivStr)));

			Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
			cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(Base64.decodeBase64(sessionKey), "AES"), params);

			return new String(PKCS7Encoder.decode(cipher.doFinal(Base64.decodeBase64(encryptedData))),
					StandardCharsets.UTF_8);
		} catch (Exception e) {
			throw new RuntimeException("AES解密失败", e);
		}
	}

}
