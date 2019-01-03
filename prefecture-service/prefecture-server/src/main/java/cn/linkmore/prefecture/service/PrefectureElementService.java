package cn.linkmore.prefecture.service;

import java.util.List;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.prefecture.request.ReqPrefectureElement;

public interface PrefectureElementService {
	 /**
	  * 保存
	  * @param ele
	  * @return
	  */
	int save(ReqPrefectureElement ele);
	/**
	 * 更新
	 * @param ele
	 * @return
	 */
	int update (ReqPrefectureElement ele);
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
