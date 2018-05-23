package cn.linkmore.common.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.linkmore.common.dao.cluster.AccessDetailClusterMapper;
import cn.linkmore.common.dao.master.AccessDetailMasterMapper;
import cn.linkmore.common.entity.AccessDetail;
import cn.linkmore.common.request.ReqAccessDetail;
import cn.linkmore.common.service.AccessDetailService;
import cn.linkmore.util.ObjectUtils;
@Service
public class AccessDetailServiceImpl implements AccessDetailService {

	@Resource
	private AccessDetailClusterMapper accessDetailClusterMapper;
	
	@Resource
	private AccessDetailMasterMapper accessDetailMasterMapper;
	
	@Override
	public void appSave(ReqAccessDetail accessDetail) {
		AccessDetail detail = ObjectUtils.copyObject(accessDetail, new AccessDetail());
		detail.setType(0);
		this.accessDetailMasterMapper.insertSelective(detail);
	}

	@Override
	public void miniSave(ReqAccessDetail accessDetail) {
		AccessDetail detail = ObjectUtils.copyObject(accessDetail, new AccessDetail());
		detail.setType(1);
		this.accessDetailMasterMapper.insertSelective(detail);
	}
}
