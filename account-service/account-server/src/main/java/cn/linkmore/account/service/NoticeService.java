package cn.linkmore.account.service;

import java.util.List;
import javax.servlet.http.HttpServletRequest;

import cn.linkmore.account.request.ReqCreateNotice;
import cn.linkmore.account.request.ReqNotice;
import cn.linkmore.account.response.ResNotice;
import cn.linkmore.account.response.ResNoticeBean;
import cn.linkmore.account.response.ResPage;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;

/**
 * 消息
 * @author   GFF
 * @Date     2018年6月9日
 * @Version  v2.0
 */
public interface NoticeService {

	/**
	 * @param request 
	 * @Description  分页查询
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	ResPage page(Long start, HttpServletRequest request);

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

	/**
	 * @Description  查询list
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	ViewPage selectList(ViewPageable pageable);

	/**
	 * @Description  新增
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	void saveNotice(ReqCreateNotice noticeBean);

	/**
	 * @Description  根据id查询
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	ResNoticeBean selectById(Long id);

	/**
	 * @Description  更新
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	void updateNotice(ReqCreateNotice noticeBean);

	/**
	 * @Description  消息推送
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	void pushNotice(List<Long> ids);

	/**
	 * @Description 推送消息 
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	void pushNotice(Long id);

	/**
	 * @Description  更新阅读状态
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	void updateRead();



}
