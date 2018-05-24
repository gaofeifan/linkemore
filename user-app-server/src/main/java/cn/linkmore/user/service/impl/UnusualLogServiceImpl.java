package cn.linkmore.user.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.linkmore.common.client.UnusualLogClient;
import cn.linkmore.common.request.ReqUnusualLog;
import cn.linkmore.user.service.UnusualLogService;
import cn.linkmore.util.ObjectUtils;
/**
 * app版本上报实现类
 * @author   GFF
 * @Date     2018年5月23日
 * @Version  v2.0
 */
@Service
public class UnusualLogServiceImpl implements UnusualLogService {

	@Resource
	private UnusualLogClient unusualLogClient;

	@Override
	@Transactional
	public void insert(cn.linkmore.user.request.ReqUnusualLog unusualLog) {
		ReqUnusualLog log = ObjectUtils.copyObject(unusualLog, new ReqUnusualLog());
		this.unusualLogClient.insert(log);
		
	}
	
	
}
