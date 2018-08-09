package cn.linkmore.ops.biz.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.ops.biz.service.StallLockService;
import cn.linkmore.prefecture.client.StallLockClient;
import cn.linkmore.prefecture.request.ReqCheck;
import cn.linkmore.prefecture.request.ReqStallLock;
import cn.linkmore.prefecture.response.ResStallLock;

@Service
public class StallLockServiceImpl implements StallLockService {

	@Autowired
	private StallLockClient stallLockClient;

	@Override
	public ViewPage findPage(ViewPageable pageable) {
		return this.stallLockClient.list(pageable);
	}

	@Override
	public List<ResStallLock> find(Map<String, Object> param) {
		List<ResStallLock> list = this.stallLockClient.findList(param);
		return list;
	}

	@Override
	public int save(ReqStallLock record) {
		record.setCreateTime(new Date());
		return this.stallLockClient.save(record);
	}

	@Override
	public int update(ReqStallLock record) {
		return this.stallLockClient.update(record);
	}

	@Override
	public int delete(Long id) {
		return this.stallLockClient.delete(id);
	}

	@Override
	public Boolean check(ReqCheck reqCheck) {
		return this.stallLockClient.check(reqCheck);
	}

	@Override
	public int batchSave(List<String> sns) {
		List<ReqStallLock> locks = new ArrayList<ReqStallLock>();
		ReqStallLock lock = null;
		for (String sn : sns) {
			lock = new ReqStallLock();
			lock.setCreateTime(new Date());
			lock.setSn(sn);
			locks.add(lock);
		}
		return this.stallLockClient.batchSave(locks);
	}

	@Override
	public List<ResStallLock> findAll(Long lockId) {
		return this.stallLockClient.findAll(lockId);
	}

	@Override
	public int checkSn(String sn) {
		return this.stallLockClient.checkSn(sn);
	}

	@Override
	public boolean checkFormerLock(String sn) {
		Map<String, Object> param = new HashMap<>();
		param.put("sn", sn);
		return stallLockClient.checkFormerSn(param);
	}

}
