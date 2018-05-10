package cn.linkmore.account.dao.cluster;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.account.entity.VehicleMarkManage;
@Mapper
public interface VehicleMarkManageClusterMapper {

	    VehicleMarkManage selectByPrimaryKey(Long id);

	    List<VehicleMarkManage> selectAll();

	    //查询车牌号是否已经存在
		int getCountByVehMark(Map<String,Object> paramMap);
		//获取个人车牌号数量
		int getTotalByUserId(Long userId);
		//根据车牌id查询
		int getCountById(Long id);
		//根据用户id获取车牌列表
		List<VehicleMarkManage> getAllByUserId(String userId);
}