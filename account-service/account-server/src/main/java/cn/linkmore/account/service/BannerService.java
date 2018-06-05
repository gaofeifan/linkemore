package cn.linkmore.account.service;

import java.util.List;

import cn.linkmore.account.entity.WalletBanner;
import cn.linkmore.account.request.ReqWalletBanner;
import cn.linkmore.account.response.ResWalletBanner;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;


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
	 *  修改状态
	 *@param bid
	 *@param status
	 */
	void setStatus(Long bid,int status);
	/**
	 *  根据ID查询
	 *@param bid
	 */
	WalletBanner findById(Long bid);
	/**
	 *  查询上线数量
	 *@return
	 */
	int findStatusCount();
	
	ViewPage findPage(ViewPageable pageable);
	
	void updateColumnValue(Long bid, String column, int status);
	
	List<ResWalletBanner> selectList(String sql);
	
	void setStatus(List<Long> ids, int status);
	
}
