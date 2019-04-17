package cn.linkmore.prefecture.dao.master;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import cn.linkmore.prefecture.entity.AdminUser;
@Mapper
public interface AdminUserMasterMapper {

    int save(AdminUser record);

    int update(AdminUser record);

	int delete(List<Long> ids);

	void updateLoginTime(Map<String, Object> map);

	void updateMobile(@Param("id")Long id, @Param("mobile")String mobile);

	void updatePw(@Param("id")Long id, @Param("pw")String pw);
}