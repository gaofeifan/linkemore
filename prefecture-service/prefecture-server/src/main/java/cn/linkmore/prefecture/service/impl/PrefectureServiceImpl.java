package cn.linkmore.prefecture.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.linkmore.prefecture.dao.cluster.PrefectureClusterMapper;
import cn.linkmore.prefecture.dao.cluster.StallClusterMapper;
import cn.linkmore.prefecture.dao.master.PrefectureMasterMapper;
import cn.linkmore.prefecture.request.ReqPrefecture;
import cn.linkmore.prefecture.response.ResPrefectureDetail;
import cn.linkmore.prefecture.service.PrefectureService;
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
	private PrefectureMasterMapper prefectureMasterMapper;
	
	@Override
	public ResPrefectureDetail find(Long preId) {
		ResPrefectureDetail detail = prefectureClusterMapper.findPrefectureById(preId);
		int stallCount = stallClusterMapper.getCountByPreId(preId);
		detail.setStallCount(stallCount);
		return detail;
	}
	@Override
	public List<ResPrefectureDetail> findPreListByLoc(ReqPrefecture reqPrefecture) {
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("latitude", reqPrefecture.getLatitude());
		param.put("longitude", reqPrefecture.getLongitude());
		param.put("scale", reqPrefecture.getScale());
		return prefectureClusterMapper.findPreListByLoc(param);
	}
	@Override
	public List<ResPrefectureDetail> findPreListByCityId(Long cityId) {
		return prefectureClusterMapper.findPreListByCityId(cityId);
	}
}
