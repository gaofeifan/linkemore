package cn.linkmore.prefecture.dao.cluster;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.prefecture.entity.TargetDay;
import cn.linkmore.prefecture.response.ResTargetDay;
@Mapper
public interface TargetDayClusterMapper {

    TargetDay findById(Long id);

	Integer count(Map<String, Object> param);

	List<ResTargetDay> findPage(Map<String, Object> param);

}