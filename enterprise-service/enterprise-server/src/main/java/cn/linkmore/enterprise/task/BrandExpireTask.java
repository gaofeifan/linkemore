package cn.linkmore.enterprise.task;

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
import cn.linkmore.enterprise.dao.master.EntBrandPreMasterMapper;
import cn.linkmore.enterprise.response.ResBrandPre;

@Component
public class BrandExpireTask {

	private Logger log = LoggerFactory.getLogger(getClass());

	@Resource
	private EntBrandPreClusterMapper entBrandPreClusterMapper;
	
	@Resource
	private EntBrandPreMasterMapper entBrandPreMasterMapper;
	
	@Scheduled(cron = "0 0/5 * * * ?")
	public void run() {
		log.info("sync stall lock thread...");
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
			}
		}
	}

}
