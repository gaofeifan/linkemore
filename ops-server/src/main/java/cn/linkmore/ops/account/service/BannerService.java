package cn.linkmore.ops.account.service;

import java.util.List;

import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.order.request.ReqUpdateStatus;
import cn.linkmore.order.request.ReqWalletBanner;
import cn.linkmore.order.response.ResWalletBanner;


/**
 * 
 * @ Content  - Banner的Service 
 * @author  zhangxurui
 * @date  2017年11月17日  下午5:09:55
 * @version  v1.0.0
 */
public interface BannerService {
	/**
	 *  保存
	 *@param banner
	 */
	void save(ReqWalletBanner banner);
	/**
	 *  修改
	 *@param banner
	 */
	void update(ReqWalletBanner banner);

	/**
	 *  根据ID查询
	 *@param bid
	 */
	ResWalletBanner findById(Long bid);
	/**
	 *  查询上线数量
	 *@return
	 */
	int findStatusCount();
	
	/**
	 * @Description  分页查询
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	ViewPage findPage(ViewPageable pageable);
	
	/**
	 * @Description  根据条件查询
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	List<ResWalletBanner> selectList(String sql);
	
	/**
	 * @Description  更新状态
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	void updateStatus(ReqUpdateStatus reqUpdateStatus);
	
}
