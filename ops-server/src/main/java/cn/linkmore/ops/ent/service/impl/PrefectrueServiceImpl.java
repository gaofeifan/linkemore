package cn.linkmore.ops.ent.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.enterprise.request.ReqAddEntPreture;
import cn.linkmore.enterprise.response.ResEntPrefecture;
import cn.linkmore.ops.ent.service.PrefectrueService;
import cn.linkmore.prefecture.client.PrefectrueClient;
import cn.linkmore.prefecture.response.ResPreList;
/**
 * 企业车区
 * @author   GFF
 * @Date     2018年7月27日
 * @Version  v2.0
 */
@Service
public class PrefectrueServiceImpl implements PrefectrueService {

	@Resource
	private PrefectrueClient prefectrueClient;
	
	@Override
	public ViewPage findPage(ViewPageable pageable) {
		return this.prefectrueClient.findPage(pageable);
	}

	@Override
	public void save(ReqAddEntPreture auth) {
		this.prefectrueClient.save(auth);
	}

	@Override
	public void update(ReqAddEntPreture auth) {
		this.prefectrueClient.update(auth);
	}

	@Override
	public void delete(List<Long> ids) {
		this.prefectrueClient.delete(ids);
	}

	@Override
	public List<ResPreList> findNotCreateEntPre() {
		return this.prefectrueClient.findNotCreateEntPre();
	}

	@Override
	public List<ResEntPrefecture> findAll(Map<String, Object> map) {
		return this.prefectrueClient.findAll(map);
	}
}
