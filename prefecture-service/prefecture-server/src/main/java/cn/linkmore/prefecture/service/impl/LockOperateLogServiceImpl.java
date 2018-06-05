package cn.linkmore.prefecture.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.linkmore.bean.view.ViewFilter;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.prefecture.dao.cluster.LockOperateLogClusterMapper;
import cn.linkmore.prefecture.dao.master.LockOperateLogMasterMapper;
import cn.linkmore.prefecture.entity.LockOperateLog;
import cn.linkmore.prefecture.request.ReqLockOperateLog;
import cn.linkmore.prefecture.request.ReqLockOperateLogExcel;
import cn.linkmore.prefecture.response.ResLockOperateLog;
import cn.linkmore.prefecture.service.LockOperateLogService;
import cn.linkmore.security.client.PersonClient;
import cn.linkmore.security.response.ResPerson;
import cn.linkmore.util.DomainUtil;
import cn.linkmore.util.ObjectUtils;
/**
 * Service实现类 - 锁操作日志信息
 * @author jiaohanbin
 * @version 2.0
 *
 */
@Service
public class LockOperateLogServiceImpl implements LockOperateLogService {
	
	@Autowired
	private LockOperateLogMasterMapper lockOperateLogMasterMapper;
	
	@Autowired
	private LockOperateLogClusterMapper lockOperateLogClusterMapper;
	
	@Autowired
	private PersonClient personClient;

	@Override
	public int save(ReqLockOperateLog reqLockOperateLog) {
		if(reqLockOperateLog != null) {
			LockOperateLog log = new LockOperateLog();
			log.setLockSn(reqLockOperateLog.getLockSn());
			log.setStallId(reqLockOperateLog.getStallId());
			log.setCreateTime(new Date());
			log.setOperation(reqLockOperateLog.getOperation());
			log.setOperatorId(reqLockOperateLog.getOperatorId());
			log.setSource(reqLockOperateLog.getSource());
			log.setStatus(reqLockOperateLog.getStatus());
			return lockOperateLogMasterMapper.save(log);
		}
		return 0;
	}

	@Override
	public int delete(List<Long> ids) {
		return lockOperateLogMasterMapper.delete(ids);
	}

	@Override
	public int update(ReqLockOperateLog reqLock) {
		LockOperateLog record = new LockOperateLog();
		record = ObjectUtils.copyObject(reqLock, record);
		return this.lockOperateLogMasterMapper.update(record);
	}

	@Override
	public ViewPage findPage(ViewPageable pageable) {
		Map<String,Object> param = new HashMap<String,Object>(); 
		List<ViewFilter> filters = pageable.getFilters();
		if(StringUtils.isNotBlank(pageable.getSearchProperty())) {
			param.put(pageable.getSearchProperty(), pageable.getSearchValue());
		}
		if(filters!=null&&filters.size()>0) {
			for(ViewFilter filter:filters) {
				param.put(filter.getProperty(), filter.getValue());
			}
		}
		if(StringUtils.isNotBlank(pageable.getOrderProperty())) {
			param.put("property", DomainUtil.camelToUnderline(pageable.getOrderProperty()));
			param.put("direction", pageable.getOrderDirection());
		}
		Integer count = this.lockOperateLogClusterMapper.count(param);
		List<ResLockOperateLog> list = new ArrayList<ResLockOperateLog>();
		if(count > 0){
			param.put("start", pageable.getStart());
			param.put("pageSize", pageable.getPageSize());
			list = this.lockOperateLogClusterMapper.findPage(param);
			for (ResLockOperateLog bean : list) {
				//来源（1后台，2app电池更换，3app设备管理,4app车区管理）
				Integer source = bean.getSource();
				Long operatorId = bean.getOperatorId();
				if(source==1){
					ResPerson person = personClient.findById(operatorId);
					if(person!=null){
						bean.setOperator(person.getRealname());
					}else{
						bean.setOperator("");
					}
				}
			}
		}
		return new ViewPage(count,pageable.getPageSize(),list);
	}

	@Override
	public List<ResLockOperateLog> findListById(Long id) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("id", id);
		param.put("start", 0);
		param.put("pageSize", 1);
		List<ResLockOperateLog> list = this.lockOperateLogClusterMapper.findPage(param);
		for (ResLockOperateLog bean : list) {
			//来源（1后台，2app电池更换，3app车区管理）
			Integer source = bean.getSource();
			Long operatorId = bean.getOperatorId();
			if(source==1){
				ResPerson person = personClient.findById(operatorId);
				if(person!=null){
					bean.setOperator(person.getRealname());
				}else{
					bean.setOperator("");
				}
			}
		}
		return list;
	}

	@Override
	public List<ResLockOperateLog> exportList(ReqLockOperateLogExcel bean) {
		return this.lockOperateLogClusterMapper.exportList(bean);
	}
}
