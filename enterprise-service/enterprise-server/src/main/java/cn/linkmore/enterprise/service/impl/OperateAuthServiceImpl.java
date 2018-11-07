package cn.linkmore.enterprise.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import cn.linkmore.bean.view.Tree;
import cn.linkmore.bean.view.ViewFilter;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.enterprise.dao.cluster.EntAuthPreClusterMapper;
import cn.linkmore.enterprise.dao.cluster.EntAuthStallClusterMapper;
import cn.linkmore.enterprise.dao.cluster.EntOperateAuthClusterMapper;
import cn.linkmore.enterprise.dao.master.EntAuthPreMasterMapper;
import cn.linkmore.enterprise.dao.master.EntAuthStallMasterMapper;
import cn.linkmore.enterprise.dao.master.EntOperateAuthMasterMapper;
import cn.linkmore.enterprise.entity.EntAuthPre;
import cn.linkmore.enterprise.entity.EntAuthStall;
import cn.linkmore.enterprise.entity.EntOperateAuth;
import cn.linkmore.enterprise.entity.EntPrefecture;
import cn.linkmore.enterprise.request.ReqOperateAuth;
import cn.linkmore.enterprise.request.ReqOperateBind;
import cn.linkmore.enterprise.request.ReqStallList;
import cn.linkmore.enterprise.response.ResEntPrefecture;
import cn.linkmore.enterprise.response.ResEnterprise;
import cn.linkmore.enterprise.service.EntPreService;
import cn.linkmore.enterprise.service.EnterpriseService;
import cn.linkmore.enterprise.service.OperateAuthService;
import cn.linkmore.prefecture.client.OpsStallClient;
import cn.linkmore.prefecture.client.PrefectrueClient;
import cn.linkmore.prefecture.client.PrefectureClient;
import cn.linkmore.prefecture.response.ResPre;
import cn.linkmore.prefecture.response.ResStall;
import cn.linkmore.util.DomainUtil;

/**
 * 操作权限实现
 * @author   GFF
 * @Date     2018年7月20日
 * @Version  v2.0
 */
@Service
public class OperateAuthServiceImpl implements OperateAuthService {
	@Resource
	private EnterpriseService enterpriseService;
	@Resource
	private EntOperateAuthClusterMapper entOperateAuthClusterMapper;
	@Resource
	private EntOperateAuthMasterMapper entOperateAuthMasterMapper;
	@Resource
	private EntAuthPreMasterMapper entAuthPreMasterMapper;
	@Resource
	private EntAuthPreClusterMapper entAuthPreClusterMapper;
	@Resource
	private EntAuthStallClusterMapper entAuthStallClusterMapper;
	@Resource
	private EntAuthStallMasterMapper entAuthStallMasterMapper;
	@Resource
	private EntPreService entPreService;
	@Resource
	private PrefectureClient prefectureClient;
	@Resource
	private OpsStallClient stallClient;
	@Override
	public List<Tree> tree(Long entId) {
		List<ResEnterprise> list = null;
		List<ResEntPrefecture> preList = null;
		Map<String, Object> map = new HashMap<>();
		List<Tree> roots = new ArrayList<>();
		if(entId == 0) {
			list = this.enterpriseService.findList(null);
			preList = this.entPreService.findList(null);
		}else {
			Map<String,Object> param = new HashMap<>();
			param.put("id", entId);
			list = this.enterpriseService.findList(param);
			if(list == null || list.size() == 0) {
				return roots;
			}
			param.put("entId", entId);
			preList = this.entPreService.findList(param);
		}
		List<Long> ids = preList.stream().map(p -> p.getPreId()).collect(Collectors.toList());
		map.put("preIds", ids);
		map.put("category", 2);
		List<ResPre> byIds = this.prefectureClient.findPreByIds(map );
		if(byIds == null) {
			return roots;
		}
		ids = byIds.stream().map(b -> b.getId()).collect(Collectors.toList());
		List<Tree> pchildren = null;
		List<Tree> children = null;
		List<ResStall> stallList = stallClient.findStallList(new HashMap<String, Object>());
		Tree root = null;
		Tree chi = null;
		Tree pchi = null;
		for (ResEnterprise ent : list) {
			root = new Tree();
			root.setName(ent.getName());
			root.setId(ent.getId().toString());
			root.setCode(ent.getId().toString());
			root.setOpen(true);
			root.setmId(ent.getId().toString());
			root.setpId("0");
			children = new ArrayList<>();
			for (ResEntPrefecture entPrefecture : preList) {
				if(entPrefecture.getEntId().equals(ent.getId()) && ids.contains(entPrefecture.getPreId())) {
					chi = new Tree();
					chi.setName(entPrefecture.getPreName());
					chi.setId(entPrefecture.getPreId().toString());
					chi.setIsParent(false);
					chi.setCode(entPrefecture.getPreId().toString());
					chi.setOpen(true);
					chi.setmId(entPrefecture.getPreId().toString());
					chi.setpId(ent.getId().toString());
					children.add(chi);
					pchildren = new ArrayList<>();
					for (ResStall stall : stallList) {
						if(stall.getPreId() == entPrefecture.getPreId()) {
							pchi = new Tree();
							pchi.setName(stall.getStallName());
							pchi.setId(stall.getId().toString());
							pchi.setIsParent(false);
							pchi.setCode(stall.getId().toString());
							pchi.setOpen(true);
							pchi.setmId(stall.getId().toString());
							pchi.setpId(entPrefecture.getPreId().toString());
							pchildren.add(pchi);
						}
					}
					if(pchildren.size() != 0) {
						chi.setChildren(pchildren);
					}
				}
			}
			if(children.size() != 0) {
				root.setChildren(children);
				roots.add(root);
			}
		}
		return roots;
	}
	
	@Override
	public void save(ReqOperateAuth operateAuth) {
		this.entOperateAuthMasterMapper.saveReq(operateAuth);
	}

	@Override
	public void update(ReqOperateAuth operateAuth) {
		this.entOperateAuthMasterMapper.updateByIdSelective(operateAuth);
	}

	@Override
	public void stop(Long id) {
		this.updateStatus(id, (short)0);
	}

	@Override
	public void start(Long id) {
		this.updateStatus(id, (short)1);
	}
	
	private void updateStatus(Long id,Short status) {
		Map<String, Object> map = new HashMap<>();
		map.put("id",id);
		map.put("status",status);
		this.entOperateAuthMasterMapper.updateStatus(map);
	}

	@Override
	public void delete(Long id) {
		this.entOperateAuthMasterMapper.deleteById(id);
	}

	@Override
	public ViewPage findPage(ViewPageable pageable) {
		Map<String,Object> param = new HashMap<String,Object>(); 
		List<ViewFilter> filters = pageable.getFilters();
		if(StringUtils.isNotBlank(pageable.getSearchProperty())) {
			param.put(pageable.getSearchProperty(), pageable.getSearchValue());
		}
		if(filters!=null&&filters.size()>0) {
			for(ViewFilter filter:filters) {
				param.put(filter.getProperty(), filter.getValue());
			}
		}
		if(StringUtils.isNotBlank(pageable.getOrderProperty())) {
			param.put("property", DomainUtil.camelToUnderline(pageable.getOrderProperty()));
			param.put("direction", pageable.getOrderDirection());
		}
		Integer count = this.entOperateAuthClusterMapper.count(param);
		param.put("start", pageable.getStart());
		param.put("pageSize", pageable.getPageSize());
		List<EntOperateAuth> list = this.entOperateAuthClusterMapper.findPage(param);
		return new ViewPage(count,pageable.getPageSize(),list); 
	}

	@Override
	public void bind(ReqOperateBind bind) {
		List<ReqStallList> list = bind.getStallList();
		Set<Long> collect = list.stream().map(stall -> stall.getPreId()).collect(Collectors.toSet());
		List<EntAuthPre> pres = new ArrayList<>();
		List<EntAuthStall> stalls = new ArrayList<>();
		EntAuthPre pre  = null;
		EntAuthStall stall = null;
		for (Long id : collect) {
			if(id != null) {
				pre = new EntAuthPre();
				pre.setAuthId(bind.getId());
				pre.setPreId(id);
				pres.add(pre);
			}
		}
		for (ReqStallList reqStallList : list) {
			stall = new EntAuthStall();
			stall.setAuthId(bind.getId());
			stall.setPreId(reqStallList.getPreId());
			stall.setStallId(reqStallList.getStallId());
			stalls.add(stall);
		}
		this.entAuthPreMasterMapper.deleteByAuthId(bind.getId());
		this.entAuthStallMasterMapper.deleteByAuthId(bind.getId());
		this.entAuthPreMasterMapper.saveBatch(pres);
		this.entAuthStallMasterMapper.saveBatch(stalls);
	}

	@Override
	public Map<String, Object> resource(Long id) {
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("authId", id);
		List<EntAuthPre> res = this.entAuthPreClusterMapper.findList(param);
		List<EntAuthStall> rps = this.entAuthStallClusterMapper.findList(param);
		param.put("pres", res);
		param.put("stas", rps);
		return param;
	}
}
