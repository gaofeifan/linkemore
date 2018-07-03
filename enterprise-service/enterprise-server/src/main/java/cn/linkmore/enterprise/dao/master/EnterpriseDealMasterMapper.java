package cn.linkmore.enterprise.dao.master;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import cn.linkmore.enterprise.entity.EnterpriseDeal;
import cn.linkmore.enterprise.response.ResEnterpriseDeal;
/**
 * 企业合同
 * @author jiaohanbin
 * @version 2.0
 *
 */
@Mapper
public interface EnterpriseDealMasterMapper {

    int save(EnterpriseDeal record);

    int update(EnterpriseDeal record);
    
    int delete(List<Long> ids);
    
    void updateBatch(List<ResEnterpriseDeal> enterpriseDealList);
    
    void updateByIdSelective(EnterpriseDeal record);

	void updateCreateStatus(Map<String, Object> map);
}