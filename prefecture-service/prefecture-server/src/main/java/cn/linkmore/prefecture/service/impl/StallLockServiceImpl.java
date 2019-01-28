package cn.linkmore.prefecture.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.linkmore.bean.view.ViewFilter;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.prefecture.dao.cluster.StallLockClusterMapper;
import cn.linkmore.prefecture.dao.master.StallLockMasterMapper;
import cn.linkmore.prefecture.entity.StallLock;
import cn.linkmore.prefecture.request.ReqCheck;
import cn.linkmore.prefecture.request.ReqStallLock;
import cn.linkmore.prefecture.response.ResStallLock;
import cn.linkmore.prefecture.service.StallLockService;
import cn.linkmore.redis.RedisService;
import cn.linkmore.util.DomainUtil;
import cn.linkmore.util.ObjectUtils;
/**
 * Service实现类 - 车位锁
 * @author liwenlong
 * @version 1.0
 *
 */
@Service
public class StallLockServiceImpl implements StallLockService {

	public static final String FREE_LOKC_KEY = "freelock_key:";

	@Autowired
	private StallLockClusterMapper stallLockClusterMapper;
	
	@Autowired
	private StallLockMasterMapper stallLockMasterMapper;

	@Resource
	private RedisService redisService;
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	public ViewPage findPage(ViewPageable pageable) {
		Map<String, Object> param = new HashMap<String, Object>();
		List<ViewFilter> filters = pageable.getFilters();
		if (StringUtils.isNotBlank(pageable.getSearchProperty())) {
			param.put(pageable.getSearchProperty(), pageable.getSearchValue());
		}
		if (filters != null && filters.size() > 0) {
			for (ViewFilter filter : filters) {
				param.put(filter.getProperty(), filter.getValue());
			}
		}
		if (StringUtils.isNotBlank(pageable.getOrderProperty())) {
			param.put("property", DomainUtil.camelToUnderline(pageable.getOrderProperty()));
			param.put("direction", pageable.getOrderDirection());
		}
		Integer count = this.stallLockClusterMapper.count(param);
		param.put("start", pageable.getStart());
		param.put("pageSize", pageable.getPageSize());
		List<ResStallLock> list = this.stallLockClusterMapper.findPage(param);
		return new ViewPage(count, pageable.getPageSize(), list);
	}

	@Override
	public int save(ReqStallLock record) {
		record.setCreateTime(new Date());
		StallLock stallLock = new StallLock();
		stallLock = ObjectUtils.copyObject(record, stallLock);
		return this.stallLockMasterMapper.save(stallLock);
	}

	@Override
	public int update(ReqStallLock record) {
		StallLock stallLock = new StallLock();
		stallLock = ObjectUtils.copyObject(record, stallLock);
		return this.stallLockMasterMapper.update(stallLock);
	}

	@Override
	public int delete(Long id) {
		return this.stallLockMasterMapper.delete(id);
	}

	@Override
	public Integer check(ReqCheck reqCheck) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("property", reqCheck.getProperty());
		param.put("value", reqCheck.getValue());
		param.put("id", reqCheck.getId());
		return this.stallLockClusterMapper.check(param);
	}

	@Override
	public int batchSave(List<String> sns) {
		List<StallLock> locks = new ArrayList<StallLock>();
		StallLock lock = null;
		for (String sn : sns) {
			lock = new StallLock();
			lock.setCreateTime(new Date());
			lock.setSn(sn);
			locks.add(lock);
		}
		return this.stallLockMasterMapper.batchSave(locks);
	}

	@Override
	public List<ResStallLock> findAll(Map<String,Object> param) {
		Long lockId = Long.valueOf(param.get("lockId").toString());
		log.info("find all lockId:{}",lockId);
		if (-1 != lockId) {
			param.put("lockId", lockId);
		}else {
			param.put("lockId", null);
		}
		
		return this.stallLockClusterMapper.findAll(param);
	}

	@Override
	public int reset(Long id) {
		ResStallLock lock = this.stallLockClusterMapper.findById(id);
		redisService.remove(FREE_LOKC_KEY + lock.getPrefectureId(), lock.getSn());
		lock.setBindTime(null);
		lock.setPrefectureId(null);
		lock.setStallId(null);
		
		StallLock stallLock = new StallLock();
		stallLock = ObjectUtils.copyObject(lock, stallLock);
		return stallLockMasterMapper.updateBind(stallLock);
	}

	@Override
	public int checkSn(String sn) {
		Map<String, Object> param = new HashMap<>();
		param.put("sn", sn);
		return this.stallLockClusterMapper.checkSn(param);
	}

	@Override
	public boolean checkFormerLock(String sn) {
		boolean flag = true;
		Map<String, Object> param = new HashMap<>();
		if (null != sn) {
			param.put("sn", sn);
			int check = stallLockClusterMapper.checkFormerSn(param);
			if (check < 1) {
				flag = false;
			}
		} else {
			flag = false;
		}
		return flag;
	}

}
