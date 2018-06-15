package cn.linkmore.coupon.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.linkmore.bean.common.Constants;
import cn.linkmore.bean.view.ViewFilter;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.coupon.dao.cluster.TemplateConditionClusterMapper;
import cn.linkmore.coupon.dao.master.TemplateConditionMasterMapper;
import cn.linkmore.coupon.entity.TemplateCondition;
import cn.linkmore.coupon.request.ReqCheck;
import cn.linkmore.coupon.request.ReqTemplateCondition;
import cn.linkmore.coupon.response.ResTemplateCondition;
import cn.linkmore.coupon.service.TemplateConditionService;
import cn.linkmore.redis.RedisService;
import cn.linkmore.util.DomainUtil;
import cn.linkmore.util.ObjectUtils;

/**
 * 优惠券条件
 * @author jiaohanbin
 * @version 2.0
 *
 */
@Service
public class TemplateConditionServiceImpl implements TemplateConditionService {
	@Autowired
	private TemplateConditionClusterMapper templateConditionClusterMapper;
	
	@Autowired
	private TemplateConditionMasterMapper templateConditionMasterMapper;
	@Resource
	private RedisService redisService;
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
		Integer count = this.templateConditionClusterMapper.count(param);
		param.put("start", pageable.getStart());
		param.put("pageSize", pageable.getPageSize());
		List<ResTemplateCondition> list = this.templateConditionClusterMapper.findPage(param);
		return new ViewPage(count,pageable.getPageSize(),list); 
	}

	@Override
	public int save(ReqTemplateCondition record) {
		record.setCreateTime(new Date());
		if(record.getIsDefault().equals(1)){
			List<ResTemplateCondition> list = this.templateConditionClusterMapper.findConditionList(record.getTemplateId());
			Map<String,Object> param = new HashMap<String,Object>();
			for(ResTemplateCondition condition :list){
				param.put("id", condition.getId());
				param.put("isDefault", 0);
				this.templateConditionMasterMapper.setDefault(param);
			}
		}
		TemplateCondition tempCondition = ObjectUtils.copyObject(record, new TemplateCondition());
		int num = this.templateConditionMasterMapper.save(tempCondition);
		if(StringUtils.isNotBlank(record.getUseTimeJson())){
			redisService.set(Constants.RedisKey.COUPON_TEMPLATE_CONDITION_USETIME.key+record.getId(), record.getUseTimeJson());
		}
		if(StringUtils.isNotBlank(record.getPreIdJson())){
			redisService.set(Constants.RedisKey.COUPON_TEMPLATE_CONDITION_PREIDS.key+record.getId(), record.getPreIdJson());
		}
		return num;
	}
	
	@Override
	public int update(ReqTemplateCondition reqTemplateCondition) {
		TemplateCondition tempCondition = ObjectUtils.copyObject(reqTemplateCondition, new TemplateCondition());
		return this.templateConditionMasterMapper.update(tempCondition);
	}

	@Override
	public int delete(Long id) {
		return this.templateConditionMasterMapper.delete(id); 
	}
	
	@Override
	public Integer check(ReqCheck reqCheck) {
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("property", reqCheck.getProperty());
		param.put("value", reqCheck.getValue());
		param.put("id", reqCheck.getId());
		return this.templateConditionClusterMapper.check(param); 
	}
	
	@Override
	public List<ResTemplateCondition> findConditionList(Long tempId) {
		List<ResTemplateCondition> list = this.templateConditionClusterMapper.findConditionList(tempId);
		return list;
	}

	@Override
	public int setDefault(Long id) {
		Map<String,Object> param = new HashMap<String,Object>();
		ResTemplateCondition tempConditon = templateConditionClusterMapper.findById(id);
		List<ResTemplateCondition> list = this.templateConditionClusterMapper.findConditionList(tempConditon.getTemplateId());
		for(ResTemplateCondition condition :list){
			if(condition.getId().equals(id)){
				param.put("id", id);
				param.put("isDefault", 1);
				return this.templateConditionMasterMapper.setDefault(param);
			}else{
				param.put("id", condition.getId());
				param.put("isDefault", 0);
				return this.templateConditionMasterMapper.setDefault(param);
			}
		}
		return 0;
	}

	@Override
	public ResTemplateCondition findById(Long id) {
		ResTemplateCondition condition = this.templateConditionClusterMapper.findById(id);
		return condition;
	}
}
