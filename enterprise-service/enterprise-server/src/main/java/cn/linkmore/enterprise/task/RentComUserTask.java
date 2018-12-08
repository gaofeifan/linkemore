package cn.linkmore.enterprise.task;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSON;
import cn.linkmore.enterprise.dao.cluster.EntBrandPreClusterMapper;
import cn.linkmore.enterprise.dao.cluster.EntBrandStallClusterMapper;
import cn.linkmore.enterprise.dao.cluster.EntRentUserClusterMapper;
import cn.linkmore.enterprise.dao.master.EntBrandPreMasterMapper;
import cn.linkmore.enterprise.dao.master.EntBrandStallMasterMapper;
import cn.linkmore.enterprise.dao.master.EntRentUserMasterMapper;
import cn.linkmore.enterprise.entity.EntOwnerStall;
import cn.linkmore.enterprise.entity.EntRentUser;
import cn.linkmore.enterprise.response.ResBrandPre;
import cn.linkmore.enterprise.response.ResBrandStall;
import cn.linkmore.prefecture.client.StallClient;


@Component
public class RentComUserTask {

	private Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	private EntRentUserClusterMapper entRentUserClusterMapper;
	@Autowired
	private EntRentUserMasterMapper entRentUserMasterMapper;
	@Resource
	private StallClient stallClient;
	
	@Scheduled(cron = "0 0/1 * * * ?")
	public void run() {
		log.info("sync rent com user thread...");
		init();
	}
	
	
	public void init() {
		log.info("sync rent com user init... ");
		Map<String,Object> param = new HashMap<String,Object>();
		List<EntRentUser> rentUserList = entRentUserClusterMapper.findComUserList(param);
		List<EntOwnerStall> list = entRentUserClusterMapper.findRentComUserList2(param);
		List<EntRentUser> entRentUser = new ArrayList<EntRentUser>();
		EntRentUser rentUser = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		log.info("sync rent com user list {} , rentUserList size{}",JSON.toJSON(list), rentUserList.size());
		try {
			if(CollectionUtils.isNotEmpty(list)) {
				for (EntOwnerStall stall : list) {
					rentUser = new EntRentUser();
					rentUser.setCompanyId(stall.getCompanyId());
					rentUser.setCompanyName(stall.getCompanyName());
					rentUser.setPreId(stall.getPreId());
					rentUser.setPreName(stall.getPreName());
					rentUser.setStallId(stall.getStallId());
					rentUser.setStallName(stall.getStallName());
					rentUser.setMobile(stall.getMobile());
					rentUser.setPlate(stall.getPlate());
					rentUser.setStartTime(sdf.parse(stall.getStartTime()));
					rentUser.setEndTime(sdf.parse(stall.getEndTime()));
					rentUser.setUserId(stall.getUserId());
					rentUser.setType((short)1);
					entRentUser.add(rentUser);
				}
				if(CollectionUtils.isNotEmpty(rentUserList)) {
					log.info("add the new rent com user size {}", rentUserList.size());
					entRentUserMasterMapper.saveBatch(rentUserList);
				}
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
}
