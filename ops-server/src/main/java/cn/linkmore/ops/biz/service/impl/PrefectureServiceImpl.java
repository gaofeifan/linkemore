package cn.linkmore.ops.biz.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.linkmore.bean.view.ViewPage;
import cn.linkmore.bean.view.ViewPageable;
import cn.linkmore.common.client.BaseDictClient;
import cn.linkmore.common.client.CityClient;
import cn.linkmore.common.client.DistrictClient;
import cn.linkmore.common.response.ResCity;
import cn.linkmore.common.response.ResDistrict;
import cn.linkmore.common.response.ResOldDict;
import cn.linkmore.ops.biz.service.PrefectureService;
import cn.linkmore.prefecture.client.PrefectureClient;
import cn.linkmore.prefecture.client.StrategyBaseClient;
import cn.linkmore.prefecture.request.ReqCheck;
import cn.linkmore.prefecture.request.ReqPreExcel;
import cn.linkmore.prefecture.request.ReqPrefectureEntity;
import cn.linkmore.prefecture.response.ResFeeStrategy;
import cn.linkmore.prefecture.response.ResPreExcel;
import cn.linkmore.prefecture.response.ResPreList;
import cn.linkmore.prefecture.response.ResPrefectureDetail;

@Service
public class PrefectureServiceImpl implements PrefectureService {
	@Autowired
	private PrefectureClient prefectureClient;
	@Autowired
	private CityClient cityClient;
	@Autowired
	private DistrictClient districtClient;
	@Autowired
	private BaseDictClient baseDictClient;
	@Autowired
	private StrategyBaseClient strategyBaseClient;

	/*
	 * 专区下拉列表
	 */
	@Override
	public List<ResPreList> findSelectList() {
		return this.prefectureClient.selectList();
	}
	
	/*
	 * 城市列表
	 */
	@Override
	public List<ResCity> findCityList() {
		return this.cityClient.findSelectList();
	}

	/*
	 * 区域列表
	 */
	@Override
	public List<ResDistrict> findDistrictList(Long cityId) {
		return this.districtClient.findSelectListByCityId(cityId);
	}

	/*
	 * 计费策略列表
	 */
	@Override
	public List<ResFeeStrategy> findStrategyList() {
		return this.strategyBaseClient.findSelectList();
	}

	/*
	 * 计费系统列表
	 */
	@Override
	public List<ResOldDict> findBillSystemList() {
		return baseDictClient.findBillSystemList();
	}
	

	/*
	 * 专区信息列表
	 */
	@Override
	public ViewPage findPage(ViewPageable pageable) {
		return this.prefectureClient.list(pageable);
	}

	/*
	 * 删除专区信息
	 */
	@Override
	public int delete(List<Long> ids) {
		return this.prefectureClient.delete(ids);
	}

	@Override
	public Boolean check(ReqCheck reqCheck) {
		return this.prefectureClient.check(reqCheck);
	}

	@Override
	public int save(ReqPrefectureEntity prefecture) {
		prefecture.setValidTime(getEndDayTime(prefecture.getValidTime()));
		prefecture.setStallTotal(0);
		prefecture.setSoldTimes(0);
		prefecture.setCreateTime(new Date());
		prefecture.setUpdateTime(new Date());
		if (prefecture.getType().intValue() == 0) {
			prefecture.setEnterpriseId(null);
		}
		return this.prefectureClient.save(prefecture);
	}
	
	/*
	 * 更新
	 */
	@Override
	public int update(ReqPrefectureEntity prefecture) {
		prefecture.setValidTime(getEndDayTime(prefecture.getValidTime()));
		prefecture.setUpdateTime(new Date());
		return this.prefectureClient.update(prefecture);
	}

	private Date getEndDayTime(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.HOUR_OF_DAY, 23);
		c.set(Calendar.MINUTE, 59);
		c.set(Calendar.SECOND, 59);
		return c.getTime();
	}

	@Override
	public ResPrefectureDetail findById(Long id) {
		return this.prefectureClient.findById(id);
	}

	@Override
	public List<ResPrefectureDetail> findList(Map<String, Object> param) {
		return this.prefectureClient.findList(param);
	}
	
	@Override
	public List<ResPrefectureDetail> findAll() {
		return this.prefectureClient.findAll();
	}

	/*
	 * 导出
	 */
	@Override
	public List<ResPreExcel> exportList(ReqPreExcel reqPreExcel) {
		return this.prefectureClient.exportList(reqPreExcel);
	}

	@Override
	public List<Map<String, Object>> findByCity(Long cid) {
		return this.prefectureClient.findListByCityId(cid);
	}

	@Override
	public ResPrefectureDetail checkName(String preName) {
		return this.prefectureClient.checkName(preName);
	}

}
