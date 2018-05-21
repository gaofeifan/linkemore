package cn.linkmore.common.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.linkmore.common.dao.cluster.BaseDictClusterMapper;
import cn.linkmore.common.dao.master.BaseDictMasterMapper;
import cn.linkmore.common.entity.BaseDict;
import cn.linkmore.common.response.ResBaseDict;
import cn.linkmore.common.service.BaseDictService;
import cn.linkmore.util.ObjectUtils;

@Service
public class BaseDictServiceImpl implements BaseDictService {

	@Resource
	private BaseDictClusterMapper baseDictClusterMapper;

	@Resource
	private BaseDictMasterMapper baseDictMasterMapper;

	@Override
	public List<ResBaseDict> selectList(String code) {
		List<BaseDict> list = this.baseDictClusterMapper.selectListByCode(code);
		List<ResBaseDict> ress = new ArrayList<>();
		for (BaseDict dict : list) {
			ResBaseDict baseDict = ObjectUtils.copyObject(dict, new ResBaseDict());
			ress.add(baseDict);
		}
		return ress;
	}
	
	
}
