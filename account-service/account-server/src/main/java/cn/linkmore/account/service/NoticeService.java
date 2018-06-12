package cn.linkmore.account.service;

import javax.servlet.http.HttpServletRequest;

import cn.linkmore.account.request.ReqNotice;
import cn.linkmore.account.request.ReqPageNotice;
import cn.linkmore.account.response.ResNotice;
import cn.linkmore.account.response.ResPage;

/**
 * 消息
 * @author   GFF
 * @Date     2018年6月9日
 * @Version  v2.0
 */
public interface NoticeService {

	/**
	 * @Description  分页查询
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	ResPage page(ReqPageNotice bean);

	/**
	 * @Description  
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	ResNotice read(ReqNotice reqNotice);

	/**
	 * @Description  
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	void delete(ReqNotice notice);

	/**
	 * @Description  阅读--app
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	ResNotice read(Long id, HttpServletRequest request);

	/**
	 * @Description  删除--app
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	void delete(Long nid, HttpServletRequest request);


}
