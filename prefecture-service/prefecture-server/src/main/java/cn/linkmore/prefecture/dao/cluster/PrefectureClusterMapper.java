package cn.linkmore.prefecture.dao.cluster;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;
import cn.linkmore.prefecture.entity.Prefecture;

@Mapper
public interface PrefectureClusterMapper {
	
    Prefecture selectByPrimaryKey(Long id);

    List<Prefecture> selectAll();

	Prefecture getStrategyId(Long preId);
	//根据 cityId和status 获取正常 的专区数量
	int getCountByCityId(Map<String,Object> paramMap);
	
	//根据经纬度范围和状态获取 专区
	//List<PrefectureResponseBean> getPreByStatusAndGPS(Map<String, Object> paramMap);
	//List<PrefectureResponseBean> getPreByStatusAndGPS1(Map<String, Object> paramMap);
	//根据 cityId和status 分页查询专区列表
	//List<PrefecturePageListResponseBean> getPageList(Map<String, Object> paramMap); 
	//List<PrefecturePageListResponseBean> getPageList1(Map<String, Object> paramMap);
	//专区详情
	//PrefectureDetailResponseBean getPreDetail(Long id);

}