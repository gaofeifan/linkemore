package cn.linkmore.enterprise.dao.cluster;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.enterprise.entity.EntRentedRecord;
/**
 * 长租用户会用记录--读
 * @author   GFF
 * @Date     2018年7月18日
 * @Version  v2.0
 */
@Mapper
public interface EntRentedRecordClusterMapper {

    EntRentedRecord findById(Long id);

}