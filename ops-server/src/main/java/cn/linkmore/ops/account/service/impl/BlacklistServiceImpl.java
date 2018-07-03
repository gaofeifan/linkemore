package cn.linkmore.ops.account.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.linkmore.account.client.BlackListClient;
import cn.linkmore.account.response.ResUserBlacklist;
import cn.linkmore.bean.view.ViewMsg;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.ops.account.service.BlacklistService;
/**
 * 权限管理--接口实现
 * @author   GFF
 * @Date     2018年6月20日
 * @Version  v2.0
 */
@Service
public class BlacklistServiceImpl implements BlacklistService {

	@Resource
	private BlackListClient blackListClient;
	
	@Override
	public ViewPage findPage(ViewPageable pageable) {
		return this.blackListClient.list(pageable);
	}

	@Override
	public Boolean status() {
		return this.blackListClient.status();
	}

	@Override
	public ViewMsg open() {
		return this.blackListClient.open();
	}

	@Override
	public ViewMsg close() {
		return this.blackListClient.close();
	}

	@Override
	public ViewMsg enable(List<Long> list) {
		return this.blackListClient.enable(list);
	}

	@Override
	public List<ResUserBlacklist> findList() {
		return this.blackListClient.findList();
	}
	
	
	
}
