package cn.linkmore.account.service;


import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.linkmore.account.dao.cluster.CommonClusterMapper;
import cn.linkmore.account.dao.master.CommonMasterMapper;
import cn.linkmore.account.entity.Common;
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
	
	/**
	 * @Description  根据调价查询数据
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	public List<?> selectList(Common common){
		List<Map<String,Object>> list = this.commonClusterMapper.findList(common);
		return ObjectUtils.getListBean(list, common.getClazz());
	}

	/**
	 * @Description  根据条件查询数据总数
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	public Integer count(Common common) {
		return this.commonClusterMapper.count(common);
	}
	
	/**
	 * @Description 更新字段值 
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	public void updateColumnValue(Common common){
		this.commonMasterMapper.updateColumnValue(common);
	}
	
	/**
	 * @Description  根据条件更新
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	public void updateList(Common common){
		this.commonMasterMapper.updateList(common);
	}
	
	/**
	 * @Description  通用insertList
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	public <T> void insertList(List<T> list){
		Common common = new Common(list.get(0).getClass());
		common.addInsertList(list);
		this.commonMasterMapper.insertList(common);
	}
	
	/**
	 * @Description  通用insert
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	public <T> void insert(T t){
		 Common common = new Common(t.getClass());
		 common.addInsert(t);
		 this.commonMasterMapper.insert(common);
	}
	
}
