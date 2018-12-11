package cn.linkmore.enterprise.task;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSON;
import cn.linkmore.enterprise.dao.cluster.EntBrandPreClusterMapper;
import cn.linkmore.enterprise.dao.cluster.EntBrandStallClusterMapper;
import cn.linkmore.enterprise.dao.master.EntBrandPreMasterMapper;
import cn.linkmore.enterprise.dao.master.EntBrandStallMasterMapper;
import cn.linkmore.enterprise.response.ResBrandPre;
import cn.linkmore.enterprise.response.ResBrandStall;
import cn.linkmore.prefecture.client.StallClient;


@Component
public class BrandExpireTask {

	private Logger log = LoggerFactory.getLogger(getClass());

	@Resource
	private EntBrandPreClusterMapper entBrandPreClusterMapper;
	
	@Resource
	private EntBrandPreMasterMapper entBrandPreMasterMapper;
	
	@Resource
	private EntBrandStallClusterMapper entBrandStallClusterMapper;
	
	@Resource
	private EntBrandStallMasterMapper entBrandStallMasterMapper;
	
	@Resource
	private StallClient stallClient;
	
	//@Scheduled(cron = "0 0/5 * * * ?")
	public void run() {
		log.info("sync brand pre thread...");
		init();
	}
	
	public void init() {
		log.info("expire brand pre init... ");
		List<ResBrandPre> brandPreList = this.entBrandPreClusterMapper.findBrandPreList();
		log.info("expire brand pre list {}",JSON.toJSON(brandPreList));
		if(CollectionUtils.isNotEmpty(brandPreList)) {
			for(ResBrandPre pre: brandPreList) {
				pre.setStatus((short)2);
				Map<String,Object> param = new HashMap<String,Object>();
				param.put("id", pre.getId());
				param.put("status", 2);
				param.put("updateTime", new Date());
			    this.entBrandPreMasterMapper.startOrStop(param);
			    
			    List<ResBrandStall> stallList = this.entBrandStallClusterMapper.findByBrandPreId(pre.getId());
				List<Long> stallIds = new ArrayList<Long>();
				List<Long> ids = new ArrayList<Long>();
				if(CollectionUtils.isNotEmpty(stallList)) {
					for(ResBrandStall stall: stallList) {
						stallIds.add(stall.getStallId());
						ids.add(stall.getId());
					}
					Map<String,Object> map = new HashMap<String,Object>();
					map.put("brand", "0");
					map.put("list", stallIds);
					this.stallClient.updateBrand(map);
				}
				if(CollectionUtils.isNotEmpty(ids)) {
					entBrandStallMasterMapper.delete(ids);
				}
			}
		}
	}
}
