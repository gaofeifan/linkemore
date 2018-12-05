package cn.linkmore.enterprise.dao.cluster;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;
import cn.linkmore.enterprise.entity.RentEnt;
/**
 * 长租企业
 * @author   GFF
 * @Date     2018年11月26日
 * @Version  v2.0
 */
@Mapper
public interface RentEntClusterMapper {
    /**
     * @Description  根据主键查询
     * @Author   GFF 
     * @Version  v2.0
     */
    RentEnt findById(Long id);

	/**
	 * @Description  
	 * @Author   GFF 
	 * @Version  v2.0
	 */
	Integer count(Map<String, Object> param);

	List<RentEnt> findPage(Map<String, Object> param);

	List<RentEnt> findByComId(Long comId);

}