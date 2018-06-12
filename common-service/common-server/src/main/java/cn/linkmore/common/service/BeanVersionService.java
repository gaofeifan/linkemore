package cn.linkmore.common.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.common.request.ReqAppVersion;
import cn.linkmore.common.request.ReqCheck;
import cn.linkmore.common.request.ReqVersion;
import cn.linkmore.common.response.ResVersionBean;

/**
 * 	版本管理接口类
 * @author   GFF
 * @Date     2018年5月17日
 * @Version  v2.0
 */
public interface BeanVersionService {

	/**
	 * @Description  当前版本
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	ResVersionBean currentAppVersion(Integer appType);

	/**
	 * @Description  上报用户版本
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	void report(ReqVersion vrb);

	/**
	 * @Description  app版本新增
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	void saveApp(ReqAppVersion version);

	/**
	 * @Description	app版本更新  
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	void updateApp(ReqAppVersion version);

	/**
	 * @Description 通过id删除 
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	void deleteAppById(List<Long> ids);

	/**
	 * @Description  查询app版本分页查询
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	ViewPage findPage(ViewPageable pageable);

	/**
	 * @Description  校验
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	Integer check(ReqCheck check);

	/**
	 * @Description  查询用户版本分页查询
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	ViewPage findUserPage(ViewPageable pageable);

	/**
	 * @Description  查询当前版本--app
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	ResVersionBean current(Integer source);

	/**
	 * @Description  上次当前版本--app
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	void report(cn.linkmore.common.controller.app.request.ReqVersion vrb, HttpServletRequest request);


}
