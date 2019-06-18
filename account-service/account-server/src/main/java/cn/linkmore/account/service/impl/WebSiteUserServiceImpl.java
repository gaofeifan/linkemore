package cn.linkmore.account.service.impl;

import java.util.Date;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.linkmore.account.controller.app.request.ReqAuthPW;
import cn.linkmore.account.dao.cluster.WebSiteUserClusterMapper;
import cn.linkmore.account.dao.master.WebSiteUserMasterMapper;
import cn.linkmore.account.entity.WebSiteUser;
import cn.linkmore.account.service.WebSiteUserService;
import cn.linkmore.bean.exception.BusinessException;
import cn.linkmore.bean.exception.StatusEnum;

@Service
public class WebSiteUserServiceImpl implements WebSiteUserService {
	
	private Logger log = LoggerFactory.getLogger(getClass()); 
	
	@Autowired
	private WebSiteUserClusterMapper userClusterMapper;
	@Autowired
	private WebSiteUserMasterMapper userMasterMapper;
	@Override
	public Boolean register(ReqAuthPW authPw) {
		WebSiteUser resUser = userClusterMapper.findByMobile(authPw.getMobile());
		if(resUser != null) {
			throw new BusinessException(StatusEnum.ACCOUNT_USER_MOBILE_EXIST);
		}
		String password = DigestUtils.md5Hex(authPw.getPassword());
		WebSiteUser user = new WebSiteUser();
		user.setMobile(authPw.getMobile());
		user.setPassword(password);
		user.setCreateTime(new Date());
		int num = userMasterMapper.register(user);
		if(num == 1) {
			return true;
		}
		return false;
	}
	
	@Override
	public String loginPW(ReqAuthPW pw) {
		WebSiteUser resUser = userClusterMapper.findByMobile(pw.getMobile());
		if(resUser == null){
			throw new BusinessException(StatusEnum.ACCOUNT_USER_NOT_EXIST);
		}
		if(StringUtils.isBlank(resUser.getPassword())) {
			throw new BusinessException(StatusEnum.ACCOUNT_PASSWORD_ERROR);
		}
		if(resUser.getPassword().equals(DigestUtils.md5Hex(pw.getPassword()))) {
			return pw.getMobile();
		}
		throw new BusinessException(StatusEnum.ACCOUNT_PASSWORD_ERROR);
	}

}
