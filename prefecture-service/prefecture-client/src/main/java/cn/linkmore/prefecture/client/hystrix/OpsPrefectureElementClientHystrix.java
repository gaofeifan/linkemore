package cn.linkmore.prefecture.client.hystrix;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.prefecture.client.OpsPrefectureElementClient;
import cn.linkmore.prefecture.request.ReqPrefectureElement;
/**
 * 远程调用实现-断熔器 - 车区元素信息
 * @author jiaohanbin
 * @version 1.0
 *
 */ 
@Component
public class OpsPrefectureElementClientHystrix implements OpsPrefectureElementClient {
	private  final Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	public int save(ReqPrefectureElement reqPrefectureEle) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int update(ReqPrefectureElement reqPrefectureEle) {
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
