package cn.linkmore.prefecture.service;

import java.util.List;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.prefecture.request.ReqPrefectureEntrance;

public interface PrefectureEntranceService {
	 /**
	  * 保存
	  * @param ele
	  * @return
	  */
	int save(ReqPrefectureEntrance ele);
	/**
	 * 更新
	 * @param ele
	 * @return
	 */
	int update (ReqPrefectureEntrance ele);
	/**
	 * 删除
	 * @param ids
	 * @return
	 */
	int delete(List<Long> ids);
	
	/**
	 * 分页列表
	 * @param pageable
	 * @return
	 */
	ViewPage findPage(ViewPageable pageable);

}
