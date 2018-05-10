package cn.linkmore.common.dao.master;

import java.util.List;

import java.util.Map;
import org.apache.ibatis.annotations.Mapper;
import cn.linkmore.common.entity.BaseDictGroup;
import cn.linkmore.common.response.ResCauseBean;
import cn.linkmore.common.response.ResCustomerInfoBean;

@Mapper
public interface BaseDictGroupMasterMapper {
    BaseDictGroup selectByPrimaryKey(Long id);

    List<ResCauseBean> findByCause(Map<String,Object> param);
    
    List<ResCustomerInfoBean> findCustomerBean(List<String> list);

	List<ResCauseBean> findByCode(Map<String, Object> param);
}