package cn.linkmore.enterprise.dao.cluster;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.enterprise.entity.EntStaff;
/**
 * 企业员工---读mapper
 * @author   GFF
 * @Date     2018年7月18日
 * @Version  v2.0
 */
@Mapper
public interface EntStaffClusterMapper {

    EntStaff findById(Long id);

	EntStaff findByMobile(String mobile);

}