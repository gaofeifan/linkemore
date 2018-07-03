package cn.linkmore.account.service;

import java.util.List;

import cn.linkmore.account.request.ReqWhitelist;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;

/**
 * 权限模块 - 类记录 -- 接口
 * @author   GFF
 * @Date     2018年6月22日
 * @Version  v2.0
 */
public interface WhitelistService {

	/**
	 * @Description  校验
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	Integer check(String property, String value, Long id);

	/**
	 * @Description  保存
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	void save(ReqWhitelist record);

	/**
	 * @Description  更新
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	void update(ReqWhitelist record);

	/**
	 * @Description  批量删除
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	void delete(List<Long> ids);

	/**
	 * @Description  分页查询
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	ViewPage findPage(ViewPageable pageable);

}
