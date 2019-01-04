package cn.linkmore.enterprise.dao.cluster;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.enterprise.entity.EntUserPlate;
@Mapper
public interface EntUserPlateClusterMapper {

    EntUserPlate findById(Long id);

	Integer count(Map<String, Object> param);

	List<EntUserPlate> findPage(Map<String, Object> param);

	int exists(Map<String, Object> checkParam);

}