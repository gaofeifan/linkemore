package cn.linkmore.ops.security.service.impl;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.linkmore.bean.view.Tree;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.ops.security.request.ReqCheck;
import cn.linkmore.ops.security.request.ReqDict;
import cn.linkmore.ops.security.response.ResDict;
import cn.linkmore.ops.security.service.DictService;
import cn.linkmore.security.client.DictClient;
import cn.linkmore.util.ObjectUtils;

/**
 * Service实现类 -权限模块 - 类信息
 * @author jiaohanbin
 * @version 2.0
 *
 */
@Service
public class DictServiceImpl implements DictService {

	@Autowired
	private DictClient dictClient;

	@Override
	public ViewPage findPage(ViewPageable pageable) {
		return this.dictClient.list(pageable);
	}

	@Override
	public int save(ReqDict reqDict) {
		cn.linkmore.security.request.ReqDict reqDictSecurity = new cn.linkmore.security.request.ReqDict();
		reqDictSecurity = ObjectUtils.copyObject(reqDict, reqDictSecurity);
		return this.dictClient.save(reqDictSecurity);
	}

	@Override
	public int update(ReqDict reqDict) {
		cn.linkmore.security.request.ReqDict reqDictSecurity = new cn.linkmore.security.request.ReqDict();
		reqDictSecurity = ObjectUtils.copyObject(reqDict, reqDictSecurity);
		return this.dictClient.update(reqDictSecurity);
		
	}

	@Override
	public int delete(List<Long> ids) {
		return this.dictClient.delete(ids);
	}

	@Override
	public Boolean check(ReqCheck reqCheck) {
		cn.linkmore.security.request.ReqCheck reqCheckSecurity = new cn.linkmore.security.request.ReqCheck();
		reqCheckSecurity = ObjectUtils.copyObject(reqCheck, reqCheckSecurity);
		return this.dictClient.check(reqCheckSecurity);
	}

	@Override
	public Tree findTree() {
		return this.dictClient.tree();
	}

	@Override
	public List<ResDict> findByGroupCode(String code) {
		List<ResDict> resDictList = new ArrayList<ResDict>();
		ResDict resDict = null;
		List<cn.linkmore.security.response.ResDict> list = this.dictClient.groupList(code);
		for(cn.linkmore.security.response.ResDict dict :list) {
			resDict = new ResDict();
			resDict = ObjectUtils.copyObject(dict, resDict);
			resDictList.add(resDict);
		}
		return resDictList;
	}
	
	
}
