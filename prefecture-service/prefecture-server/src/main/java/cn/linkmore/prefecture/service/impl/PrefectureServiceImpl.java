package cn.linkmore.prefecture.service.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.linkmore.account.client.UserStaffClient;
import cn.linkmore.account.client.hystrix.UserStaffClientHystrix;
import cn.linkmore.account.response.ResUser;
import cn.linkmore.account.response.ResUserStaff;
import cn.linkmore.common.client.hystrix.CityClientHystrix;
import cn.linkmore.common.response.ResCity;
import cn.linkmore.prefecture.dao.cluster.PrefectureClusterMapper;
import cn.linkmore.prefecture.dao.cluster.StallClusterMapper;
import cn.linkmore.prefecture.dao.cluster.StrategyBaseClusterMapper;
import cn.linkmore.prefecture.dao.master.PrefectureMasterMapper;
import cn.linkmore.prefecture.entity.Prefecture;
import cn.linkmore.prefecture.entity.StrategyBase;
import cn.linkmore.prefecture.request.ReqPrefecture;
import cn.linkmore.prefecture.response.ResPre;
import cn.linkmore.prefecture.response.ResPrefecture;
import cn.linkmore.prefecture.response.ResPrefectureDetail;
import cn.linkmore.prefecture.response.ResPrefectureList;
import cn.linkmore.prefecture.response.ResPrefectureStrategy;
import cn.linkmore.prefecture.service.PrefectureService;
import cn.linkmore.prefecture.util.FreeLockPool;
import cn.linkmore.util.StringUtil;
/**
 * Service实现类 - 车区信息
 * @author jiaohanbin
 * @version 2.0
 *
 */
@Service
public class PrefectureServiceImpl implements PrefectureService {
	
	@Autowired
	private StallClusterMapper stallClusterMapper;
	@Autowired
	private PrefectureClusterMapper prefectureClusterMapper;
	@Autowired
	private StrategyBaseClusterMapper strategyBaseClusterMapper;
	@Autowired
	private FreeLockPool freeLockPool;
	@Autowired
	private PrefectureMasterMapper prefectureMasterMapper;
	@Autowired
	private UserStaffClient userStaff;
	@Autowired
	private CityClientHystrix cityClient;
	
	@Override
	public ResPrefectureDetail find(Long preId) {
		ResPrefectureDetail detail = prefectureClusterMapper.findPrefectureById(preId);
		int stallCount = stallClusterMapper.findCountByPreId(preId);
		String lan = detail.getTimelyUnit();
		detail.setStallCount(stallCount);
		detail.setFirstHour(detail.getFirstHour());
		detail.setTimelyLong(detail.getTimelyLong()+lan);
		detail.setLeisureStall(freeLockPool.freeStallCount(preId).intValue());
		return detail;
	}
	@Override
	public List<ResPrefecture> findPreListByLoc(ReqPrefecture reqPrefecture,ResUser user) {
		/*Map<String,Object> param = new HashMap<String,Object>();
		param.put("latitude", reqPrefecture.getLatitude());
		param.put("longitude", reqPrefecture.getLongitude());
		*/
		
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("status", 0);
		//此处cityId暂时为空，返回所有的车区信息
		paramMap.put("cityId", null);
		List<ResPrefecture> preList = prefectureClusterMapper.findPreByStatusAndGPS(paramMap);
		 
		if(user!=null){
			ResUserStaff us = this.userStaff.selectById(user.getId());
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
			prb.setLeisureStall(freeLockPool.freeStallCount(prb.getId()).intValue()); 
		}
		return preList;
	}
	
	@Override
	public List<ResPrefectureList> findPreListByCityId(Long cityId,ResUser user) {
		Map<String,Object> paramMap = new HashMap<>();
		//如果传城市id为-1 获取杭州的专区数据
		if(cityId == -1){
			ResCity city = this.cityClient.findByCode("330100");
			paramMap.put("cityId", city.getId());
		}else{
			paramMap.put("cityId", cityId);
		}
		paramMap.put("status", 0);
		
		String lan = "分钟";
		List<ResPrefectureList> list = prefectureClusterMapper.findPreListByCityId(paramMap);
		if(user!=null){
			ResUserStaff us = this.userStaff.selectById(user.getId());
			if(us!=null&&us.getStatus().intValue()==ResUserStaff.STATUS_ON.intValue()){
				List<ResPrefectureList> list1 = prefectureClusterMapper.findPreListByCityId1(paramMap);
				if(list1!=null){ 
					if(list!=null){
						list.addAll(list1);
					}else{
						list = list1;
					}
				} 
			}
		}
		for(ResPrefectureList resPrefecture:list){
			int stallCount = stallClusterMapper.findCountByPreId(resPrefecture.getId());
			resPrefecture.setStallCount(stallCount);
			resPrefecture.setTimelyLong(resPrefecture.getTimelyLong() + lan);
			resPrefecture.setLeisureStall(freeLockPool.freeStallCount(resPrefecture.getId()).intValue());
		}
		return list;
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
		if(StringUtil.isNotBlank(prefecture)){
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
}
