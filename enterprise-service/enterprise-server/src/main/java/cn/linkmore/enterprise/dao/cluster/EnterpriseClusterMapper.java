package cn.linkmore.enterprise.dao.cluster;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;
import cn.linkmore.enterprise.response.ResEnterprise;
/**
 * 企业
 * @author jiaohanbin
 * @version 2.0
 *
 */
@Mapper
public interface EnterpriseClusterMapper {

    ResEnterprise findById(Long id);
        
    List<ResEnterprise> findList(Map<String,Object> param);
    
    List<ResEnterprise> findPage(Map<String,Object> param);
    
    int count(Map<String,Object> param);
    
    int check(Map<String,Object> param);
    
    ResEnterprise findName(Map<String,Object> param);
    
    List<ResEnterprise> findAll();
    
    List<ResEnterprise>findByType(Map<String,Object> param);
    
    List<Long>findByRegion(Map<String,Object> param);

	/**
	 * 通过所属行业查询区域
	 */
    List<ResEnterprise> selectRegionByIndustry(Integer industry);
}