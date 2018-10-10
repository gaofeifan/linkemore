package cn.linkmore.enterprise.dao.cluster;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.enterprise.controller.staff.response.CauseReponseBean;
import cn.linkmore.enterprise.controller.staff.response.DictBean;
import cn.linkmore.enterprise.entity.BaseDictGroup;


@Mapper
public interface BaseDictGroupMapper {
    BaseDictGroup selectByPrimaryKey(Long id);

    List<CauseReponseBean> findByCause(Map<String,Object> param);
    
    List<DictBean> findCustomerBean(List<String> list);

	List<CauseReponseBean> findByCode(Map<String, Object> param);
}