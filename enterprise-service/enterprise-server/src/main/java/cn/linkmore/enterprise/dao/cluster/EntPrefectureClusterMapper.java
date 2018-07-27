package cn.linkmore.enterprise.dao.cluster;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.enterprise.entity.EntPrefecture;
/**
 * 企业车区--读
 * @author   GFF
 * @Date     2018年7月18日
 * @Version  v2.0
 */
@Mapper
public interface EntPrefectureClusterMapper {
    EntPrefecture findById(Long id);

	EntPrefecture findByPreId(Long preId);

	/**
	 * @Description  
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	List<EntPrefecture> findList(Map<String, Object> map);
}