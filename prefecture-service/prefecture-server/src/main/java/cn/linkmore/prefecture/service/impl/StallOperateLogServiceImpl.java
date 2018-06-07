package cn.linkmore.prefecture.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.linkmore.bean.view.ViewFilter;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.prefecture.dao.cluster.StallOperateLogClusterMapper;
import cn.linkmore.prefecture.dao.master.StallOperateLogMasterMapper;
import cn.linkmore.prefecture.entity.StallOperateLog;
import cn.linkmore.prefecture.request.ReqStallOperateLog;
import cn.linkmore.prefecture.request.ReqStallOperateLogExcel;
import cn.linkmore.prefecture.response.ResStallOperateLog;
import cn.linkmore.prefecture.service.StallOperateLogService;
import cn.linkmore.security.client.PersonClient;
import cn.linkmore.security.response.ResPerson;
import cn.linkmore.util.DomainUtil;
import cn.linkmore.util.ObjectUtils;
/**
 * Service实现类 - 车位上下线
 * @author jiaohanbin
 * @version 2.0
 */
@Service
public class StallOperateLogServiceImpl implements StallOperateLogService {
	@Autowired
	private StallOperateLogClusterMapper operateLogClusterMapper;
	@Autowired
	private StallOperateLogMasterMapper operateLogMasterMapper;
	@Autowired
	private PersonClient personClient;
	
	private  final Logger log = LoggerFactory.getLogger(this.getClass());
	/*
	 * 列表
	 */
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
		Integer count = this.operateLogClusterMapper.count(param);
		List<ResStallOperateLog> list = new ArrayList<ResStallOperateLog>();
		if(count > 0){
			param.put("start", pageable.getStart());
			param.put("pageSize", pageable.getPageSize());
			list = this.operateLogClusterMapper.findPage(param);
			for (ResStallOperateLog bean : list) {
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
		}
		return new ViewPage(count,pageable.getPageSize(),list);
	}
	
	@Override
	public List<ResStallOperateLog> findListById(Long id) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("id", id);
		param.put("start", 0);
		param.put("pageSize", 1);
		List<ResStallOperateLog> list = this.operateLogClusterMapper.findPage(param);
		for (ResStallOperateLog bean : list) {
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
	public int delete(List<Long> ids) {
		return this.operateLogMasterMapper.delete(ids);
	}

	@Override
	public int save(ReqStallOperateLog reqStallOperateLog) {
		StallOperateLog stallOperateLog = new StallOperateLog();
		stallOperateLog = ObjectUtils.copyObject(reqStallOperateLog, stallOperateLog);
		return this.operateLogMasterMapper.save(stallOperateLog);
	}

	@Override
	public int update(ReqStallOperateLog reqStallOperateLog) {
		StallOperateLog stallOperateLog = new StallOperateLog();
		stallOperateLog = ObjectUtils.copyObject(reqStallOperateLog, stallOperateLog);
		return this.operateLogMasterMapper.update(stallOperateLog);
	}

	/*
	 * 导出
	 */
	@Override
	public List<ResStallOperateLog> exportList(
			ReqStallOperateLogExcel excelBean) {
		List<ResStallOperateLog> list = this.operateLogClusterMapper.findExportList(excelBean);
		log.info("export list size,{}",list.size());
		for (ResStallOperateLog bean : list) {
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

}
