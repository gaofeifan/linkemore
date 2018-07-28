package cn.linkmore.enterprise.dao.master;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import cn.linkmore.enterprise.entity.EntBrandUser;
import cn.linkmore.enterprise.request.ReqEntBrandUser;
/**
 * 授权品牌用户
 * @author jiaohanbin
 * @version 2.0
 *
 */
@Mapper
public interface EntBrandUserMasterMapper {
    int delete(List<Long> ids);

    int save(EntBrandUser record);

    int update(EntBrandUser record);

	int insertBatch(List<ReqEntBrandUser> reqUserList);
}