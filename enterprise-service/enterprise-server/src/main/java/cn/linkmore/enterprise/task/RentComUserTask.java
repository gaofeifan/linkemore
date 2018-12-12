package cn.linkmore.enterprise.task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;

import cn.linkmore.enterprise.dao.cluster.EntRentUserClusterMapper;
import cn.linkmore.enterprise.dao.master.EntRentUserMasterMapper;
import cn.linkmore.enterprise.entity.EntRentUser;
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

	//@Scheduled(cron = "0 0/1 * * * ?")
	public void run() {
		log.info("sync rent com user thread...");
		init();
	}

	public void init() {
		log.info("sync rent com user init... ");
		Map<String, Object> param = new HashMap<String, Object>();
		List<EntRentUser> oldRentUserList = entRentUserClusterMapper.findComUserList(param);
		List<EntRentUser> newRentUserList = entRentUserClusterMapper.findRentComUserList(param);
		log.info("sync rent com user old list size={} , new list size={}",oldRentUserList.size(),newRentUserList.size());	
		//log.info("sync rent com user list {} , rentUserList size{}", JSON.toJSON(newRentUserList), oldRentUserList.size());

		//新记录增加
		List<EntRentUser> entRentUser = new ArrayList<EntRentUser>();
		if (CollectionUtils.isNotEmpty(newRentUserList)) {
			for (EntRentUser stall : newRentUserList) {
				if (! existRentUser(oldRentUserList,stall)) {
					stall.setType((short) 1);
					entRentUser.add(stall);
				}
			}
		}
		if (CollectionUtils.isNotEmpty(entRentUser)) {
			log.info("add the new rent com user size={},data={}", entRentUser.size(),JSON.toJSON(entRentUser));
			entRentUserMasterMapper.saveBatch(entRentUser);
		}
		
		//不存在的记录删除
		List<Long> ids=new ArrayList<Long>();
		if (CollectionUtils.isNotEmpty(oldRentUserList)) {
			for (EntRentUser stall : oldRentUserList) {
				if (! existRentUser(newRentUserList,stall)) {
					ids.add(stall.getId());
				}
			}
		}
		if (CollectionUtils.isNotEmpty(ids)) {
			log.info("delete the rent com user size={},data={}", ids.size(),JSON.toJSON(ids) );
			entRentUserMasterMapper.delete(ids);
		}
		log.info("sync rent com user finished.");
	}
	
	private boolean existRentUser(List<EntRentUser> rentUserList,EntRentUser entRentUser) {
		if (CollectionUtils.isNotEmpty(rentUserList)) {
			for (EntRentUser userStall : rentUserList) {
				if(userStall.getPreId() != null &&  entRentUser.getPreId() != null
					&& userStall.getCompanyId() != null &&  entRentUser.getCompanyId() != null
					&& userStall.getStallId() != null &&  entRentUser.getStallId() != null
					&& userStall.getUserId() != null &&  entRentUser.getUserId() != null
				){			
					if(userStall.getPreId().longValue() == entRentUser.getPreId().longValue()
							&& userStall.getCompanyId().longValue() == entRentUser.getCompanyId().longValue()
							&& userStall.getStallId().longValue() == entRentUser.getStallId().longValue()
							&& userStall.getUserId().longValue() == entRentUser.getUserId().longValue()
							&& StringUtils.equalsIgnoreCase(userStall.getPlate(), entRentUser.getPlate())
							) {
						return true;
					}
				}
			}
		}
		return false;
	}
	
}
