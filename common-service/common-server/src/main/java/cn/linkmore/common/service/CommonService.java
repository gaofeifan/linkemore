package cn.linkmore.common.service;


import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.linkmore.common.dao.cluster.CommonClusterMapper;
import cn.linkmore.common.dao.master.CommonMasterMapper;
import cn.linkmore.common.entity.Common;
import cn.linkmore.util.ObjectUtils;


/**
 * @Description  通用service(现有方法可使用)
 * @author  GFF
 * @Date     2018年5月7日
 *
 */
@Service
public class CommonService {
	@Autowired
	private CommonMasterMapper commonMasterMapper;
	@Autowired
	private CommonClusterMapper commonClusterMapper;
	public List<?> selectList(Common common){
		List<Map<String,Object>> list = this.commonClusterMapper.findList(common);
		return ObjectUtils.getListBean(list, common.getClazz());
	}

	public Integer count(Common common) {
		return this.commonClusterMapper.count(common);
	}
	
	public void updateColumnValue(Common common){
		this.commonMasterMapper.updateColumnValue(common);
	}
	
	public void updateList(Common common){
		this.commonMasterMapper.updateList(common);
	}
	
	public <T> void insertList(List<T> list){
		Common common = new Common(list.get(0).getClass());
		common.addInsertList(list);
		this.commonMasterMapper.insertList(common);
	}
	
	public <T> void insert(T t){
		 Common common = new Common(t.getClass());
		 common.addInsert(t);
		 this.commonMasterMapper.insert(common);
	}
	
}
