package cn.linkmore.account.service;

import java.util.List;

import cn.linkmore.account.request.ReqFeedBack;
import cn.linkmore.account.response.ResFeedBack;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;

/**
 * 问题反馈接口
 * @author   GFF
 * @Date     2018年5月29日
 * @Version  v2.0
 */
public interface FeedbackService {

	/**
	 * @Description  分页查询
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	ViewPage findPage(ViewPageable pageable);

	/**
	 * @Description  导出数据 
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	List<ResFeedBack> exportList(ReqFeedBack bean);

}
