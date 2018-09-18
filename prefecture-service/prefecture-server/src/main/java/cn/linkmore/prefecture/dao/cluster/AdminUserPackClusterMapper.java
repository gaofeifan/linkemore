package cn.linkmore.prefecture.dao.cluster;

import java.util.HashMap;

import org.apache.ibatis.annotations.Mapper;

/**
 * @author   GFF
 * @Date     2018年9月17日
 * @Version  v2.0
 */
@Mapper
public interface AdminUserPackClusterMapper {

	HashMap<String, Object> findByAdminId(Long adminId);

}
