package cn.linkmore.prefecture.dao.cluster;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import cn.linkmore.prefecture.entity.Prefecture;
import cn.linkmore.prefecture.request.ReqPreExcel;
import cn.linkmore.prefecture.response.ResPre;
import cn.linkmore.prefecture.response.ResPreExcel;
import cn.linkmore.prefecture.response.ResPreList;
import cn.linkmore.prefecture.response.ResPrefecture;
import cn.linkmore.prefecture.response.ResPrefectureDetail;
/**
 * dao 车区
 * @author jiaohanbin
 * @version 2.0
 *
 */
@Mapper
public interface PrefectureClusterMapper {

	ResPrefectureDetail findById(Long id);

	List<ResPrefecture> findPreByStatusAndGPS(Map<String, Object> paramMap);

	List<ResPrefecture> findPreByStatusAndGPS1(Map<String, Object> paramMap);

	List<ResPre> findByIds(List<Long> ids);
	
	List<Long> findPreIdList();

	List<ResPreExcel> findExportList();

	List<ResPreList> findSelectList();
	
	List<ResPre> findTreeList(Map<String, Object> param);

	List<ResPreExcel> findPage(Map<String, Object> param);

	Integer count(Map<String, Object> param);

	List<ResPre> findListByCityId(Long cityId);

	Integer check(Map<String, Object> param);

	List<ResPrefectureDetail> findAll();
	/**
	 * 校验车区是否存在
	 * @param param
	 * @return
	 */
	ResPrefectureDetail checkName(Map<String, Object> param);
	
	
}