package cn.linkmore.ops.biz.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.ops.biz.service.PrefectureElementService;
import cn.linkmore.prefecture.client.OpsPrefectureElementClient;
import cn.linkmore.prefecture.request.ReqPrefectureElement;
import cn.linkmore.prefecture.response.ResStrategyTime;

@Service
public class PrefectureElementServiceImpl implements PrefectureElementService {

	
	@Autowired
	private OpsPrefectureElementClient opsPrefectureElementClient;
	
	@Override
	public ViewPage findPage(ViewPageable pageable) {
		return opsPrefectureElementClient.list(pageable);
	}

	@Override
	public int save(ReqPrefectureElement record) {
		return opsPrefectureElementClient.save(record);
	}

	@Override
	public int update(ReqPrefectureElement record) {
		return opsPrefectureElementClient.update(record);
	}

	@Override
	public int delete(List<Long> ids) {
		return opsPrefectureElementClient.delete(ids);
	}
	
	/*@Override
	public ResStrategyTime selectByPrimaryKey(Long id) {
		return opsPrefectureElementClient.selectByPrimaryKey(id);
	}*/

}
