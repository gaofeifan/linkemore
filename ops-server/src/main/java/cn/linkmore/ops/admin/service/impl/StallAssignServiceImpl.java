package cn.linkmore.ops.admin.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.ops.admin.service.StallAssignService;
import cn.linkmore.prefecture.client.StallAssignClient;
import cn.linkmore.prefecture.response.ResPreList;
import cn.linkmore.prefecture.response.ResStall;

/**
 * Service实现类 - 车位指定记录
 * @author liwenlong
 * @version 1.0
 *
 */
@Service
public class StallAssignServiceImpl implements StallAssignService {
	
	@Autowired
	private StallAssignClient stallAssignClient; 
	
	@Override
	public ViewPage findPage(ViewPageable pageable) {
		return this.stallAssignClient.list(pageable);
	}
	
	@Override
	public List<ResPreList> findPrefectureList() {
		return this.stallAssignClient.findPrefectureList();
	}

	@Override
	public List<ResStall> findStallList(Map<String, Object> param) {
		return this.stallAssignClient.stallList(param);
	}
}
