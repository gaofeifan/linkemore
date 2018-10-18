package cn.linkmore.ops.biz.service.impl;

import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import cn.linkmore.bean.view.Tree;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.ops.biz.service.StallService;
import cn.linkmore.prefecture.client.OpsStallClient;
import cn.linkmore.prefecture.request.ReqCheck;
import cn.linkmore.prefecture.request.ReqStall;
import cn.linkmore.prefecture.response.ResStallEntity;
import cn.linkmore.prefecture.response.ResStallOps;

/**
 * 车位
 * @author jiaohanbin
 * @version 2.0
 *
 */
@Service
public class StallServiceImpl implements StallService {
	
	@Resource
	private OpsStallClient stallClient;

	private static final Logger log = LoggerFactory.getLogger(Logger.class);

	@Override
	public Tree findTree(Map<String,Object> param) {
		return this.stallClient.tree(param);
	}

	@Override
	public ViewPage findPage(ViewPageable pageable) {
		return this.stallClient.list(pageable);
	}

	@Override
	public ResStallEntity findById(Long id) {
		return stallClient.findById(id);
	}

	@Override
	public int save(ReqStall stall) {
		return this.stallClient.save(stall);
	}

	@Override
	public int update(ReqStall stall) {
		return stallClient.update(stall);
	}

	@Override
	public int check(ReqCheck reqCheck) {
		return this.stallClient.check(reqCheck);
	}

	@Override
	public int bind(Long id,Long sid) {
		return this.stallClient.bind(id, sid);
	}


	@Override
	public void saveAndBind(Long preId, String stallName, String sn) {
	   this.stallClient.saveAndBind(preId,stallName,sn);
	}

	@Override
	public void changedUp(List<Long> ids) {
		this.stallClient.changedUp(ids);
	}

	@Override
	public void changedDown(Long id) {
		this.stallClient.changedDown(id);
	}

	@Override
	public List<ResStallOps> findList(Map<String, Object> param) {
		return this.stallClient.findList(param);
	}
	
	
}
