package cn.linkmore.ops.biz.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.ops.biz.service.PrefectureEntranceService;
import cn.linkmore.prefecture.client.OpsPrefectureEntranceClient;
import cn.linkmore.prefecture.request.ReqPrefectureEntrance;

@Service
public class PrefectureEntranceServiceImpl implements PrefectureEntranceService {

	
	@Autowired
	private OpsPrefectureEntranceClient opsPrefectureEntranceClient;
	
	@Override
	public ViewPage findPage(ViewPageable pageable) {
		return opsPrefectureEntranceClient.list(pageable);
	}

	@Override
	public int save(ReqPrefectureEntrance record) {
		return opsPrefectureEntranceClient.save(record);
	}

	@Override
	public int update(ReqPrefectureEntrance record) {
		return opsPrefectureEntranceClient.update(record);
	}

	@Override
	public int delete(List<Long> ids) {
		return opsPrefectureEntranceClient.delete(ids);
	}

}
