package cn.linkmore.account.service;

import java.util.List;

import cn.linkmore.account.request.ReqWechatFans;
import cn.linkmore.account.request.ReqWechatFansExcel;
import cn.linkmore.account.response.ResWechatFans;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;

/**
 * 微信粉接口
 * @author   GFF
 * @Date     2018年5月29日
 * @Version  v2.0
 */
public interface WechatFansService {

	/**
	 * @Description  分页查询
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	ViewPage findPage(ViewPageable pageable);

	/**
	 * @Description  导出查询
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	List<ResWechatFans> exportList(ReqWechatFansExcel bean);

	/**
	 * @Description  新增通过请求bean
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	void saveReq(ReqWechatFans bean);

	/**
	 * @Description  更新通过请求bean
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	void updateReq(ReqWechatFans bean);


}
