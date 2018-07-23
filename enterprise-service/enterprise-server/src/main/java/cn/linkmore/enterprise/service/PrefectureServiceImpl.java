package cn.linkmore.enterprise.service;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.linkmore.bean.common.Constants.RedisKey;
import cn.linkmore.bean.common.security.CacheUser;
import cn.linkmore.order.client.OrderClient;
import cn.linkmore.order.response.ResPreOrderCount;
import cn.linkmore.prefecture.client.PrefectureClient;
import cn.linkmore.redis.RedisService;
import cn.linkmore.util.TokenUtil;

/**
 * @author   GFF
 * @Date     2018年7月21日
 * @Version  v2.0
 */
@Service
public class PrefectureServiceImpl implements PrefectureService {

	@Resource
	private RedisService redisService;
	@Resource
	private EntStallService entStallService; 
	@Resource
	private PrefectureClient prefectureClient;
	@Resource
	private OrderClient orderClient;
	@Override
	public List<cn.linkmore.enterprise.controller.ent.response.ResPreOrderCount> findPreList(HttpServletRequest request) {
		String key = TokenUtil.getKey(request);
		CacheUser ru = (CacheUser)this.redisService.get(RedisKey.STAFF_ENT_AUTH_USER.key+key); 
		List<Long> authStall = this.entStallService.findByStaffId(ru.getId());
		List<ResPreOrderCount> list = this.orderClient.findPreCountByIds(authStall);
		Map<Long,cn.linkmore.enterprise.controller.ent.response.ResPreOrderCount> map = new HashMap<>();
		cn.linkmore.enterprise.controller.ent.response.ResPreOrderCount order = null;
		for (ResPreOrderCount resPreOrderCount : list) {
			if(map.containsKey(resPreOrderCount.getPreId())) {
				order = map.get(resPreOrderCount.getPreId());
				order.setDayOrder(order.getDayOrder() + 1);
				order.setDayAmount(resPreOrderCount.getOrderAmount().add(order.getDayAmount()));
			}else {
				order = new cn.linkmore.enterprise.controller.ent.response.ResPreOrderCount();
				order.setDayOrder(1);
				order.setDayAmount(resPreOrderCount.getOrderAmount());
				order.setPreId(resPreOrderCount.getPreId());
				order.setPreName(resPreOrderCount.getPreName());
			}
			map.put(resPreOrderCount.getPreId(), order);
		}
		Set<Entry<Long,cn.linkmore.enterprise.controller.ent.response.ResPreOrderCount>> set = map.entrySet();
		List<cn.linkmore.enterprise.controller.ent.response.ResPreOrderCount> preOrders = new ArrayList<>();
		for (Entry<Long, cn.linkmore.enterprise.controller.ent.response.ResPreOrderCount> entry : set) {
			preOrders.add(entry.getValue());
		}
		return preOrders;
		
	}

}
