package cn.linkmore.prefecture.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.linkmore.lock.bean.LockBean;
import com.linkmore.lock.factory.LockFactory;
import com.linkmore.lock.response.ResponseMessage;
import cn.linkmore.account.client.UserStaffClient;
import cn.linkmore.account.response.ResUserStaff;
import cn.linkmore.prefecture.dao.cluster.PrefectureClusterMapper;
import cn.linkmore.prefecture.dao.cluster.StallClusterMapper;
import cn.linkmore.prefecture.dao.cluster.StrategyBaseClusterMapper;
import cn.linkmore.prefecture.entity.Prefecture;
import cn.linkmore.prefecture.entity.StrategyBase;
import cn.linkmore.prefecture.request.ReqPrefecture;
import cn.linkmore.prefecture.response.ResPre;
import cn.linkmore.prefecture.response.ResPrefecture;
import cn.linkmore.prefecture.response.ResPrefectureDetail;
import cn.linkmore.prefecture.response.ResPrefectureList;
import cn.linkmore.prefecture.response.ResPrefectureStrategy;
import cn.linkmore.prefecture.response.ResStall;
import cn.linkmore.prefecture.service.PrefectureService;
import cn.linkmore.util.JsonUtil;
import cn.linkmore.util.ObjectUtils;

/**
 * Service实现类 - 车区信息
 * @author jiaohanbin
 * @version 2.0
 *
 */
@Service
public class PrefectureServiceImpl implements PrefectureService {
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private StallClusterMapper stallClusterMapper;
	@Autowired
	private PrefectureClusterMapper prefectureClusterMapper;
	@Autowired
	private StrategyBaseClusterMapper strategyBaseClusterMapper;
	@Autowired
	private UserStaffClient userStaff;
	
	@Override
	public ResPrefectureDetail findById(Long preId) {
		ResPrefectureDetail detail = new ResPrefectureDetail();
		Prefecture pre = prefectureClusterMapper.findById(preId);
		if(pre != null) {
			return ObjectUtils.copyObject(pre, detail);
		}
		return null;
	}
	@Override
	public List<ResPrefecture> findPreListByLoc(ReqPrefecture reqPrefecture) {
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("status", 0);
		//此处cityId暂时为空，返回所有的车区信息
		paramMap.put("cityId", null);
		List<ResPrefecture> preList = prefectureClusterMapper.findPreByStatusAndGPS(paramMap);
		log.info("pre loc:{}",JsonUtil.toJson(preList));
		if(reqPrefecture.getUserId()!=null){
			ResUserStaff us = this.userStaff.findById(reqPrefecture.getUserId());
			if(us!=null&&us.getStatus().intValue() == ResUserStaff.STATUS_ON.intValue()){
				List<ResPrefecture> preList1 = prefectureClusterMapper.findPreByStatusAndGPS1(paramMap);
				if(preList1!=null){
					if(preList==null){
						preList = preList1;
					}else{
						preList.addAll(preList1);
					}
				}
			} 
		}
		for(ResPrefecture prb: preList){ 
			prb.setChargeTime(prb.getChargeTime() + "分钟");
			prb.setChargePrice(prb.getChargePrice() + "元");
			prb.setLeisureStall(getFreeStall(prb.getId())); 
		}
		return preList;
	}
	
	@Override
	public ResPrefectureStrategy getPreStrategy(Long preId) {
		String mins = "分钟";
		String freetime = "免费时长";
		String free = "免费";
		String yuan = "￥";
		String cap = "￥封顶";
		String total = "共";
		
		ResPrefectureStrategy bean = null;
		Prefecture prefecture = prefectureClusterMapper.findById(preId);
		if(prefecture != null){
			bean = new ResPrefectureStrategy();
			StrategyBase strategyBase = strategyBaseClusterMapper.findById(prefecture.getStrategyId());
			//当type大于4时，情况另行判断
			if(strategyBase.getType() < 5||strategyBase.getType().intValue()==7){
				String beginTime = strategyBase.getBeginTime();
				String[] split1 = beginTime.split(":");
				String endTime = strategyBase.getEndTime();
				String[] split2 = endTime.split(":");
				bean.setFreeMins(freetime+strategyBase.getFreeMins()+" "+mins);
				//白天时间段(9:00 - 21:00)
				String dayTime = "("+split1[0]+":"+split1[1]+"-"+split2[0]+":"+split2[1]+") ";
				// 首小时计费价格 10元/小时
				String firstFeePrice = yuan+String.format("%.2f", strategyBase.getFirstHour())+"/"+strategyBase.getTimelyLong()+mins+"\n";
				//夜间时间段 (21:00 - 9:00)
				String nightTime = "("+split2[0]+":"+split2[1]+"-"+split1[0]+":"+split1[1]+") ";
				// 夜间计费价格 2.5元/小时
				String nightFeePrice = yuan+String.format("%.2f", strategyBase.getNightPrice())+"/"+strategyBase.getNightTimelyLong()+mins;
				bean.setContent(dayTime+firstFeePrice+nightTime+nightFeePrice);
			}else if(strategyBase.getType()==5){
				//拱墅万达广场
				/*
				 *  (0h- 3h) 共5元
				 *	(3h-24h) 5元/60分钟
				 *	(0h-24h) 60元封顶
				 *	(  >24h) 5元/60分钟
				 */
				bean.setFreeMins(freetime+strategyBase.getFreeMins()+" "+mins);
				String first = "(0h-3h) "+total+yuan+strategyBase.getFirstHour()+"\n";
				String second = "(3h-24h) "+yuan+strategyBase.getBasePrice()+"/"+strategyBase.getTimelyLong()+mins+"\n";
				String third = "(0h-24h) "+yuan+strategyBase.getBasePrice().multiply(new BigDecimal(strategyBase.getTopDaily()/60))+cap+"\n";
				String fourth = "(>24h) "+yuan+strategyBase.getBasePrice()+"/"+strategyBase.getTimelyLong()+mins;
				bean.setContent(first+second+third+fourth);
			}else if(strategyBase.getType()==6){
				//国瑞城
				/* 
				 * 数据的时间无顺序   按时间排序
				 *	00:00,01:00,1.0,15|
				 *	01:00,05:00|
				 *	05:00,09:00,1.0,15|
				 *	09:00,13:00,1.5,15|
				 *	13:00,22:00,2.5,15|
				 *	22:00,24:00,1.0,15|
				 * 效果时间排序
				 *	(00:00-01:00) 1元/15分钟
				 *  (1:00-5:00) 免费
				 *	(5:00-9:00) 1元/15分钟
				 *	(9:00-13:00) 1.5元/15分钟
				 *	(13:00-22:00)2.5元/15分钟
				 *	(22:00-24:00) 1元/15分钟
				 */
				String[] flexDetail = strategyBase.getFlexDetail().split("\\|");
				int[] spaceTime = new int[flexDetail.length];
				//获取第一段时间的分钟数
				for (int i = 0; i < flexDetail.length; i++) {
					String[] time = flexDetail[i].split(",")[0].split("\\:");
					spaceTime[i] = Integer.valueOf(time[0])*60+Integer.valueOf(time[1]);
				}
				//初始索引
				int[] index = new int[spaceTime.length];
				for (int i = 0; i < index.length; i++) {
					index[i] = i;
				}
				//按照分钟数大小计算排序 - 获取排序后的索引
				for(int i = 0;i < spaceTime.length-1;i++){
					for(int j = 0;j < spaceTime.length-1-i;j++){
						if(spaceTime[j] > spaceTime[j+1]){
							int temp = spaceTime[j];
							spaceTime[j] = spaceTime[j+1];
							spaceTime[j+1] = temp;
							//获取排序后索引 - 即详情段的排序索引
							int temp2 = index[j];
							index[j] = index[j+1];
							index[j+1] = temp2;
						}
					}
				}
				//根据索引赋值 得到排序后的计费详情
				String[] detail = new String[flexDetail.length];
				for (int i = 0; i < index.length; i++) {
					detail[i] = flexDetail[index[i]];
				}
				String price = "";
				//从第二个开始
				for (int j = 1; j < detail.length; j++) {
					String[] split = detail[j].split(",");
					if(split.length == 2){
						price += "("+split[0]+"-"+split[1]+") "+free;
					}else{
						if(j < detail.length-1){
							price += "("+split[0]+"-"+split[1]+") "+yuan+split[2]+"/"+split[3]+mins;
						}else{
							price += "("+split[0]+"-"+detail[0].split(",")[1]+") "+yuan+split[2]+"/"+split[3]+mins;
						}
					}
					price+="\n";
				}
				bean.setContent(price);
				bean.setFreeMins(freetime+strategyBase.getFreeMins()+" "+mins);
			}
		}
		return bean;
	}
	@Override
	public List<ResPre> findList(List<Long> ids) {
		List<ResPre> list = this.prefectureClusterMapper.findByIds(ids);
		return list;
	}
	
	@Override
	public List<ResPrefectureList> getStallCount() {
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("status", 0);
		//此处cityId暂时为空，返回所有的车区信息
		paramMap.put("cityId", null);
		List<ResPrefecture> preList = prefectureClusterMapper.findPreByStatusAndGPS(paramMap);
		log.info("get_stall_count pre size :{}", preList.size());
		List<ResPrefectureList> list = new ArrayList<ResPrefectureList>();
		ResPrefectureList pre = null;
		if(CollectionUtils.isNotEmpty(preList)) {
			for(ResPrefecture resPre:preList) {
				pre = new ResPrefectureList();
				pre.setId(resPre.getId());
				pre.setLeisureStall(getFreeStall(resPre.getId()));
				list.add(pre);
			}
		}
		return list;
	}
	/**
	 * 根据车区id查询所有空闲车位
	 * @param preId
	 * @return
	 */
	public Integer getFreeStall(Long preId) {
//		Prefecture preDetail = this.prefectureClusterMapper.findById(preId);
		List<ResStall> stallList = this.stallClusterMapper.findStallsByPreId(preId);
		int count = 0;
		if(stallList!=null) {
			count = stallList.size();
		}
		return count;
//		LockFactory lockFactory = InitLockFactory.getInstance();
//		ResponseMessage<LockBean> lc = lockFactory.findAvailableLock(preDetail.getGateway());
//		List<LockBean> lockBeanList = lc.getDataList();
//		Integer count = 0;
//		if(CollectionUtils.isNotEmpty(lockBeanList) && CollectionUtils.isNotEmpty(stallList)) {
//			log.info("pref free stall:{},         -------------       stall:{}" ,JsonUtil.toJson(lc.getDataList()) , JsonUtil.toJson(stallList));
//			for(LockBean lock : lockBeanList) {
//				for(ResStall stall : stallList) {
//					if(lock.getSlaveId().equals(stall.getLockSn())) {
//						//1 表示竖起来 0 表示躺下被占用
//						if(lock.getOpenState().equals(1)) {
//							count ++;
//						}
//						log.info("pref free stall count :{}" ,count);
//					}
//				}
//			}
//		}
//		return count;
	}
}
