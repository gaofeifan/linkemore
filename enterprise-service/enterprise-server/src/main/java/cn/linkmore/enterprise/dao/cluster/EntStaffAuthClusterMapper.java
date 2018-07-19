package cn.linkmore.enterprise.dao.cluster;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.enterprise.entity.EntStaffAuth;
/**
 * 企业用户权限---读
 * @author   GFF
 * @Date     2018年7月18日
 * @Version  v2.0
 */
@Mapper
public interface EntStaffAuthClusterMapper {

    EntStaffAuth findById(Long id);
}