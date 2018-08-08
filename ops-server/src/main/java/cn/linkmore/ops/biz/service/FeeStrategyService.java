package cn.linkmore.ops.biz.service;

import java.util.List;
import java.util.Map;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.prefecture.request.ReqCheck;
import cn.linkmore.prefecture.request.ReqStrategyBase;
import cn.linkmore.prefecture.response.ResStrategyBase;

/**
 * 
 * @Description - 计费策略Service
 * @Author jiaohanbin
 * @version 2.0
 */
public interface FeeStrategyService {

	ViewPage findPage(ViewPageable pageable);

	List<ResStrategyBase> findList();

	int save(ReqStrategyBase record);

	int update(ReqStrategyBase record);

	int delete(List<Long> ids);

	Boolean check(ReqCheck reqCheck);

}
