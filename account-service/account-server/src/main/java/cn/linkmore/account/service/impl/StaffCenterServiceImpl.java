package cn.linkmore.account.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import cn.linkmore.account.controller.staff.response.ResCenter;
import cn.linkmore.account.service.StaffCenterService;
import cn.linkmore.bean.common.Constants.RedisKey;
import cn.linkmore.bean.common.security.CacheUser;
import cn.linkmore.prefecture.client.AdminUserClient;
import cn.linkmore.prefecture.client.StaffAdminUserPackClient;
import cn.linkmore.prefecture.response.ResAdminUser;
import cn.linkmore.redis.RedisService;
import cn.linkmore.util.TokenUtil;

/**
 * @author GFF
 * @Date 2018年9月17日
 * @Version v2.0
 */
@Service
public class StaffCenterServiceImpl implements StaffCenterService {

	@Resource
	private RedisService redisService;
	@Resource
	private AdminUserClient adminUserClient;
	@Resource
	private StaffAdminUserPackClient adminUserPackClient;

	@Override
	public ResCenter getCenter(HttpServletRequest request) {
		CacheUser ru = (CacheUser) this.redisService
				.get(RedisKey.STAFF_STAFF_AUTH_USER.key + TokenUtil.getKey(request));
		ResAdminUser admin = this.adminUserClient.findById(ru.getId());
		HashMap<String, Object> cause = this.adminUserPackClient.findByAdminId(ru.getId());
		ResCenter bean = new ResCenter();
		if (cause != null) {
			if (cause.get("money") != null) {
				bean.setMoney(Double.parseDouble(cause.get("money").toString()));
			}
			if (cause.get("sendTotal") != null) {
				bean.setSendTotal(Double.parseDouble(cause.get("sendTotal").toString()));
			}
		}

		bean.setName(admin.getRealname());
		bean.setUsername(admin.getCellphone());
		return bean;
	}

}
