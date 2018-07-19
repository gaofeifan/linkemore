package cn.linkmore.enterprise.dao.master;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.enterprise.entity.EntStaff;
/**
 * 企业员工---写mapper
 * @author   GFF
 * @Date     2018年7月18日
 * @Version  v2.0
 */
@Mapper
public interface EntStaffMasterMapper {
    int deleteById(Long id);

    int save(EntStaff record);

    int saveSelective(EntStaff record);

    EntStaff findById(Long id);

    int updateByIdSelective(EntStaff record);

    int updateById(EntStaff record);
}