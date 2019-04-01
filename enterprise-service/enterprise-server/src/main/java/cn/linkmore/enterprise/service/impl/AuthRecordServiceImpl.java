package cn.linkmore.enterprise.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.linkmore.bean.common.security.CacheUser;
import cn.linkmore.bean.exception.BusinessException;
import cn.linkmore.bean.exception.StatusEnum;
import cn.linkmore.bean.view.ViewFilter;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.enterprise.controller.app.request.ReqAuthRecord;
import cn.linkmore.enterprise.controller.app.request.ReqAuthRecordUpdate;
import cn.linkmore.enterprise.controller.app.response.AuthRecordDetail;
import cn.linkmore.enterprise.controller.app.response.AuthRecordPre;
import cn.linkmore.enterprise.dao.cluster.AuthRecordClusterMapper;
import cn.linkmore.enterprise.dao.cluster.OwnerStallClusterMapper;
import cn.linkmore.enterprise.dao.master.AuthRecordMasterMapper;
import cn.linkmore.enterprise.entity.AuthRecord;
import cn.linkmore.enterprise.entity.EntOwnerStall;
import cn.linkmore.enterprise.service.AuthRecordService;
import cn.linkmore.redis.RedisService;
import cn.linkmore.user.factory.AppUserFactory;
import cn.linkmore.user.factory.UserFactory;
import cn.linkmore.util.DomainUtil;

/**
 * 授权记录service
 * @author jhb
 * @Date 2019年3月27日
 * @Version v1.0
 */
@Service
public class AuthRecordServiceImpl implements AuthRecordService {
	
	@Resource
	private AuthRecordMasterMapper authRecordMasterMapper;
	
	@Resource
	private AuthRecordClusterMapper authRecordClusterMapper;
	
	@Autowired
	private OwnerStallClusterMapper ownerStallClusterMapper;

	
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	private UserFactory appUserFactory = AppUserFactory.getInstance();
	
	@Autowired
	private RedisService redisService;

	@Override
	public ViewPage findPage(ViewPageable pageable) {
		Map<String,Object> param = new HashMap<String,Object>(); 
		List<ViewFilter> filters = pageable.getFilters();
		if(StringUtils.isNotBlank(pageable.getSearchProperty())) {
			param.put(pageable.getSearchProperty(), pageable.getSearchValue());
		}
		if(filters!=null&&filters.size()>0) {
			for(ViewFilter filter:filters) {
				param.put(filter.getProperty(), filter.getValue());
			}
		}
		if(StringUtils.isNotBlank(pageable.getOrderProperty())) {
			param.put("property", DomainUtil.camelToUnderline(pageable.getOrderProperty()));
			param.put("direction", pageable.getOrderDirection());
		}
		Integer count = this.authRecordClusterMapper.count(param);
		param.put("start", pageable.getStart());
		param.put("pageSize", pageable.getPageSize());
		List<AuthRecord> list = this.authRecordClusterMapper.findPage(param);
		return new ViewPage(count,pageable.getPageSize(),list); 
	}

	@Override
	public AuthRecord findById(Long id) {
		return authRecordClusterMapper.findById(id);
	}

	@Override
	public List<AuthRecord> findRecordList(Map<String, Object> param) {
		return authRecordClusterMapper.findRecordList(param);
	}

	@Override
	public Boolean save(ReqAuthRecord record, HttpServletRequest request) {
		Boolean flag = false;
		CacheUser user = (CacheUser) this.redisService.get(appUserFactory.createTokenRedisKey(request));
		if (user == null) {
			throw new BusinessException(StatusEnum.USER_APP_NO_LOGIN);
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd hh:mm");
		AuthRecord authRecord = null;
		if(StringUtils.isNotBlank(record.getStallIds())) {
			String [] ids = record.getStallIds().split(",");
			String [] stallNames = record.getStallNames().split(",");
			int i = 0;
			for(String stallId: ids) {
				try {
					authRecord = new AuthRecord();
					authRecord.setStallId(Long.valueOf(stallId));
					authRecord.setStallName(stallNames[i]);
					authRecord.setCreateTime(new Date());
					authRecord.setUpdateTime(new Date());
					authRecord.setMobile(record.getMobile());
					authRecord.setUsername(record.getUsername());
					authRecord.setPreId(record.getPreId());
					authRecord.setPreName(record.getPreName());
					authRecord.setRelationId(record.getRelationId());
					authRecord.setRelationName(record.getRelationName());
					authRecord.setStartTime(sdf.parse(record.getStartTime()));
					authRecord.setEndTime(sdf.parse(record.getEndTime()));
					authRecord.setAuthFlag((short)0);
					authRecord.setAuthUserId(user.getId());
					authRecordMasterMapper.save(authRecord);
					flag = true;
				} catch (ParseException e) {
					flag = false;
					e.printStackTrace();
				}
				i++;
			}
		}
		return flag;
	}

	@Override
	public Boolean update(ReqAuthRecordUpdate record, HttpServletRequest request) {
		Boolean flag = false;
		CacheUser user = (CacheUser) this.redisService.get(appUserFactory.createTokenRedisKey(request));
		if (user == null) {
			throw new BusinessException(StatusEnum.USER_APP_NO_LOGIN);
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd hh:mm");
		try {
			AuthRecord authRecord = new AuthRecord();
			authRecord.setId(record.getId());
			authRecord.setUpdateTime(new Date());
			authRecord.setUsername(record.getUsername());
			authRecord.setRelationId(record.getRelationId());
			authRecord.setRelationName(record.getRelationName());
			authRecord.setStartTime(sdf.parse(record.getStartTime()));
			authRecord.setEndTime(sdf.parse(record.getEndTime()));
			authRecordMasterMapper.update(authRecord);
			flag = true;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public Boolean cancalAuth(Long id, HttpServletRequest request) {
		Boolean flag = false;
		CacheUser user = (CacheUser) this.redisService.get(appUserFactory.createTokenRedisKey(request));
		if (user == null) {
			throw new BusinessException(StatusEnum.USER_APP_NO_LOGIN);
		}
		int num = authRecordMasterMapper.cancelAuth(id);
		if(num == 1) {
			flag = true;
		}
		return flag;
	}

	@Override
	public List<AuthRecordPre> findRecordList(HttpServletRequest request) {
		Map<String,Object> param = new HashMap<String,Object>();
		CacheUser user = (CacheUser) this.redisService.get(appUserFactory.createTokenRedisKey(request));
		if (user == null) {
			throw new BusinessException(StatusEnum.USER_APP_NO_LOGIN);
		}
		List<EntOwnerStall> stalllist = ownerStallClusterMapper.findStall(user.getId());
		Set<Long> ids = new HashSet<Long>();
		if (CollectionUtils.isNotEmpty(stalllist) && stalllist.size() > 0) {
			for (EntOwnerStall entOwnerStall : stalllist) {
				ids.add(entOwnerStall.getPreId());
			}
		}
		
		List<AuthRecordPre> authRecordPreList = null;
		AuthRecordPre authRecordPre = null;
		param.put("authUserId", user.getId());
		List<AuthRecord> authRecordList = authRecordClusterMapper.findRecordList(param);
		Set<Long> stallId = new HashSet<Long>();
		if(CollectionUtils.isNotEmpty(authRecordList)) {
			authRecordPreList = new ArrayList<AuthRecordPre>();
			for(AuthRecord authRecord:authRecordList) {
				if(stallId.contains(authRecord.getStallId())) {
					continue;
				}
				authRecordPre = new AuthRecordPre();
				authRecordPre.setStallId(authRecord.getStallId());
				authRecordPre.setPreId(authRecord.getPreId());
				authRecordPre.setPreName(authRecord.getPreName());
				authRecordPre.setStallName(authRecord.getStallName());
				authRecordPreList.add(authRecordPre);
				stallId.add(authRecord.getStallId());
			}
			List<AuthRecordDetail> detailList = null;
			AuthRecordDetail recordDetail = null;
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd hh:mm");
			for(AuthRecordPre authPre: authRecordPreList) {
				detailList = new ArrayList<AuthRecordDetail>();
				for (AuthRecord authRecord : authRecordList) {
					if(authPre.getPreId().equals(authRecord.getPreId()) &&
							authPre.getStallId().equals(authRecord.getStallId())) {
						recordDetail = new AuthRecordDetail();
						recordDetail.setId(authRecord.getId());
						recordDetail.setMobile(authRecord.getMobile());
						recordDetail.setRelationId(authRecord.getRelationId());
						recordDetail.setRelationName(authRecord.getRelationName());
						recordDetail.setUsername(authRecord.getUsername());
						recordDetail.setStartTime(sdf.format(authRecord.getStartTime()));
						recordDetail.setEndTime(sdf.format(authRecord.getEndTime()));
						recordDetail.setAuthFlag(authRecord.getAuthFlag());
						//此处需要根据车位id查询当前授权人是否拥有车位的使用权限
						if(authRecord.getEndTime().before(new Date())) {
							authRecord.setAuthFlag((short)2);
						}
						if(authRecord.getAuthFlag() == 0 && !ids.contains(authRecord.getStallId()) ) {
							authRecord.setAuthFlag((short)3);
						}
						detailList.add(recordDetail);
					}
				}
				authPre.setDetailList(detailList);
			}
		}
		return authRecordPreList;
	}
}
