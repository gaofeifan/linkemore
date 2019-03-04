package cn.linkmore.enterprise.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.linkmore.bean.view.ViewFilter;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.enterprise.dao.cluster.FixedPlateClusterMapper;
import cn.linkmore.enterprise.dao.cluster.FixedRentClusterMapper;
import cn.linkmore.enterprise.dao.cluster.FixedStallClusterMapper;
import cn.linkmore.enterprise.dao.master.FixedPlateMasterMapper;
import cn.linkmore.enterprise.dao.master.FixedRentMasterMapper;
import cn.linkmore.enterprise.dao.master.FixedStallMasterMapper;
import cn.linkmore.enterprise.entity.FixedPlate;
import cn.linkmore.enterprise.entity.FixedRent;
import cn.linkmore.enterprise.entity.FixedStall;
import cn.linkmore.enterprise.request.ReqFixedRent;
import cn.linkmore.enterprise.response.ResFixedRent;
import cn.linkmore.enterprise.response.ResFixedRentStall;
import cn.linkmore.enterprise.response.ResStall;
import cn.linkmore.enterprise.service.FixedRentService;
import cn.linkmore.prefecture.client.FeignLockClient;
import cn.linkmore.prefecture.response.ResLockInfo;
import cn.linkmore.util.DomainUtil;

@Service
public class FixedRentServiceImpl implements FixedRentService {

	@Autowired
	private FixedRentClusterMapper fixedRentClusterMapper;

	@Autowired
	private FixedRentMasterMapper fixedRentMasterMapper;

	@Autowired
	private FixedStallMasterMapper fixedStallMasterMapper;
	
	@Autowired
	private FixedStallClusterMapper fixedStallClusterMapper;

	@Autowired
	private FixedPlateMasterMapper fixedPlateMasterMapper;
	
	@Autowired
	private FixedPlateClusterMapper fixedPlateClusterMapper;
	
	@Autowired
	private FeignLockClient feignLockClient;

	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
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
		Integer count = this.fixedRentClusterMapper.count(param);
		param.put("start", pageable.getStart());
		param.put("pageSize", pageable.getPageSize());
		List<ResFixedRentStall> list = this.fixedRentClusterMapper.findPage(param);

		for (ResFixedRentStall resFixedRent : list) {
			if (StringUtils.isNotEmpty(resFixedRent.getLockSN())) {
				ResLockInfo lockInfo = feignLockClient.lockInfo(resFixedRent.getLockSN());
				if (lockInfo != null) {
					resFixedRent.setLockState(lockInfo.getLockState());
					resFixedRent.setElectricity(lockInfo.getElectricity());
				}
			}
		}

		return new ViewPage(count, pageable.getPageSize(), list);
	}

	@Override
	public List<ResStall> stallList(Map<String, Object> map) {
		return fixedRentClusterMapper.stallList(map);
	}

	@Override
	public List<ResStall> freeStallList(Map<String, Object> map) {
		return fixedRentClusterMapper.freeStallList(map);
	}
	
	private boolean isValidPlate(String plate) {
		final String[] values= {"^[京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼使领A-Z]{1}[A-Z]{1}(([0-9]{5}[DF]$)|([DF][A-HJ-NP-Z0-9][0-9]{4}$))"
                ,"^[京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼使领A-Z]{1}[A-Z]{1}[A-HJ-NP-Z0-9]{4}[A-HJ-NP-Z0-9挂学警港澳]{1}$"};
		final int[] lengths={8,7};
		for (int i = 0; i < lengths.length; i++) {
			if (plate.length() == lengths[i]) {
				Pattern pattern = Pattern.compile(values[i]);
				Matcher matcher = pattern.matcher(plate);
				return matcher.matches();
			}
		}
		return false;
	}

	@Override
	public String check(ReqFixedRent reqFixedRent) {
		
		if (StringUtils.isNotEmpty(reqFixedRent.getStallIds())) {
			String[] arrayStall = reqFixedRent.getStallIds().split(",");
			for (String stallId : arrayStall) {
				Map<String, Object> param =new HashMap<String, Object>();
				param.put("preId", reqFixedRent.getPreId());
				param.put("stallId", stallId);

				Integer existsStall = fixedStallClusterMapper.existsStall(param);

				if(existsStall==null ) {
					System.out.println("固定车位不存在");
					return "固定车位不存在";
				}
				
				if (reqFixedRent.getFixedId()!=null) {
					param.put("NEQFixedId", reqFixedRent.getFixedId());
				}
				
				existsStall = fixedStallClusterMapper.existsFixedStall(param);

				if(existsStall != null && existsStall>0) {
					System.out.println("固定车位已使用");
					return "固定车位已使用";
				}
			}
		}else if  (StringUtils.isNotEmpty(reqFixedRent.getStallNames())) {
			String[] arrayStall = reqFixedRent.getStallNames().split(",");
			for (String stallName : arrayStall) {
				Map<String, Object> param =new HashMap<String, Object>();
				param.put("preId", reqFixedRent.getPreId());
				param.put("stallName", stallName);
				Integer existsStall = fixedStallClusterMapper.existsStall(param);
				if(existsStall==null ) {
					return "固定车位不存在";
				}
				existsStall = fixedStallClusterMapper.existsFixedStall(param);
				if(existsStall != null && existsStall>0) {
					return "固定车位已使用";
				}
			}
		}
		if (StringUtils.isNotEmpty(reqFixedRent.getPlateNos())) {
			String[] arrayPlate = reqFixedRent.getPlateNos().split(",");
			for (String plate : arrayPlate) {
				if(!isValidPlate(plate)) {
					return "车牌号不正确";
				}
			}
		}
		return "";
	}
	
	
	@Override
	public int insert(ReqFixedRent reqFixedRent) {
		int count = 0;
		try {
			FixedRent fixedRent = new FixedRent();
			fixedRent.setEntId(reqFixedRent.getEntId());
			fixedRent.setPreId(reqFixedRent.getPreId());
			fixedRent.setMobile(reqFixedRent.getLinkPhone());
			// fixedRent.setPreName(reqFixedRent.getPreName());

			fixedRent.setStartTime(sdf.parse(reqFixedRent.getStartTime()));
			fixedRent.setEndTime(sdf.parse(reqFixedRent.getEndTime()));

			fixedRent.setCreateTime(reqFixedRent.getCreateTime());
			fixedRent.setCreateUserId(reqFixedRent.getCreateUserId());
			fixedRent.setUpdateTime(reqFixedRent.getUpdateTime());
			fixedRent.setUpdateUserId(reqFixedRent.getUpdateUserId());

			count = fixedRentMasterMapper.insert(fixedRent);

			if (StringUtils.isNotEmpty(reqFixedRent.getStallIds())) {
				String[] arrayStall = reqFixedRent.getStallIds().split(",");
				for (String stallId : arrayStall) {
					FixedStall fixedStall = new FixedStall();
					fixedStall.setFixedId(fixedRent.getId());
					fixedStall.setStallId(Long.parseLong(stallId));
					fixedStall.setStatus(reqFixedRent.getStatus());
					fixedStallMasterMapper.insert(fixedStall);
				}
			}else if  (StringUtils.isNotEmpty(reqFixedRent.getStallNames())) {
				String[] arrayStall = reqFixedRent.getStallNames().split(",");
				for (String stallName : arrayStall) {
					FixedStall fixedStall = new FixedStall();
					fixedStall.setFixedId(fixedRent.getId());
					fixedStall.setStallName(stallName);
					fixedStall.setStatus(reqFixedRent.getStatus());
					fixedStallMasterMapper.insertByStallName(fixedStall);
				}
			}

			if (StringUtils.isNotEmpty(reqFixedRent.getPlateNos())) {
				String[] arrayPlate = reqFixedRent.getPlateNos().split(",");
				for (String plate : arrayPlate) {
					FixedPlate fixedPlate = new FixedPlate();
					fixedPlate.setFixedId(fixedRent.getId());
					fixedPlate.setPlateNo(plate);
					fixedPlateMasterMapper.insert(fixedPlate);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}

	
	
	/**
	 * 
	 * @param list1
	 * @param list2
	 * 比较 list1,list2,将 list2中有，但list1中没有的元素集合返回
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private static <T> List<T> getNewData(List<T>  list1,List<T> list2){
		List<Object> returnList=new ArrayList<>();
		for(Object obj:list2) {
			if(!list1.contains(obj)) {
				returnList.add(obj);
			}
		}
		return (List<T>) returnList;
	}
	
	@Override
	public int update(ReqFixedRent reqFixedRent) {
		int count = 0;
		try {
			FixedRent fixedRent = new FixedRent();
			fixedRent.setId(reqFixedRent.getFixedId());
			fixedRent.setMobile(reqFixedRent.getLinkPhone());
			fixedRent.setStartTime(sdf.parse(reqFixedRent.getStartTime()));
			fixedRent.setEndTime(sdf.parse(reqFixedRent.getEndTime()));
			fixedRent.setUpdateTime(reqFixedRent.getUpdateTime());
			fixedRent.setUpdateUserId(reqFixedRent.getUpdateUserId());
			count =fixedRentMasterMapper.updateByPrimaryKeySelective(fixedRent);
			
			if (StringUtils.isNotEmpty(reqFixedRent.getStallIds())) {
				List<Long> newList = Arrays.asList(reqFixedRent.getStallIds().split(",")).stream().map(s->Long.parseLong(s)).collect(Collectors.toList());

				List<ResStall> listFixedStall = fixedStallClusterMapper.selectByFixedId(reqFixedRent.getFixedId());
				List<Long> oldList=listFixedStall.stream().map(s->s.getId()).collect(Collectors.toList());

				Map<String, Object> map=new HashMap<String, Object>();
				
				
				map.put("fixedId", reqFixedRent.getFixedId());
				map.put("status", (short) 1);
				
				List<Long> addList = getNewData(oldList,newList);
				if(CollectionUtils.isNotEmpty(addList)) {
					map.put("stallIds", addList);
					fixedStallMasterMapper.insertMore(map);
				}
				
				List<Long> delList = getNewData(newList,oldList);
				if(CollectionUtils.isNotEmpty(delList)) {
					map.put("stallIds", delList);
					fixedStallMasterMapper.deleteStall(map);
				}
			}
			
			if (StringUtils.isNotEmpty(reqFixedRent.getPlateNos())) {
				List<String> newList = Arrays.asList(reqFixedRent.getPlateNos().split(","));
				List<String> oldList = fixedPlateClusterMapper.selectByFixedId(reqFixedRent.getFixedId());
				
				Map<String, Object> map=new HashMap<String, Object>();
				map.put("fixedId", reqFixedRent.getFixedId());
				
				List<String> addList = getNewData(oldList,newList);
				if(CollectionUtils.isNotEmpty(addList)) {
					map.put("plateNos", addList);
					fixedPlateMasterMapper.insertMore(map);
				}
				
				List<String> delList = getNewData(newList,oldList);
				if(CollectionUtils.isNotEmpty(delList)) {
					map.put("plateNos", delList);
					fixedPlateMasterMapper.deleteByPlate(map);
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}

	@Override
	public int deleteStall(Map<String, Object> map) {
		List<Long> stallIds= (List<Long>) map.get("stallIds");
		map.put("stallId", stallIds.get(0));
		ResStall resStall = fixedStallClusterMapper.selectByFixedAndStall(map);
		if (resStall!=null) {
			if (resStall.getFixedState()==1) {
				return -2;
			}
		}
		return fixedStallMasterMapper.deleteStall(map);
	}

	@Override
	public int open(Map<String, Object> map) {
		map.put("status", 1);
		return fixedStallMasterMapper.updateStatus(map);
	}

	@Override
	public int close(Map<String, Object> map) {
		map.put("status", 0);
		return fixedStallMasterMapper.updateStatus(map);
	}

	@Override
	public ResFixedRent view(Long fixedId) {
		ResFixedRent rsFixedRent=new ResFixedRent();
		
		FixedRent fixedRent = fixedRentClusterMapper.selectByPrimaryKey(fixedId);
		rsFixedRent.setFixedId(fixedRent.getId());
		rsFixedRent.setStartTime(fixedRent.getStartTime());
		rsFixedRent.setEndTime(fixedRent.getEndTime());
		rsFixedRent.setLinkPhone(fixedRent.getMobile());
		rsFixedRent.setCreateUserName(fixedRent.getCreateUserName());
		rsFixedRent.setCreateTime(fixedRent.getCreateTime());
		rsFixedRent.setUpdateTime(fixedRent.getUpdateTime());
		rsFixedRent.setUpdateUserName(fixedRent.getUpdateUserName());
		
		List<ResStall> listFixedStall = fixedStallClusterMapper.selectByFixedId(fixedId);
		
		for (ResStall resStall : listFixedStall) {
			if (StringUtils.isNotEmpty(resStall.getLockSN())) {
				ResLockInfo lockInfo = feignLockClient.lockInfo(resStall.getLockSN());
				if (lockInfo != null) {
					resStall.setLockState(lockInfo.getLockState());
					resStall.setElectricity(lockInfo.getElectricity());
				}
			}
		}
		
		rsFixedRent.setStall(listFixedStall);

		List<String> listFixedPlate = fixedPlateClusterMapper.selectByFixedId(fixedId);
		rsFixedRent.setPlateNo(listFixedPlate);

		List<String> listFixedUserName = fixedPlateClusterMapper.selectUserNameByFixedId(fixedId);
		rsFixedRent.setUserName(listFixedUserName);
		
		return rsFixedRent;
	}



}
