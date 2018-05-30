package cn.linkmore.ops.service;
import java.util.List;
import java.util.Map;

import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.ops.request.ReqWechatFans;
import cn.linkmore.ops.request.ReqWechatFansExcel;
import cn.linkmore.ops.response.ResWechatFans;
/**
 * 接口
 * @author   GFF
 * @Date     2018年5月29日
 * @Version  v2.0
 */
public interface WechatFansService {
	/**
	 * 分页查询
	 * @param pageable
	 * @return
	 */
	ViewPage findPage(ViewPageable pageable);
	
	/**
	 * 导出查询 
	 * @param bean
	 * @return
	 */
	List<cn.linkmore.account.response.ResWechatFans> exportList(ReqWechatFansExcel bean); 
}
