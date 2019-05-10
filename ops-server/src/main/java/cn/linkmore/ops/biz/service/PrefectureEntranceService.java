package cn.linkmore.ops.biz.service;

import java.util.List;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.prefecture.request.ReqPrefectureEntrance;

/**
 * 
 * @Description - 入口元素
 * @Author jiaohanbin
 * @version 
 */
public interface PrefectureEntranceService {

	ViewPage findPage(ViewPageable pageable);

	int save(ReqPrefectureEntrance record);

	int update(ReqPrefectureEntrance record);
	
	int delete(List<Long> ids);

}
