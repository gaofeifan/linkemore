package cn.linkmore.common.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.linkmore.common.controller.app.response.ResDonwLockError;
import cn.linkmore.common.dao.cluster.BaseDictClusterMapper;
import cn.linkmore.common.dao.master.BaseDictMasterMapper;
import cn.linkmore.common.entity.BaseDict;
import cn.linkmore.common.request.ReqBaseDict;
import cn.linkmore.common.response.ResBaseDict;
import cn.linkmore.common.service.BaseDictService;
import cn.linkmore.util.ObjectUtils;

/**
 * @author   GFF
 * @Date     2018年5月21日
 * @Version  v2.0
 */
@Service
public class BaseDictServiceImpl implements BaseDictService {

	public static final String DOWN_LOCK_ERROR_CAUSE = "order_stall_down_error";
	public static final String DOWN_CAUSE = "cause_down";	
	public static final String CAUSE_FAULT = "cause_fault";	
	public static final String CAUSE_HANG = "cause_hang";	
	public static final String CAUSE_CLOSE = "cause_close";	
	@Resource
	private BaseDictClusterMapper baseDictClusterMapper;

	@Resource
	private BaseDictMasterMapper baseDictMasterMapper;

	@Override
	public List<ResBaseDict> findList(String code) {
		List<ResBaseDict> list = this.baseDictClusterMapper.findListByCode(code);
		return list;
	}

	@Override
	public void save(ReqBaseDict baseDict) {
		BaseDict dict = ObjectUtils.copyObject(baseDict, new BaseDict());
		this.baseDictMasterMapper.insertSelective(dict);
	}

	@Override
	public void deleteById(Long id) {
		this.baseDictMasterMapper.deleteById(id);
	}

	@Override
	public void updateByIdSelective(ReqBaseDict baseDict) {
		BaseDict dict = ObjectUtils.copyObject(baseDict, new BaseDict());
		this.baseDictMasterMapper.updateByIdSelective(dict);
	}

	@Override
	public ResBaseDict find(Long id) {
		return this.baseDictClusterMapper.find(id);
	}

	@Override
	public List<ResDonwLockError> selectLockDownErrorCause() {
		List<ResBaseDict> list = this.findList(DOWN_LOCK_ERROR_CAUSE);
		List<ResDonwLockError> resultList = new ArrayList<>();
		for (ResBaseDict resBaseDict : list) {
			ResDonwLockError lockError = ObjectUtils.copyObject(resBaseDict,new ResDonwLockError());
			resultList.add(lockError);
		}
		return resultList;
	}

	@Override
	public List<ResBaseDict> findListByCodes(List<String> codes) {
		return this.baseDictClusterMapper.findListByCodes(codes);
	}

	@Override
	public List<ResBaseDict> downCause() {
		return this.findList(DOWN_CAUSE);
	}

	@Override
	public List<ResBaseDict> causeHang() {
		return this.findList(CAUSE_HANG);
	}

	@Override
	public List<ResBaseDict> causeClose() {
		return this.findList(CAUSE_CLOSE);
	}

	@Override
	public List<ResDonwLockError> findLockFaultCause() {
		List<ResBaseDict> list = this.findList(DOWN_LOCK_ERROR_CAUSE);
		List<ResDonwLockError> resultList = new ArrayList<>();
		for (ResBaseDict resBaseDict : list) {
			ResDonwLockError lockError = ObjectUtils.copyObject(resBaseDict,new ResDonwLockError());
			resultList.add(lockError);
		}
		return resultList;
	}

	
}
