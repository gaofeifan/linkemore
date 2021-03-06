package cn.linkmore.ops.base.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.linkmore.bean.view.Tree;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.common.client.DistrictClient;
import cn.linkmore.ops.base.service.DistrictService;
import cn.linkmore.ops.request.ReqCheck;
import cn.linkmore.ops.request.ReqDistrict;
import cn.linkmore.util.ObjectUtils;

/**
 * 区域接口实现
 * @author   GFF
 * @Date     2018年5月28日
 * @Version  v2.0
 */
@Service
public class DistrictServiceImpl implements DistrictService {

	@Resource
	private DistrictClient districtClient;
	
	@Override
	public void delete(List<Long> ids) {
		this.districtClient.delete(ids);
	}

	@Override
	public void update(ReqDistrict record) {
		cn.linkmore.common.request.ReqDistrict district = ObjectUtils.copyObject(record, new cn.linkmore.common.request.ReqDistrict());
		this.districtClient.update(district);
	}

	@Override
	public void save(ReqDistrict record) {
		cn.linkmore.common.request.ReqDistrict district = ObjectUtils.copyObject(record, new cn.linkmore.common.request.ReqDistrict());
		this.districtClient.save(district);
	}

	@Override
	public Boolean check(String property, String value, Long id) {
		cn.linkmore.common.request.ReqCheck check = new cn.linkmore.common.request.ReqCheck();
		check.setId(id);
		check.setProperty(property);
		check.setValue(value);
		return this.districtClient.check(check);
	}

	@Override
	public ViewPage findPage(ViewPageable pageable) {
		return this.districtClient.findPage(pageable);
	}

	@Override
	public Tree findTree() {
		return this.districtClient.findTree();
	}

	
}
