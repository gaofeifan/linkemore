package cn.linkmore.user.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.linkmore.common.client.UnusualLogClient;
import cn.linkmore.common.request.ReqUnusualLog;
import cn.linkmore.user.service.UnusualLogService;
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
	public void insert(ReqUnusualLog unusualLog) {
		this.unusualLogClient.insert(unusualLog);
		
	}
	
	
}
