package cn.linkmore.prefecture.dao.cluster;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;
import cn.linkmore.prefecture.response.ResAdminAuthStall;
/**
 * dao
 * @author jiaohanbin
 * @version 2.0
 *
 */
@Mapper
public interface AdminAuthStallClusterMapper {

	List<ResAdminAuthStall> findList(Map<String, Object> param);
}