package cn.linkmore.common.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.linkmore.common.dao.cluster.UnusualLogClusterMapper;
import cn.linkmore.common.dao.cluster.UnusualLogContentClusterMapper;
import cn.linkmore.common.dao.master.UnusualLogContentMasterMapper;
import cn.linkmore.common.dao.master.UnusualLogMasterMapper;
import cn.linkmore.common.entity.UnusualLog;
import cn.linkmore.common.entity.UnusualLogContent;
import cn.linkmore.common.request.ReqUnusualLog;
import cn.linkmore.common.service.UnusualLogService;
import cn.linkmore.util.ObjectUtils;

/**
 * app异常日志接口实现类
 * @author   GFF
 * @Date     2018年5月23日
 * @Version  v2.0
 */
@Service
public class UnusualLogServiceImpl implements UnusualLogService {

	@Resource
	private UnusualLogClusterMapper unusualLogClusterMapper;
	@Resource
	private UnusualLogMasterMapper unusualLogMasterMapper;
	@Resource
	private UnusualLogContentMasterMapper unusualLogContentMasterMapper;
	@Resource
	private UnusualLogContentClusterMapper unusualLogContentClusterMapper;
	
	@Override
	public void insert(ReqUnusualLog unusualLog) {
		UnusualLog log = ObjectUtils.copyObject(unusualLog, new UnusualLog());
		this.unusualLogMasterMapper.insertSelective(log);
		UnusualLogContent record = new UnusualLogContent();
		record.setContent(unusualLog.getContent());
		record.setLogId(log.getId());
		this.unusualLogContentMasterMapper.insert(record );
	}

	@Override
	public void insert(cn.linkmore.common.controller.app.request.ReqUnusualLog unusualLog) {
		ReqUnusualLog log = ObjectUtils.copyObject(unusualLog, new ReqUnusualLog());
		this.insert(log);
	}
	
	
	
}
