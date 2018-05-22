package cn.linkmore.security.service.impl;
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
import cn.linkmore.security.dao.cluster.ClazzClusterMapper;
import cn.linkmore.security.dao.master.ClazzMasterMapper;
import cn.linkmore.security.entity.Clazz;
import cn.linkmore.security.request.ReqCheck;
import cn.linkmore.security.service.ClazzService;
import cn.linkmore.util.DomainUtil;


/**
 * Service实现类 -权限模块 - 类信息
 * @author jiaohanbin
 * @version 2.0
 *
 */
@Service
public class ClazzServiceImpl implements ClazzService {

	@Autowired
	private ClazzClusterMapper clazzClusterMapper;
	@Autowired
	private ClazzMasterMapper clazzMasterMapper;
	
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
		Integer count = this.clazzClusterMapper.count(param);
		param.put("start", pageable.getStart());
		param.put("pageSize", pageable.getPageSize());
		List<Clazz> list = this.clazzClusterMapper.findPage(param);
		return new ViewPage(count,pageable.getPageSize(),list); 
	}

	@Override
	public void save(Clazz clazz) {
		clazz.setCreateTime(new Date());
		this.clazzMasterMapper.save(clazz);
	}
	
	@Override
	public Clazz update(Clazz clazz) {
		this.clazzMasterMapper.update(clazz);
		return clazz;
	}

	@Override
	public int delete(List<Long> ids) {
		return this.clazzMasterMapper.delete(ids); 
	}
	
	@Override
	public Integer check(ReqCheck reqCheck) {
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("property", reqCheck.getProperty());
		param.put("value", reqCheck.getValue());
		param.put("id", reqCheck.getId());
		return this.clazzClusterMapper.check(param); 
	}
}
