package cn.linkmore.ops.biz.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.ops.biz.service.PreGroupService;
import cn.linkmore.prefecture.client.PrefectureGroupClient;
import cn.linkmore.prefecture.request.ReqCheck;
import cn.linkmore.prefecture.request.ReqPrefectureGroup;

@Service
public class PreGroupServiceImpl implements PreGroupService {

	@Autowired
	private PrefectureGroupClient preGroupClient;

	@Override
	public Boolean check(ReqCheck reqCheck) {
		return this.preGroupClient.check(reqCheck);
	}

	@Override
	public int save(ReqPrefectureGroup group) {
		return this.preGroupClient.save(group);
	}

	@Override
	public ViewPage findPage(ViewPageable pageable) {
		return this.preGroupClient.list(pageable);
	}

	@Override
	public int start(List<Long> ids) {
		return this.preGroupClient.down(ids);
	}

	@Override
	public int down(List<Long> ids) {
		return this.preGroupClient.down(ids);
	}

	@Override
	public int delete(List<Long> ids) {
		return this.preGroupClient.delete(ids);
	}

}
