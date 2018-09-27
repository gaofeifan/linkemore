package cn.linkmore.prefecture.service;

import java.util.List;
import java.util.Map;
import cn.linkmore.bean.view.Tree;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.prefecture.entity.AdminAuthPre;
import cn.linkmore.prefecture.request.ReqAdminAuth;
import cn.linkmore.prefecture.request.ReqCheck;
import cn.linkmore.prefecture.response.ResAdminAuth;
import cn.linkmore.prefecture.response.ResAdminAuthPre;
import cn.linkmore.prefecture.response.ResPre;
import cn.linkmore.prefecture.response.ResPrefecture;
import cn.linkmore.prefecture.response.ResStaffCity;

/**
 * Service - 线下管理员 授权
 * @author jiaohanbin
 * @version 2.0
 */
public interface AdminAuthService {

	int delete(List<Long> ids);
	
	int save(ReqAdminAuth admin);
	
	int update(ReqAdminAuth admin);
	
	ResAdminAuth find(Long id);
	
	ViewPage findPage(ViewPageable pageable);

	Integer check(ReqCheck reqCheck);

	Tree findTree();

	void bind(Long id, String json,String citys,String pres);

	Map<String, Object> resource(Long id);

	/**
	 * @Description  查询管理版用户城市
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	List<ResStaffCity> findStaffCitysByAdminId(Long id);

	/**
	 * @Description  
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	List<ResPre> findStaffPreByAdminId(Long id);

}
