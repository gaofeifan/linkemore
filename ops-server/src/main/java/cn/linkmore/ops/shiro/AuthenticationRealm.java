package cn.linkmore.ops.shiro;

import java.util.Date;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import com.alibaba.fastjson.JSON;
import cn.linkmore.ops.security.request.ReqPerson;
import cn.linkmore.ops.security.response.ResPerson;
import cn.linkmore.ops.security.service.MenuService;
import cn.linkmore.ops.security.service.PageElementService;
import cn.linkmore.ops.security.service.PersonService;
import cn.linkmore.security.response.ResAuthElement;
import cn.linkmore.util.ObjectUtils;
import cn.linkmore.util.PasswordUtil;

/**
 * 权限认证
 * <br/>Base Framework
 * @author liwenlong.net
 * @version 1.0
 */
public class AuthenticationRealm extends AuthorizingRealm {
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private PersonService personService;   
	
	@Autowired
	private MenuService menuService;
	
	@Autowired
	private PageElementService pageElementService;
	/**
	 * 获取认证信息
	 *
	 * @param token
	 *            令牌
	 * @return 认证信息
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(org.apache.shiro.authc.AuthenticationToken token) {
		AuthenticationToken authenticationToken = (AuthenticationToken) token;
		String username = authenticationToken.getUsername();
		String password = new String(authenticationToken.getPassword()); 
		String ip = authenticationToken.getHost();
		 
		if (StringUtils.isNotBlank(username) && StringUtils.isNotBlank(password)) {
			ResPerson person = this.personService.findByUsername(username); 
			if (person == null) {
				throw new UnknownAccountException();
			}
			if (person.getStatus()==0) {
				throw new DisabledAccountException();
			}
 
			if (person.getLockStatus()!=0) {
				int loginFailureLockTime = 3;
				if (loginFailureLockTime == 0) {
					throw new LockedAccountException();
				}
				Long lockedTime = person.getLockTime().getTime();
				Long unlockTime = DateUtils.addMinutes(new Date(lockedTime), loginFailureLockTime).getTime();
				if (new Date().after(new Date(unlockTime) )) {
					person.setLockCount(0);
					person.setLockStatus(0); 
					person.setPassword(null);
					ReqPerson p = new ReqPerson();
					p = ObjectUtils.copyObject(person, p);
					personService.update(p);
				} else {
					throw new LockedAccountException();
				}
			}
			if (!PasswordUtil.checkPassword(password, person.getPassword())) {
				int loginFailureCount = person.getLockCount() + 1;
				if (loginFailureCount >= 3) {
					person.setLockStatus(1);
					person.setPassword(null);
					person.setLockTime(new Date());
				}
				person.setLockCount(loginFailureCount);
				person.setPassword(null);
				ReqPerson p = new ReqPerson();
				p = ObjectUtils.copyObject(person, p);
				personService.update(p);
				throw new IncorrectCredentialsException();
			}
			person.setLoginIp(ip);
			person.setLoginTime(new Date());
			person.setLockCount(0);
			try{
				person.setPassword(null);
				ReqPerson p = new ReqPerson();
				p = ObjectUtils.copyObject(person, p);
				personService.update(p);
			}catch(Exception e){
				e.printStackTrace();
			}
			Subject currentPerson = SecurityUtils.getSubject();
			currentPerson.getSession().setAttribute("person", person);
			log.info("session {}",JSON.toJSON(currentPerson.getSession().getAttribute("person")));
			this.menuService.cachePersonAuthList(); 
			
			List<String> authorities = this.personService.findAuthList(new Principal(person.getId(), username )); 
			currentPerson.getSession().setAttribute("authorities", authorities);
			List<ResAuthElement> maps = this.pageElementService.findResAuthElementList();
			currentPerson.getSession().setAttribute("authelement", maps);
			return new SimpleAuthenticationInfo(new Principal(person.getId(), username ), password, getName());
		}
		throw new UnknownAccountException();
	}

	/**
	 * 获取授权信息
	 *
	 * @param principals
	 *            principals
	 * @return 授权信息
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		Principal principal = (Principal) principals.fromRealm(getName()).iterator().next();
		if (principal != null) {
			Subject currentPerson = SecurityUtils.getSubject();
			List<String> authorities = (List<String>)currentPerson.getSession().getAttribute("authorities");  
			if (authorities != null) {
				SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
				authorizationInfo.addStringPermissions(authorities);
				return authorizationInfo;
			}
		}
		return null;
	}

}