package cn.linkmore.account.service;

import java.util.List;

import cn.linkmore.account.entity.UserBlacklist;
import cn.linkmore.account.response.ResUserBlacklist;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;

public interface BlacklistService {

	/**
	 * 分页查询
	 * @param pageable
	 * @return
	 */
	ViewPage findPage(ViewPageable pageable);

	/**
	 * 专区列表
	 * @return
	 */
	/*List<PrefectureBean> findPreList();*/
	
	/**
	 * 解锁
	 * @param list
	 */
	void enable(List<Long> list);
	
	/**
	 * 取列表
	 * @return
	 */
	List<ResUserBlacklist> findList();

	void reset();

}
