package cn.linkmore.prefecture.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;

import cn.linkmore.bean.view.ViewFilter;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.prefecture.dao.cluster.CodePayPrefectureClusterMapper;
import cn.linkmore.prefecture.dao.master.CodePayPrefectureMasterMapper;
import cn.linkmore.prefecture.request.ReqDep;
import cn.linkmore.prefecture.service.CodePayPrefectureService;
import cn.linkmore.util.DomainUtil;
import cn.linkmore.util.HttpUtil;
import cn.linkmore.util.JsonUtil;

@Service
public class CodePayPrefectureServiceImpl implements CodePayPrefectureService{

	@Autowired
     private	CodePayPrefectureClusterMapper codePayPrefectureClusterMapper;
	
	@Autowired
    private	CodePayPrefectureMasterMapper codePayPrefectureMasterMapper;
	
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
		
		param.put("start", pageable.getStart());
		param.put("pageSize", pageable.getPageSize());
		int count = codePayPrefectureClusterMapper.count(param);
		List<Map<String, Object>> list = codePayPrefectureClusterMapper.findPage(param);

		return new ViewPage(count,pageable.getPageSize(),list); 
	}

	@Override
	public void save(ReqDep reqDep) {
		Map<String,Object> param = new HashMap<String,Object>(); 
		param.put("preId", reqDep.getPreId());
		param.put("type", reqDep.getType());
		codePayPrefectureMasterMapper.deleteOld(param);
		this.codePayPrefectureMasterMapper.save(reqDep);
	}

	@Override
	public ViewPage findRecordPage(ViewPageable pageable) {
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
		/*if(StringUtils.isNotBlank(pageable.getOrderProperty())) {
			param.put("property", DomainUtil.camelToUnderline(pageable.getOrderProperty()));
			param.put("direction", pageable.getOrderDirection());
		}*/
		
		param.put("start", pageable.getStart());
		param.put("pageSize", pageable.getPageSize());
		int count = codePayPrefectureClusterMapper.countRecord(param);
		List<Map<String, Object>> list = codePayPrefectureClusterMapper.findRecordPage(param);

		return new ViewPage(count,pageable.getPageSize(),list); 
	}

	@Override
	public List<Map<String, Object>> payList(String orderNo) {
		return codePayPrefectureClusterMapper.payList(orderNo);
	}

	@Override
	public void delete(String preId) {
	    codePayPrefectureMasterMapper.delete(preId);
	}

	@Override
	public Map<String, Object> down(String preId) {
		Map<String,Object> param = new HashMap<String,Object>(); 
		//String url = "http://test.linkmoreparking.cn/api/order/h5/d?preId="+preId;
		String url = "http://api.linkmoreparking.com/api/order/h5/d?preId="+preId;
		param.put("url", url);
		return param;
	}

	@Override
	public List<Map<String, Object>> selectAll() {
		Map<String, Object> parameters = new HashMap<>();
		//String ur= "http://192.168.1.133:8086/api/select-park";
		String ur= "http://gateapi.linkmoreparking.com/api/select-park";
		String response = HttpUtil.sendJson(ur, JsonUtil.toJson(parameters));
		Map<String, Object> order = new HashMap<>();
		order = JsonUtil.toObject(response, order.getClass());
		List<Map<String, Object>>  llist = new ArrayList<>();
		llist	=(List<Map<String, Object>>) order.get("dataList");
		return llist;
	}
	
	
	
	

}
