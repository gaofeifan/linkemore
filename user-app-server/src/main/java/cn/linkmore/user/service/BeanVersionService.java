package cn.linkmore.user.service;

import javax.servlet.http.HttpServletRequest;

import cn.linkmore.common.request.ReqVersion;
import cn.linkmore.common.response.ResVersionBean;

/**
 * 版本管理接口
 * @author   GFF
 * @Date     2018年5月21日
 * @Version  v2.0
 */
public interface BeanVersionService {

	/**
	 * 查询当前版本
	 * @Description  
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	ResVersionBean current(Integer source);

	/**
	 * @param request 
	 * @Description  上报版本
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	void report(ReqVersion vrb, HttpServletRequest request);

}
