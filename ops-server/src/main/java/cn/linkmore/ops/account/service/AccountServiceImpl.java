package cn.linkmore.ops.account.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.linkmore.account.client.AccountClient;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
@Service
public class AccountServiceImpl implements AccountService {

	@Resource
	private AccountClient accountClient;
	@Override
	public ViewPage findPage(ViewPageable pageable) {
		return this.accountClient.list(pageable);
	}

}
