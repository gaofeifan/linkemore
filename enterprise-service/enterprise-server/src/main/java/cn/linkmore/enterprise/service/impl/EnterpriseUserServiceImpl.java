package cn.linkmore.enterprise.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.annotation.Resource;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import cn.linkmore.account.client.UserClient;
import cn.linkmore.account.client.VehicleMarkClient;
import cn.linkmore.account.request.ReqVehicleMark;
import cn.linkmore.account.response.ResUser;
import cn.linkmore.account.response.ResVechicleMark;
import cn.linkmore.bean.view.ViewFilter;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.enterprise.dao.cluster.EnterpriseUserClusterMapper;
import cn.linkmore.enterprise.dao.master.EnterpriseUserMasterMapper;
import cn.linkmore.enterprise.entity.EnterpriseUser;
import cn.linkmore.enterprise.request.ReqEnterpriseUser;
import cn.linkmore.enterprise.response.ResEnterpriseUser;
import cn.linkmore.enterprise.service.EnterpriseUserService;
import cn.linkmore.util.DomainUtil;
import cn.linkmore.util.ObjectUtils;

@Service
@Transactional(isolation = Isolation.REPEATABLE_READ, propagation = Propagation.REQUIRED, rollbackForClassName = "RuntimeExcpeiton")
public class EnterpriseUserServiceImpl implements EnterpriseUserService {

	@Resource
	private EnterpriseUserMasterMapper enterpriseUserMasterMapper;
	
	@Resource
	private EnterpriseUserClusterMapper enterpriseUserClusterMapper;

	@Resource
	private UserClient userClient;

	/*@Resource
	private AccountClient accountMapper;*/

	@Resource
	private VehicleMarkClient vehicleMarkClient;

	@Override
	public int save(ReqEnterpriseUser reqEnterpriseUser) {
		EnterpriseUser enterpriseUser = ObjectUtils.copyObject(reqEnterpriseUser, new EnterpriseUser());
		Map<String, Object> param = null;
		Date currentTime = new Date();
		String mobile = enterpriseUser.getMobile();
		ResUser user = userClient.getUserByUserName(mobile);
		/*if (user == null) {
			user = new User();
			// 初始化新用户
			user.setUsername(mobile);// 用户名
			user.setPassword(UUID.randomUUID().toString()); // 用户密码
			user.setStatus(0); // 状态修改为 0 未注册
			user.setUserType(1); // 用户类型 个人类型
			user.setMobile(mobile); // 手机号
			user.setCreateTime(currentTime); // 初始化时间
			user.setUpdateTime(currentTime); // 初始修改时间
			user.setType(1);
			userClient.insertAndGetId(user);
			// 生成账套
			Account acc = new Account();
			acc.setId(user.getId());
			acc.setAmount(0.00d);
			acc.setUsableAmount(0.00d);
			acc.setFrozenAmount(0.00d);
			acc.setRechagePaymentAmount(0.00d);
			acc.setRechargeAmount(0.00d);
			acc.setAccType(user.getUserType());
			acc.setStatus(0);
			acc.setOrderAmount(0.00d);
			acc.setOrderPaymentAmount(0.00d);
			acc.setCreateTime(currentTime);
			accountMapper.insert(acc);
		}else {
			user.setType(1);
			user.setUpdateTime(currentTime);
			userMapper.updateByPrimaryKey(user);
		}*/
		param = new HashMap<>();
		param.put("uId", user.getId());
		param.put("entId", enterpriseUser.getEnterpriseId());
		ResEnterpriseUser findEntUser = enterpriseUserClusterMapper.getEntUserByUserId(param);
		if (null != findEntUser) {
			findEntUser.setStatus((short)1);
			findEntUser.setPlateNo(enterpriseUser.getPlateNo());
			findEntUser.setUpdateTime(currentTime);
			EnterpriseUser enterUser = ObjectUtils.copyObject(findEntUser, new EnterpriseUser());
			enterpriseUserMasterMapper.update(enterUser);
		} else {
			enterpriseUser.setStatus((short)1);
			enterpriseUser.setUserId(user.getId());
			enterpriseUser.setCreateTime(currentTime);
			enterpriseUser.setUpdateTime(currentTime);
			enterpriseUserMasterMapper.save(enterpriseUser);
		}
		this.changedPlateNo(enterpriseUser.getPlateNo(), enterpriseUser.getPlateNo(), user.getId());
		return 0;
	}

	@Override
	public int delete(Long id) {
		ResEnterpriseUser enterpriseUser = enterpriseUserClusterMapper.findById(id);
		EnterpriseUser user = ObjectUtils.copyObject(enterpriseUser, new EnterpriseUser());
		user.setStatus((short)0);
		user.setUpdateTime(new Date());
		return enterpriseUserMasterMapper.update(user);
	}

	@Override
	public ResEnterpriseUser getEnterpriseUser(Long id) {
		return enterpriseUserClusterMapper.findById(id);
	}

	@Override
	public ViewPage findPage(ViewPageable pageable,Long personId) {
		Map<String, Object> param = new HashMap<>();
		List<ViewFilter> filters = pageable.getFilters();

		if (StringUtils.isNotBlank(pageable.getSearchProperty())) {
			param.put(pageable.getSearchProperty(), pageable.getSearchValue());
		}

		if (filters != null && filters.size() > 0) {
			for (ViewFilter filter : filters) {
				param.put(filter.getProperty(), filter.getValue());
			}
		}
		if (StringUtils.isNotBlank(pageable.getOrderProperty())) {
			param.put("property", DomainUtil.camelToUnderline(pageable.getOrderProperty()));
			param.put("direction", pageable.getOrderDirection());
		}
		param.put("entId", personId);
		int count = enterpriseUserClusterMapper.count(param);
		param.put("start", pageable.getStart());
		param.put("pageSize", pageable.getPageSize());
		List<ResEnterpriseUser> findPage = enterpriseUserClusterMapper.findPage(param);
		return new ViewPage(count, pageable.getPageSize(), findPage);
	}

	@Override
	public int update(ReqEnterpriseUser updateUser) {
		Date updateTime = new Date();
		String updateNo = updateUser.getPlateNo();
		ResEnterpriseUser user = enterpriseUserClusterMapper.findById(updateUser.getId());
		updateUser = ObjectUtils.copyObject(user, updateUser);
		Long userId = user.getUserId();
		String forpl = user.getPlateNo();
		user.setPlateNo(updateNo.toUpperCase());
		user.setUpdateTime(updateTime);
		EnterpriseUser enteUser = ObjectUtils.copyObject(updateUser, new EnterpriseUser());
		enterpriseUserMasterMapper.update(enteUser);
		return this.changedPlateNo(forpl, updateNo, userId);
	}

	private int changedPlateNo(String forpl, String newpl, Long userId) {
		Map<String, Object> param = new HashMap<>();
		param.put("userId", userId);
		param.put("plateNo", forpl);
		ResVechicleMark vechicle = vehicleMarkClient.findByPlateNo(param);
		if (null == vechicle) {
			ReqVehicleMark reqMark = new ReqVehicleMark();
			reqMark.setVehMark(newpl.toUpperCase());
			reqMark.setUserId(userId);
			vehicleMarkClient.create(reqMark);
		} else {
			ReqVehicleMark reqMark = ObjectUtils.copyObject(vechicle, new ReqVehicleMark());
			reqMark.setNewpl(newpl.toUpperCase());
			vehicleMarkClient.update(reqMark);
		}
		return 0;
	}

	@Override
	public void saveAll(List<ReqEnterpriseUser> all) {
		for (ReqEnterpriseUser enterpriseUser : all) {
			this.save(enterpriseUser);
		}
	}

	@Override
	public List<ResEnterpriseUser> findExcel(Map<String,Object> param) {
		List<ResEnterpriseUser> res = enterpriseUserClusterMapper.findList(param);
		return res;
	}

}
