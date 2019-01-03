package cn.linkmore.ops.biz.service;

import java.util.List;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.prefecture.request.ReqPrefectureElement;

/**
 * 
 * @Description - 车区元素
 * @Author jiaohanbin
 * @version 
 */
public interface PrefectureElementService {

	ViewPage findPage(ViewPageable pageable);

	int save(ReqPrefectureElement record);

	int update(ReqPrefectureElement record);
	
	int delete(List<Long> ids);

	//Boolean check(ReqCheck reqCheck);

}
