package cn.linkmore.enterprise.dao.cluster;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.enterprise.entity.RentEntUser;
/**
 * 企业长租用户
 * @author   GFF
 * @Date     2018年11月26日
 * @Version  v2.0
 */
@Mapper
public interface RentEntUserClusterMapper {

    RentEntUser selectByPrimaryKey(Long rentEntId);

	Integer count(Map<String, Object> param);

	List<RentEntUser> findPage(Map<String, Object> param);

}