package cn.linkmore.prefecture.client.hystrix;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.prefecture.client.OpsPrefectureEntranceClient;
import cn.linkmore.prefecture.request.ReqPrefectureEntrance;
/**
 * 远程调用实现-断熔器 - 入口信息
 * @author jiaohanbin
 * @version 1.0
 *
 */ 
@Component
public class OpsPrefectureEntranceClientHystrix implements OpsPrefectureEntranceClient {
	private  final Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	public int save(ReqPrefectureEntrance reqPrefectureEle) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int update(ReqPrefectureEntrance reqPrefectureEle) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(List<Long> ids) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public ViewPage list(ViewPageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	
}
