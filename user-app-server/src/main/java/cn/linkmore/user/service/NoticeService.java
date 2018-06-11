package cn.linkmore.user.service;

import javax.servlet.http.HttpServletRequest;

import cn.linkmore.account.request.ReqPageNotice;
import cn.linkmore.account.response.ResNotice;
import cn.linkmore.account.response.ResPage;

/**
 * @author   GFF
 * @Date     2018年6月11日
 * @Version  v2.0
 */
public interface NoticeService {

	/**
	 * @return 
	 * @Description  分页查询
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	ResPage findPage(ReqPageNotice bean);

	/**
	 * @Description  
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	ResNotice read(Long id, HttpServletRequest request);

	/**
	 * @Description  根据主键删除
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	void delete(Long nid, HttpServletRequest request);

}
