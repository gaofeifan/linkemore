package cn.linkmore.coupon.dao.cluster;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;
import cn.linkmore.coupon.response.ResSendUser;
import cn.linkmore.coupon.response.ResSendUserList;

@Mapper
public interface SendUserClusterMapper {

    ResSendUser findById(Long id);

	Integer count(Map<String, Object> param);

	List<ResSendUser> findExpireList();

	List<ResSendUserList> findPage(Map<String, Object> param);

}