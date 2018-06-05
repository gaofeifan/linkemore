package cn.linkmore.prefecture.dao.cluster;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.prefecture.entity.TargetMounth;
import cn.linkmore.prefecture.response.ResTargetMounth;
@Mapper
public interface TargetMounthClusterMapper {

    TargetMounth findById(Long id);

	List<ResTargetMounth> findPage(Map<String, Object> param);

	Integer count(Map<String, Object> param);

}