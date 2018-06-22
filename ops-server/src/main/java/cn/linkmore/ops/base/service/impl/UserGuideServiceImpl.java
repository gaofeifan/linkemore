package cn.linkmore.ops.base.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.linkmore.account.client.UserGuideClient;
import cn.linkmore.bean.view.Tree;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.ops.base.service.UserGuideService;
import cn.linkmore.ops.request.ReqUserGuide;
import cn.linkmore.util.ObjectUtils;

/**
 * 用户指南--接口实现
 * @author   GFF
 * @Date     2018年6月21日
 * @Version  v2.0
 */
@Service
public class UserGuideServiceImpl implements UserGuideService {

	@Resource
	private UserGuideClient userGuideClient;
	
	@Override
	public void save(ReqUserGuide record) {
		cn.linkmore.account.request.ReqUserGuide guide = ObjectUtils.copyObject(record, new cn.linkmore.account.request.ReqUserGuide());
		this.userGuideClient.save( guide);
	}

	@Override
	public void update(ReqUserGuide record) {
		cn.linkmore.account.request.ReqUserGuide guide = ObjectUtils.copyObject(record, new cn.linkmore.account.request.ReqUserGuide());
		this.userGuideClient.update(guide);
	}

	@Override
	public void delete(List<Long> ids) {
		this.userGuideClient.delete(ids);
	}

	@Override
	public Integer check(String property, String value, Long parentId, Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ViewPage findPage(ViewPageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Tree findTree() {
		// TODO Auto-generated method stub
		return null;
	}

}
