package cn.linkmore.enterprise.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import cn.linkmore.bean.view.ViewFilter;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.enterprise.dao.cluster.EntRentedRecordClusterMapper;
import cn.linkmore.enterprise.dao.master.EntRentedRecordMasterMapper;
import cn.linkmore.enterprise.entity.EntRentedRecord;
import cn.linkmore.enterprise.request.ReqRentedRecord;
import cn.linkmore.enterprise.response.ResRentedRecord;
import cn.linkmore.enterprise.service.RentedRecordService;
import cn.linkmore.util.DomainUtil;
/**
 * 接口实现
 * @author   GFF
 * @Date     2018年7月30日
 * @Version  v2.0
 */
@Service
public class RentedRecordServiceImpl implements RentedRecordService {

	@Resource
	private EntRentedRecordClusterMapper entRentedRecordClusterMapper;
	@Resource
	private EntRentedRecordMasterMapper entRentedRecordMasterMapper; 
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Override
	public ViewPage findList(ViewPageable pageable) {
		Map<String, Object> param = new HashMap<String, Object>();
		List<ViewFilter> filters = pageable.getFilters();
		if (StringUtils.isNotBlank(pageable.getSearchProperty())) {
			param.put(pageable.getSearchProperty(), pageable.getSearchValue());
		}
		if (filters != null && filters.size() > 0) {
			for (ViewFilter filter : filters) {
				param.put(filter.getProperty(), filter.getValue());
			}
		}
		if (StringUtils.isNotBlank(pageable.getOrderProperty())) {
			param.put("property", DomainUtil.camelToUnderline(pageable.getOrderProperty()));
			param.put("direction", pageable.getOrderDirection());
		}
		Integer count = this.entRentedRecordClusterMapper.count(param);
		param.put("start", pageable.getStart());
		param.put("pageSize", pageable.getPageSize());
		List<EntRentedRecord> list = this.entRentedRecordClusterMapper.findPage(param);
		Map<String,Long> rentRecordParam = null;
		if(CollectionUtils.isNotEmpty(list)) {
			for(EntRentedRecord record: list) {
				rentRecordParam = new HashMap<String,Long>();
				rentRecordParam.put("stallId", record.getStallId());
				rentRecordParam.put("userId", record.getUserId());
				Integer useCount = this.entRentedRecordClusterMapper.findUseCountByMonth(rentRecordParam);
				record.setUseCount(useCount);
			}
		}
		return new ViewPage(count, pageable.getPageSize(), list);
	}

	@Override
	public List<ResRentedRecord> exportList(ReqRentedRecord bean) {
		List<ResRentedRecord> list = this.entRentedRecordClusterMapper.exportList(bean);
		return list;
	}

	@Override
	public List<EntRentedRecord> findLastByStallIds(List<Long> stalls) {
		return this.entRentedRecordClusterMapper.findLastByStallIds(stalls);
	}

	@Override
	public List<EntRentedRecord> findParkingRecord(Integer pageNo,Long stallId) {
		if(pageNo == null || pageNo == 0) {
			pageNo = 1;
		}
		pageNo = pageNo*10-10;
		return this.entRentedRecordClusterMapper.findParkingRecord(pageNo,stallId);
	}

	@Override
	public void updateRecordBatch(List<EntRentedRecord> chengsRecord) {
		this.entRentedRecordMasterMapper.updateRecordBatch(chengsRecord);
	}

	
	
	
	
}
