package cn.linkmore.common.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.linkmore.bean.view.ViewOrder.Direction;
import cn.linkmore.common.dao.cluster.BaseAppVersionClusterMapper;
import cn.linkmore.common.dao.cluster.UserVersionClusterMapper;
import cn.linkmore.common.dao.master.UserVersionMasterMapper;
import cn.linkmore.common.entity.BaseAppVersion;
import cn.linkmore.common.entity.Common;
import cn.linkmore.common.entity.UserVersion;
import cn.linkmore.common.request.ReqVersion;
import cn.linkmore.common.response.ResVersionBean;
import cn.linkmore.common.service.BeanVersionService;
import cn.linkmore.common.service.CommonService;
import cn.linkmore.util.ObjectUtils;
/**
 * 版本实现类
 * @author   GFF
 * @Date     2018年5月17日
 * @Version  v2.0
 */
@Service
public class BeanVersionServiceImpl implements BeanVersionService {

	@Resource
	private CommonService commonService;
	@Resource
	private UserVersionClusterMapper versionClusterMapper;
	@Resource
	private UserVersionMasterMapper versionMasterMapper;
	@Resource
	private BaseAppVersionClusterMapper baseAppVersionClusterMapper;
	
	@Override
	public ResVersionBean currentAppVersion(Integer appType) {
		List<ResVersionBean> res = this.baseAppVersionClusterMapper.findByTypeAnStatus(appType,1);
		return res.get(0);
	}
	
	public List<BaseAppVersion> findList(Common common){
		@SuppressWarnings("unchecked")
		List<BaseAppVersion> list = (List<BaseAppVersion>) commonService.selectList(common );
		return list;
	}

	@Override
	public void report(ReqVersion vrb) {
		boolean falg = false;
		UserVersion version = this.versionClusterMapper.findById(vrb.getUserId());
		if(version != null) {
			falg = true;
		}
		version = ObjectUtils.copyObject(vrb, new UserVersion());
		version.setCommitTime(new Date());
		if(falg) {
			this.versionMasterMapper.updateById(version);
			return;
		}
		this.versionMasterMapper.insert(version);
		
	}
	
	
	
}

