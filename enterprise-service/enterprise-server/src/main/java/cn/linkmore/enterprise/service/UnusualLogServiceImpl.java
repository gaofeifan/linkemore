package cn.linkmore.enterprise.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.linkmore.common.client.UnusualLogClient;
import cn.linkmore.common.request.ReqUnusualLog;

/**
 * @author   GFF
 * @Date     2018年9月1日
 * @Version  v2.0
 */
@Service
public class UnusualLogServiceImpl implements UnusualLogService {
	@Resource
	private UnusualLogClient unusualLogClient;
	@Override
	public void insert(ReqUnusualLog unusualLog) {
		this.unusualLogClient.insert(unusualLog);
	}

	
}
