package cn.linkmore.ops.admin.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.ops.admin.request.ReqCreateNotice;
import cn.linkmore.ops.admin.response.ResNotice;

/**
 * 消息接口
 * @author   GFF
 * @Date     2018年6月22日
 * @Version  v2.0
 */
public interface NoticeService {

	/**
	 * @Description  查询list
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	ViewPage selectList(ViewPageable pageable);

	/**
	 * @Description  根据id查询
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	ResNotice selectById(Long id);

	/**
	 * @Description  更新消息
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	void updateNotice(ReqCreateNotice noticeBean);

	/**
	 * @Description  推送
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	void pushNotice(List<Long> ids);

	/**
	 * @Description  新增
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	void saveNotice(ReqCreateNotice noticeBean);


}
